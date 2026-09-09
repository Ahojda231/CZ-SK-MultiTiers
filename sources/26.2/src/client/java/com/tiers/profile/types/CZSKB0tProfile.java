package com.tiers.profile.types;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tiers.misc.Mode;
import com.tiers.profile.GameMode;
import com.tiers.profile.Status;
import net.minecraft.resources.Identifier;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static com.tiers.TiersClient.httpClient;
import static com.tiers.TiersClient.userAgent;

public class CZSKB0tProfile extends SuperProfile {
    public static final Identifier CZSK_B0T_IMAGE = Identifier.fromNamespaceAndPath("minecraft", "textures/czsk_b0tfleyz_logo.png");
    private static final String SNAPSHOT_URL = "https://tierlist.slux.cz/data/czsktiers/overall.json";

    private static volatile JsonObject cachedSnapshot;
    private static volatile long lastFetchTime = 0;
    private static final long CACHE_TTL_MS = 5 * 60 * 1000; // 5 minutes

    public CZSKB0tProfile(String uuid) {
        super();
        addGamemodes();
        fetchData(uuid);
    }

    private void addGamemodes() {
        gameModes.add(new GameMode(Mode.CZSK_B0T_SWORD, "Sword"));
        gameModes.add(new GameMode(Mode.CZSK_B0T_AXE, "Axe"));
        gameModes.add(new GameMode(Mode.CZSK_B0T_CRYSTAL, "Crystal"));
        gameModes.add(new GameMode(Mode.CZSK_B0T_UHC, "UHC"));
        gameModes.add(new GameMode(Mode.CZSK_B0T_POT, "Pot"));
        gameModes.add(new GameMode(Mode.CZSK_B0T_NPOT, "Npot"));
        gameModes.add(new GameMode(Mode.CZSK_B0T_SMP, "SMP"));
        gameModes.add(new GameMode(Mode.CZSK_B0T_DIASMP, "DiaSMP"));
        gameModes.add(new GameMode(Mode.CZSK_B0T_MACE, "Mace"));
    }

    public void fetchData(String uuid) {
        if (uuid == null || uuid.isEmpty()) {
            status = Status.NOT_PLAYER;
            return;
        }
        this.uuid = uuid;
        this.apiUrl = SNAPSHOT_URL;

        if (cachedSnapshot != null && (System.currentTimeMillis() - lastFetchTime < CACHE_TTL_MS)) {
            parseFromSnapshot(cachedSnapshot, uuid);
            return;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SNAPSHOT_URL))
                .header("User-Agent", userAgent)
                .timeout(Duration.ofSeconds(4))
                .GET()
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(response -> {
            if (response.statusCode() != 200) {
                if (cachedSnapshot != null) {
                    parseFromSnapshot(cachedSnapshot, uuid);
                    return;
                }
                status = Status.API_ISSUE;
                failedRequest();
                return;
            }

            try {
                JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
                cachedSnapshot = jsonObject;
                lastFetchTime = System.currentTimeMillis();
                parseFromSnapshot(jsonObject, uuid);
            } catch (Exception e) {
                status = Status.API_ISSUE;
                failedRequest();
            }
        }).exceptionally(ex -> {
            if (cachedSnapshot != null) {
                parseFromSnapshot(cachedSnapshot, uuid);
                return null;
            }
            status = Status.API_ISSUE;
            failedRequest();
            return null;
        });
    }

    private void parseFromSnapshot(JsonObject root, String uuid) {
        if (!root.has("players")) {
            status = Status.NOT_EXISTING;
            return;
        }

        JsonArray players = root.getAsJsonArray("players");
        JsonObject foundPlayer = null;
        int rank = 0;
        String cleanUuid = uuid.replace("-", "");

        for (int i = 0; i < players.size(); i++) {
            JsonObject p = players.get(i).getAsJsonObject();
            if (p.has("uuid") && !p.get("uuid").isJsonNull()) {
                String pUuid = p.get("uuid").getAsString().replace("-", "");
                if (pUuid.equalsIgnoreCase(cleanUuid)) {
                    foundPlayer = p;
                    rank = i + 1;
                    break;
                }
            }
        }

        if (foundPlayer == null) {
            status = Status.NOT_EXISTING;
            return;
        }

        region = "CZ";
        overallPosition = rank;
        points = foundPlayer.has("score") ? foundPlayer.get("score").getAsInt() : 0;
        if (foundPlayer.has("id") && !foundPlayer.get("id").isJsonNull())
            discordId = foundPlayer.get("id").getAsString();

        JsonObject tiers = foundPlayer.has("tiers") ? foundPlayer.getAsJsonObject("tiers") : new JsonObject();
        for (GameMode gm : gameModes) {
            if (tiers.has(gm.parsingName)) {
                JsonObject kitObj = tiers.getAsJsonObject(gm.parsingName);
                String tier = kitObj.has("t") && !kitObj.get("t").isJsonNull() ? kitObj.get("t").getAsString() : "NONE";
                String peak = kitObj.has("peak") && !kitObj.get("peak").isJsonNull() ? kitObj.get("peak").getAsString() : tier;
                gm.setDirectTier(tier, peak);
            } else {
                gm.status = Status.NOT_EXISTING;
            }
        }

        updateDisplayTexts();
        status = Status.READY;
    }
}
