package com.tiers.profile.types;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tiers.TiersClient;
import com.tiers.misc.Mode;
import com.tiers.profile.GameMode;
import com.tiers.profile.Status;
import com.tiers.textures.Icons;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.tiers.TiersClient.*;

public class SuperProfile {
    private static final ScheduledExecutorService updateAndRecoverFailedRequestsScheduler = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r, "tiers-superprofile-scheduler");
        t.setDaemon(true);
        return t;
    });
    public static final CopyOnWriteArrayList<SuperProfile> failedSuperProfiles = new CopyOnWriteArrayList<>();
    public static final AtomicInteger MCTiersRequests = new AtomicInteger(0);
    public static final AtomicInteger PvPTiersRequests = new AtomicInteger(0);
    public static final AtomicInteger SubtiersRequests = new AtomicInteger(0);
    public static final AtomicInteger CZSKTiersRequests = new AtomicInteger(0);
    public static final AtomicInteger CZSKB0tRequests = new AtomicInteger(0);
    public static final AtomicInteger CZSKSubtiersRequests = new AtomicInteger(0);

    public static final AtomicInteger failedMCTiersRequests = new AtomicInteger(0);
    public static final AtomicInteger failedPvPTiersRequests = new AtomicInteger(0);
    public static final AtomicInteger failedSubtiersRequests = new AtomicInteger(0);
    public static final AtomicInteger failedCZSKTiersRequests = new AtomicInteger(0);
    public static final AtomicInteger failedCZSKB0tRequests = new AtomicInteger(0);
    public static final AtomicInteger failedCZSKSubtiersRequests = new AtomicInteger(0);

    public static final AtomicInteger failedMCTiersRequestsLastMinute = new AtomicInteger(0);
    public static final AtomicInteger failedPvPTiersRequestsLastMinute = new AtomicInteger(0);
    public static final AtomicInteger failedSubtiersRequestsLastMinute = new AtomicInteger(0);
    public static final AtomicInteger failedCZSKTiersRequestsLastMinute = new AtomicInteger(0);
    public static final AtomicInteger failedCZSKB0tRequestsLastMinute = new AtomicInteger(0);
    public static final AtomicInteger failedCZSKSubtiersRequestsLastMinute = new AtomicInteger(0);

    public static boolean isMCTiersDown = false;
    public static boolean isPvPTiersDown = false;
    public static boolean isSubtiersDown = false;
    public static boolean isCZSKTiersDown = false;
    public static boolean isCZSKB0tDown = false;
    public static boolean isCZSKSubtiersDown = false;
    public static int numberOfMessages;

    public Status status = Status.SEARCHING;
    private int numberOfRequests;

    protected String region = "Unknown";
    protected int points = 0;
    protected int overallPosition = 0;

    public Component displayedRegion;
    public Component displayedOverall;
    public Component overallTooltip;
    public Component regionTooltip;

    public final ArrayList<GameMode> gameModes = new ArrayList<>();
    public GameMode highest;

    public String originalJson;
    public String apiUrl;
    public String uuid;
    public String discordId;
    public boolean drawn;
    public boolean apiErrorShown;
    private Runnable onUpdate;

    protected SuperProfile() {
        if (this instanceof MCTiersProfile)
            MCTiersRequests.incrementAndGet();
        if (this instanceof PvPTiersProfile)
            PvPTiersRequests.incrementAndGet();
        if (this instanceof SubtiersProfile)
            SubtiersRequests.incrementAndGet();
        if (this instanceof CZSKTiersProfile)
            CZSKTiersRequests.incrementAndGet();
        if (this instanceof CZSKB0tProfile)
            CZSKB0tRequests.incrementAndGet();
        if (this instanceof CZSKSubtiersProfile)
            CZSKSubtiersRequests.incrementAndGet();
    }

    static {
        updateAndRecoverFailedRequestsScheduler.scheduleAtFixedRate(SuperProfile::updateAndRecoverFailedRequests, 0, 1, TimeUnit.MINUTES);
        updateAndRecoverFailedRequestsScheduler.scheduleAtFixedRate(SuperProfile::updateDownStatus, 0, 1, TimeUnit.SECONDS);
    }

    private static void updateDownStatus() {
        isMCTiersDown = failedMCTiersRequestsLastMinute.get() > 3;
        isPvPTiersDown = failedPvPTiersRequestsLastMinute.get() > 3;
        isSubtiersDown = failedSubtiersRequestsLastMinute.get() > 3;
        isCZSKTiersDown = failedCZSKTiersRequestsLastMinute.get() > 3;
        isCZSKB0tDown = failedCZSKB0tRequestsLastMinute.get() > 3;
        isCZSKSubtiersDown = failedCZSKSubtiersRequestsLastMinute.get() > 3;
    }

    private static void updateAndRecoverFailedRequests() {
        failedMCTiersRequestsLastMinute.set(0);
        failedPvPTiersRequestsLastMinute.set(0);
        failedSubtiersRequestsLastMinute.set(0);
        failedCZSKTiersRequestsLastMinute.set(0);
        failedCZSKB0tRequestsLastMinute.set(0);
        failedCZSKSubtiersRequestsLastMinute.set(0);

        for (SuperProfile superProfile : SuperProfile.failedSuperProfiles) {
            if (superProfile instanceof MCTiersProfile && isMCTiersDown)
                continue;
            if (superProfile instanceof PvPTiersProfile && isPvPTiersDown)
                continue;
            if (superProfile instanceof SubtiersProfile && isSubtiersDown)
                continue;
            if (superProfile instanceof CZSKTiersProfile && isCZSKTiersDown)
                continue;
            if (superProfile instanceof CZSKB0tProfile && isCZSKB0tDown)
                continue;
            if (superProfile instanceof CZSKSubtiersProfile && isCZSKSubtiersDown)
                continue;

            SuperProfile.failedSuperProfiles.remove(superProfile);
            superProfile.prepareToRebuild();
            superProfile.buildRequest(superProfile.apiUrl, superProfile.uuid, "");
            try {
                Thread.sleep(150);
            } catch (InterruptedException interruptedException) {
                TiersClient.LOGGER.error("Error while recovering failed super profile. Details: {}\n{}", interruptedException.getMessage(), superProfile);
            }
        }
    }

    public void prepareToRebuild() {
        status = Status.SEARCHING;
        numberOfRequests = 0;

        if (this instanceof MCTiersProfile) {
            MCTiersRequests.incrementAndGet();
            failedMCTiersRequests.decrementAndGet();
        }
        if (this instanceof PvPTiersProfile) {
            PvPTiersRequests.incrementAndGet();
            failedPvPTiersRequests.decrementAndGet();
        }
        if (this instanceof SubtiersProfile) {
            SubtiersRequests.incrementAndGet();
            failedSubtiersRequests.decrementAndGet();
        }
        if (this instanceof CZSKTiersProfile) {
            CZSKTiersRequests.incrementAndGet();
            failedCZSKTiersRequests.decrementAndGet();
        }
        if (this instanceof CZSKB0tProfile) {
            CZSKB0tRequests.incrementAndGet();
            failedCZSKB0tRequests.decrementAndGet();
        }
        if (this instanceof CZSKSubtiersProfile) {
            CZSKSubtiersRequests.incrementAndGet();
            failedCZSKSubtiersRequests.decrementAndGet();
        }
    }

    public void buildRequest(String apiUrl, String uuid, String extra) {
        if (apiUrl == null || uuid == null || extra == null) {
            status = Status.API_ISSUE;
            return;
        }
        this.apiUrl = apiUrl;
        this.uuid = uuid;

        if (numberOfRequests >= 5 || status != Status.SEARCHING) {
            status = Status.TIMEOUTED;
            failedRequest();
            return;
        }

        numberOfRequests++;

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl + uuid + extra))
                .header("User-Agent", userAgent)
                .timeout(Duration.ofSeconds(3))
                .GET()
                .build();

        httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString()).thenAccept(response -> {
            int statusCode = response.statusCode();
            if (statusCode == 404) {
                status = Status.NOT_EXISTING;
                return;
            } else if (statusCode == 502 || statusCode == 525) {
                numberOfRequests++;
                CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> buildRequest(apiUrl, uuid, extra));
                return;
            } else if (statusCode != 200) {
                status = Status.API_ISSUE;
                failedRequest();
                LOGGER.info("Tiers | Request failed ({}) | Code: {} | Body: {}", apiUrl + uuid + extra, statusCode, response.body());
                return;
            }

            parseJson(response.body());
        }).exceptionally(ignored -> {
            CompletableFuture.delayedExecutor(100, TimeUnit.MILLISECONDS).execute(() -> buildRequest(apiUrl, uuid, extra));
            return null;
        });
    }

    public void parseJson(String json) {
        if (JsonParser.parseString(json).isJsonNull()) {
            status = Status.API_ISSUE;
            return;
        }

        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        if (jsonObject.has("name") && jsonObject.has("region") &&
                jsonObject.has("points") && jsonObject.has("overall") && jsonObject.has("rankings") && jsonObject.get("rankings").isJsonObject()) {
            if (!jsonObject.get("region").isJsonNull())
                region = jsonObject.get("region").getAsString();
            else
                region = "Unknown";
            points = jsonObject.get("points").getAsInt();
            overallPosition = jsonObject.get("overall").getAsInt();

            if (jsonObject.has("discord_id"))
                discordId = jsonObject.get("discord_id").getAsString();
        } else {
            status = Status.NOT_EXISTING;
            return;
        }

        parseRankings(jsonObject.getAsJsonObject("rankings"));
        updateDisplayTexts();

        status = Status.READY;
        originalJson = json;

        if (onUpdate != null)
            onUpdate.run();
    }

    public void updateDisplayTexts() {
        displayedRegion = getRegionText();
        regionTooltip = getRegionTooltip();
        displayedOverall = getOverallText();
        overallTooltip = getOverallTooltip();
        highest = getHighestMode();
        if (onUpdate != null)
            onUpdate.run();
    }

    private void parseRankings(JsonObject jsonObject) {
        for (GameMode gameMode : gameModes) {
            if (jsonObject.has(gameMode.parsingName))
                gameMode.parseTiers(jsonObject.getAsJsonObject(gameMode.parsingName));
            else
                gameMode.status = Status.NOT_EXISTING;
        }
        highest = getHighestMode();
    }

    public GameMode getGameMode(Mode gamemode) {
        for (GameMode gameMode : gameModes)
            if (gameMode.gamemode.toString().equalsIgnoreCase(gamemode.toString()))
                return gameMode;

        status = Status.NOT_EXISTING;
        return null;
    }

    private GameMode getHighestMode() {
        GameMode highest = null;
        int highestPoints = 0;
        for (GameMode gameMode : gameModes) {
            if (gameMode.status == Status.READY && gameMode.getTierPoints(false) > highestPoints) {
                highest = gameMode;
                highestPoints = gameMode.getTierPoints(false);
            }
        }
        return highest;
    }

    private Component getRegionText() {
        if (region.equalsIgnoreCase("CZ"))
            return Icons.colorText(region, "eu");
        else if (region.equalsIgnoreCase("SK"))
            return Icons.colorText(region, "eu");
        else if (region.equalsIgnoreCase("EU"))
            return Icons.colorText(region, "eu");
        else if (region.equalsIgnoreCase("NA"))
            return Icons.colorText(region, "na");
        else if (region.equalsIgnoreCase("AS"))
            return Icons.colorText(region, "as");
        else if (region.equalsIgnoreCase("AU"))
            return Icons.colorText(region, "au");
        else if (region.equalsIgnoreCase("SA"))
            return Icons.colorText(region, "sa");
        else if (region.equalsIgnoreCase("ME"))
            return Icons.colorText(region, "me");
        else if (region.equalsIgnoreCase("AF"))
            return Icons.colorText(region, "af");
        else if (region.equalsIgnoreCase("OC"))
            return Icons.colorText(region, "oc");
        return Icons.colorText("Unknown", "unknown");
    }

    private Component getRegionTooltip() {
        if (region.equalsIgnoreCase("CZ"))
            return Icons.colorText("Czechia", "eu");
        else if (region.equalsIgnoreCase("SK"))
            return Icons.colorText("Slovakia", "eu");
        else if (region.equalsIgnoreCase("EU"))
            return Icons.colorText("Europe", "eu");
        else if (region.equalsIgnoreCase("NA"))
            return Icons.colorText("North America", "na");
        else if (region.equalsIgnoreCase("AS"))
            return Icons.colorText("Asia", "as");
        else if (region.equalsIgnoreCase("AU"))
            return Icons.colorText("Australia", "au");
        else if (region.equalsIgnoreCase("SA"))
            return Icons.colorText("South America", "sa");
        else if (region.equalsIgnoreCase("ME"))
            return Icons.colorText("Middle East", "me");
        else if (region.equalsIgnoreCase("AF"))
            return Icons.colorText("Africa", "af");
        else if (region.equalsIgnoreCase("OC"))
            return Icons.colorText("Oceania", "oc");
        return Icons.colorText("Unknown", "unknown");
    }

    private Component getOverallText() {
        if (overallPosition > 0) {
            String positionString = "#" + overallPosition;
            if (!(this instanceof PvPTiersProfile) && points >= 250) return Icons.colorText(positionString, "master");
            else if (this instanceof PvPTiersProfile && points >= 200) return Icons.colorText(positionString, "master");
            else if (points >= 100) return Icons.colorText(positionString, "ace");
            else if (points >= 50) return Icons.colorText(positionString, "specialist");
            else if (points >= 20) return Icons.colorText(positionString, "cadet");
            else if (points >= 10) return Icons.colorText(positionString, "novice");
            return Icons.colorText(positionString, "rookie");
        }

        String ptsString = points + " pts";
        if (points >= 250) return Icons.colorText(ptsString, "master");
        else if (points >= 100) return Icons.colorText(ptsString, "ace");
        else if (points >= 50) return Icons.colorText(ptsString, "specialist");
        else if (points >= 20) return Icons.colorText(ptsString, "cadet");
        else if (points >= 10) return Icons.colorText(ptsString, "novice");
        return Icons.colorText(ptsString, "rookie");
    }

    private Component getOverallTooltip() {
        String overallTooltip = "Combat ";

        if (this instanceof SubtiersProfile || this instanceof CZSKSubtiersProfile)
            overallTooltip = "Subtiers ";
        else if (this instanceof CZSKTiersProfile || this instanceof CZSKB0tProfile)
            overallTooltip = "CZ/SK ";

        if (points >= 400) overallTooltip += "Titan / Grandmaster";
        else if (points >= 300) overallTooltip += "Legend";
        else if (points >= 200) overallTooltip += "Elite / Master";
        else if (points >= 140) overallTooltip += "Master";
        else if (points >= 90) overallTooltip += "Expert / Ace";
        else if (points >= 50) overallTooltip += "Competitor / Specialist";
        else if (points >= 25) overallTooltip += "Challenger / Cadet";
        else if (points >= 10) overallTooltip += "Fighter / Novice";
        else overallTooltip += "Novice / Rookie";
        overallTooltip += "\n\nPoints: " + points;

        return Component.literal(overallTooltip).setStyle(displayedOverall.getStyle());
    }

    protected void failedRequest() {
        String message = null;
        synchronized (SuperProfile.class) {
            if (this instanceof MCTiersProfile) {
                failedMCTiersRequestsLastMinute.incrementAndGet();
                if (failedMCTiersRequests.incrementAndGet() % 20 == 0)
                    message = "[Tiers] MCTiers might be down. " + failedMCTiersRequests + " searches (out of " + MCTiersRequests + ") failed so far. Use '/tiers -status' for more info";
            }
            if (this instanceof PvPTiersProfile) {
                failedPvPTiersRequestsLastMinute.incrementAndGet();
                if (failedPvPTiersRequests.incrementAndGet() % 20 == 0)
                    message = "[Tiers] PvPTiers might be down. " + failedPvPTiersRequests + " searches (out of " + PvPTiersRequests + ") failed so far. Use '/tiers -status' for more info";
            }
            if (this instanceof SubtiersProfile) {
                failedSubtiersRequestsLastMinute.incrementAndGet();
                if (failedSubtiersRequests.incrementAndGet() % 20 == 0)
                    message = "[Tiers] Subtiers might be down. " + failedSubtiersRequests + " searches (out of " + SubtiersRequests + ") failed so far. Use '/tiers -status' for more info";
            }
            if (this instanceof CZSKTiersProfile) {
                failedCZSKTiersRequestsLastMinute.incrementAndGet();
                if (failedCZSKTiersRequests.incrementAndGet() % 20 == 0)
                    message = "[Tiers] CZSKTiers might be down. " + failedCZSKTiersRequests + " searches (out of " + CZSKTiersRequests + ") failed so far. Use '/tiers -status' for more info";
            }
            if (this instanceof CZSKB0tProfile) {
                failedCZSKB0tRequestsLastMinute.incrementAndGet();
                if (failedCZSKB0tRequests.incrementAndGet() % 20 == 0)
                    message = "[Tiers] CZSK-B0t might be down. " + failedCZSKB0tRequests + " searches (out of " + CZSKB0tRequests + ") failed so far. Use '/tiers -status' for more info";
            }
            if (this instanceof CZSKSubtiersProfile) {
                failedCZSKSubtiersRequestsLastMinute.incrementAndGet();
                if (failedCZSKSubtiersRequests.incrementAndGet() % 20 == 0)
                    message = "[Tiers] CZSK-Subtiers might be down. " + failedCZSKSubtiersRequests + " searches (out of " + CZSKSubtiersRequests + ") failed so far. Use '/tiers -status' for more info";
            }
        }

        failedSuperProfiles.add(this);

        if (message != null) {
            TiersClient.LOGGER.info(message);
            if (numberOfMessages < 5) {
                Component textMessage = Icons.colorText(message, CommonColors.YELLOW);

                sendMessageToPlayer(textMessage, false);
                numberOfMessages++;
            }
        }
    }

    public String[] checkOutages() {
        String[] message = new String[3];
        if (this instanceof MCTiersProfile) {
            message[0] = "MCTiers might be down";
            message[1] = failedMCTiersRequests + " searches (out of " + MCTiersRequests + " profiles) failed so far";
        }
        if (this instanceof PvPTiersProfile) {
            message[0] = "PvPTiers might be down";
            message[1] = failedPvPTiersRequests + " searches (out of " + PvPTiersRequests + " profiles) failed so far";
        }
        if (this instanceof SubtiersProfile) {
            message[0] = "Subtiers might be down";
            message[1] = failedSubtiersRequests + " searches (out of " + SubtiersRequests + " profiles) failed so far";
        }
        if (this instanceof CZSKTiersProfile) {
            message[0] = "CZSKTiers might be down";
            message[1] = failedCZSKTiersRequests + " searches (out of " + CZSKTiersRequests + " profiles) failed so far";
        }
        if (this instanceof CZSKB0tProfile) {
            message[0] = "CZSK-B0t might be down";
            message[1] = failedCZSKB0tRequests + " searches (out of " + CZSKB0tRequests + " profiles) failed so far";
        }
        if (this instanceof CZSKSubtiersProfile) {
            message[0] = "CZSK-Subtiers might be down";
            message[1] = failedCZSKSubtiersRequests + " searches (out of " + CZSKSubtiersRequests + " profiles) failed so far";
        }

        message[2] = "Tiers will automatically try to update all failed requests";
        return message;
    }

    public static void resetRequestCounters() {
        synchronized (SuperProfile.class) {
            MCTiersRequests.set(0);
            failedMCTiersRequests.set(0);
            PvPTiersRequests.set(0);
            failedPvPTiersRequests.set(0);
            SubtiersRequests.set(0);
            failedSubtiersRequests.set(0);
            CZSKTiersRequests.set(0);
            failedCZSKTiersRequests.set(0);
            CZSKB0tRequests.set(0);
            failedCZSKB0tRequests.set(0);
            CZSKSubtiersRequests.set(0);
            failedCZSKSubtiersRequests.set(0);
        }
    }

    public void setOnUpdate(Runnable onUpdate) {
        this.onUpdate = onUpdate;
    }

    @Override
    public String toString() {
        StringBuilder gameModesDetails = new StringBuilder();
        gameModes.forEach(gameModesDetails::append);

        return "\nSuperProfile{" +
                "\nstatus=" + status +
                "\nnumberOfRequests=" + numberOfRequests +
                "\nregion=" + (region != null ? region : "null") +
                "\npoints=" + points +
                "\noverallPosition=" + overallPosition +
                "\ndisplayedRegion=" + (displayedRegion != null ? displayedRegion.getString() : "null") +
                "\ndisplayedOverall=" + (displayedOverall != null ? displayedOverall.getString() : "null") +
                "\noverallTooltip=" + (overallTooltip != null ? overallTooltip.getString() : "null") +
                "\nregionTooltip=" + (regionTooltip != null ? regionTooltip.getString() : "null") +
                "\ndrawn=" + drawn +
                "\napiErrorShown=" + apiErrorShown +
                "\n\ngameModes=" + gameModesDetails +
                "\n\nhighest=" + (highest != null ? highest : "null") +
                "\n\noriginalJson=" + (originalJson != null ? originalJson : "null") +
                "\n\n}";
    }
}