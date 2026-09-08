package com.tiers.profile.types;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.tiers.TiersClient.httpClient;
import static com.tiers.TiersClient.userAgent;

public class CZSKTiersProfile extends SuperProfile {
    public static final Identifier CZSKTIERS_IMAGE = Identifier.fromNamespaceAndPath("minecraft", "textures/czsktiers_logo.png");
    private static final String SUPABASE_REST_URL = "https://oifrjesqnpumpvnbcbss.supabase.co/rest/v1/";
    private static final String SUPABASE_API_KEY = "sb_publishable_51G816zuAaV1we6Ai-YW8Q_l-e0d_7Z";

    private static final String[] TIER_NAMES = {
            "NONE", "LT5", "HT5", "LT4", "HT4", "LT3", "HT3", "LT2", "HT2", "LT1", "HT1"
    };

    public CZSKTiersProfile(String uuid) {
        super();
        addGamemodes();
        fetchData(uuid);
    }

    private void addGamemodes() {
        gameModes.add(new GameMode(Mode.CZSK_SWORD, "sword"));
        gameModes.add(new GameMode(Mode.CZSK_AXE, "axe"));
        gameModes.add(new GameMode(Mode.CZSK_CRYSTAL, "crystal"));
        gameModes.add(new GameMode(Mode.CZSK_UHC, "uhc"));
        gameModes.add(new GameMode(Mode.CZSK_POT, "pot"));
        gameModes.add(new GameMode(Mode.CZSK_NETH_POT, "nethpot"));
        gameModes.add(new GameMode(Mode.CZSK_SMP, "smp"));
        gameModes.add(new GameMode(Mode.CZSK_MACE, "mace"));
        gameModes.add(new GameMode(Mode.CZSK_SPEARMACE, "spearmace"));
    }

    public void fetchData(String uuid) {
        if (uuid == null || uuid.isEmpty()) {
            status = Status.NOT_PLAYER;
            return;
        }
        this.uuid = uuid;
        this.apiUrl = SUPABASE_REST_URL;

        HttpRequest playerReq = HttpRequest.newBuilder()
                .uri(URI.create(SUPABASE_REST_URL + "players?mc_uuid=eq." + uuid + "&select=mc_uuid,mc_ign,region,discord_id"))
                .header("apikey", SUPABASE_API_KEY)
                .header("Authorization", "Bearer " + SUPABASE_API_KEY)
                .header("User-Agent", userAgent)
                .timeout(Duration.ofSeconds(4))
                .GET()
                .build();

        HttpRequest tiersReq = HttpRequest.newBuilder()
                .uri(URI.create(SUPABASE_REST_URL + "tiers?mc_uuid=eq." + uuid + "&select=*"))
                .header("apikey", SUPABASE_API_KEY)
                .header("Authorization", "Bearer " + SUPABASE_API_KEY)
                .header("User-Agent", userAgent)
                .timeout(Duration.ofSeconds(4))
                .GET()
                .build();

        HttpRequest retireReq = HttpRequest.newBuilder()
                .uri(URI.create(SUPABASE_REST_URL + "retire_profiles?mc_uuid=eq." + uuid + "&select=kit,is_retired,retired_tier,peak_tier"))
                .header("apikey", SUPABASE_API_KEY)
                .header("Authorization", "Bearer " + SUPABASE_API_KEY)
                .header("User-Agent", userAgent)
                .timeout(Duration.ofSeconds(4))
                .GET()
                .build();

        CompletableFuture<HttpResponse<String>> pFuture = httpClient.sendAsync(playerReq, HttpResponse.BodyHandlers.ofString());
        CompletableFuture<HttpResponse<String>> tFuture = httpClient.sendAsync(tiersReq, HttpResponse.BodyHandlers.ofString());
        CompletableFuture<HttpResponse<String>> rFuture = httpClient.sendAsync(retireReq, HttpResponse.BodyHandlers.ofString());

        CompletableFuture.allOf(pFuture, tFuture, rFuture).thenAccept(v -> {
            try {
                HttpResponse<String> pRes = pFuture.join();
                HttpResponse<String> tRes = tFuture.join();
                HttpResponse<String> rRes = rFuture.join();

                if (pRes.statusCode() != 200 || tRes.statusCode() != 200) {
                    status = Status.API_ISSUE;
                    failedRequest();
                    return;
                }

                JsonArray pArr = JsonParser.parseString(pRes.body()).getAsJsonArray();
                if (pArr.isEmpty()) {
                    status = Status.NOT_EXISTING;
                    return;
                }

                JsonObject playerObj = pArr.get(0).getAsJsonObject();
                if (playerObj.has("region") && !playerObj.get("region").isJsonNull()) {
                    region = playerObj.get("region").getAsString();
                } else {
                    region = "CZ";
                }
                if (playerObj.has("discord_id") && !playerObj.get("discord_id").isJsonNull()) {
                    discordId = playerObj.get("discord_id").getAsString();
                }

                JsonArray tArr = JsonParser.parseString(tRes.body()).getAsJsonArray();
                JsonObject tiersObj = tArr.isEmpty() ? new JsonObject() : tArr.get(0).getAsJsonObject();

                Map<String, String> retiredKits = new HashMap<>();
                Map<String, String> peakKits = new HashMap<>();
                if (rRes.statusCode() == 200) {
                    JsonArray rArr = JsonParser.parseString(rRes.body()).getAsJsonArray();
                    for (JsonElement el : rArr) {
                        JsonObject ro = el.getAsJsonObject();
                        String kit = ro.has("kit") && !ro.get("kit").isJsonNull() ? ro.get("kit").getAsString().toLowerCase() : "";
                        boolean isRetired = ro.has("is_retired") && ro.get("is_retired").getAsBoolean();
                        String retTier = ro.has("retired_tier") && !ro.get("retired_tier").isJsonNull() ? ro.get("retired_tier").getAsString() : "";
                        String pkTier = ro.has("peak_tier") && !ro.get("peak_tier").isJsonNull() ? ro.get("peak_tier").getAsString() : "";
                        if (isRetired && !retTier.isEmpty()) {
                            retiredKits.put(kit, "R" + retTier.toUpperCase());
                        }
                        if (!pkTier.isEmpty()) {
                            peakKits.put(kit, pkTier.toUpperCase());
                        }
                    }
                }

                int totalPoints = 0;
                for (GameMode gm : gameModes) {
                    String kitKey = gm.parsingName.toLowerCase();
                    String tName = "NONE";
                    if (retiredKits.containsKey(kitKey)) {
                        tName = retiredKits.get(kitKey);
                    } else if (tiersObj.has(kitKey) && !tiersObj.get(kitKey).isJsonNull()) {
                        int tNum = tiersObj.get(kitKey).getAsInt();
                        if (tNum >= 0 && tNum < TIER_NAMES.length) {
                            tName = TIER_NAMES[tNum];
                        }
                    }
                    String peak = peakKits.getOrDefault(kitKey, tName);
                    gm.setDirectTier(tName, peak);
                    totalPoints += gm.getTierPoints(false);
                }

                points = totalPoints;
                overallPosition = 0;
                updateDisplayTexts();
                status = Status.READY;
            } catch (Exception e) {
                status = Status.API_ISSUE;
                failedRequest();
            }
        }).exceptionally(ex -> {
            status = Status.API_ISSUE;
            failedRequest();
            return null;
        });
    }
}
