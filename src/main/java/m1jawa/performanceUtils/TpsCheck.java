package m1jawa.performanceUtils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static m1jawa.performanceUtils.PerformanceUtils.plugin;

public class TpsCheck {

    public TpsCheck(Logger logger) {
        this.logger = logger;
    }

    private enum TpsState {
        OK,
        LOW,
        ACCEPTABLE,
        CRITICAL
    }

    private List<Player> online_ping_players;
    private TpsState lastState = TpsState.OK;
    private long lastStateChangeMillis = System.currentTimeMillis();
    private long stateStartMillis = System.currentTimeMillis();
    private double stateMinTps = 20.0;
    private final Logger logger;

    public void checkTps() {
        online_ping_players = new ArrayList<>();

        double tps = Bukkit.getTPS()[0];

        int averageTpsTime = plugin.getConfig().getInt("checks.average-tps-time");
        double averageTps = Bukkit.getTPS()[ (averageTpsTime == 1 || averageTpsTime == 2) ? averageTpsTime : 1 ];

        double ok = plugin.getConfig().getDouble("tps-statuses.ok");
        double acceptable = plugin.getConfig().getDouble("tps-statuses.acceptable");
        double critical = plugin.getConfig().getDouble("tps-statuses.critical");

        long minStateTimeMs = plugin.getConfig().getLong("checks.min-state-time") * 1000;
        long now = System.currentTimeMillis();

        String moreInfoText = plugin.getConfig().getString("messages.more-info");

        TpsState currentState = resolveState(tps, ok, acceptable, critical);

        stateMinTps = Math.min(stateMinTps, tps);

        if (currentState == lastState) return;
        if (now - lastStateChangeMillis < minStateTimeMs) return;

        long durationMillis = now - stateStartMillis;
        long minutes = durationMillis / 60000;
        long seconds = (durationMillis / 1000) % 60;
        String moreInfo = String.format(moreInfoText, minutes, seconds, stateMinTps);

        switch (currentState) {
            case OK -> {

                if (lastState != TpsState.OK) {
                    if (averageTps < ok) {
                        sendMessage(
                                plugin.getConfig().getString("messages.tps-normalizes-part1")
                                        + Math.round(tps)
                                        + plugin.getConfig().getString("messages.tps-normalizes-part2")
                                        + Math.round(averageTps)
                                        + moreInfo
                        );
                    } else sendMessage( plugin.getConfig().getString("messages.normalized-tps") + moreInfo);
                }
            }
            case LOW -> sendMessage(plugin.getConfig().getString("messages.ok-tps") + Math.round(tps) + moreInfo);
            case ACCEPTABLE -> sendMessage(plugin.getConfig().getString("messages.acceptable-tps") + Math.round(tps) + moreInfo);
            case CRITICAL -> sendMessage(
                    plugin.getConfig().getString("messages.critical-tps") + Math.round(tps)
                            + moreInfo
                            + getServerDiagnostics()
            );
        }

        lastState = currentState;
        lastStateChangeMillis = now;
        stateStartMillis = now;
        stateMinTps = tps;
    }

    private TpsState resolveState(double tps, double ok, double acceptable, double critical) {
        if (tps <= critical) return TpsState.CRITICAL;
        if (tps <= acceptable) return TpsState.ACCEPTABLE;
        if (tps <= ok) return TpsState.LOW;
        return TpsState.OK;
    }

    private void sendMessage(String msg){

        List<String> ping_players = (List<String>) plugin.getConfig().getList("pings.nicknames");
        if (ping_players != null) {
            for (String nickname : ping_players) {
                Player player = Bukkit.getPlayer(nickname);
                if (player != null && player.isOnline()) online_ping_players.add( Bukkit.getPlayer(nickname) );
            }
        }

        boolean in_game_pings = plugin.getConfig().getBoolean("pings.in-game");
        boolean console_pings = plugin.getConfig().getBoolean("pings.console");
        boolean discord_pings = plugin.getConfig().getBoolean("pings.discord");

        if (in_game_pings) for (Player player : online_ping_players) player.sendMessage(msg);
        if (console_pings) logger.warning(msg);
        if (discord_pings) DiscordPing.ping(msg);
    }

    private String getServerDiagnostics() {

        int players = 0;
        int totalEntities = 0;
        int totalChunks = 0;

        String message = plugin.getConfig().getString("messages.diagnostics");

        for (World world : Bukkit.getWorlds()) {
            if (world.getPlayers().isEmpty()) continue;

            players += world.getPlayers().size();
            totalEntities += world.getEntities().size();
            totalChunks += world.getLoadedChunks().length;
        }

        return String.format(message, players, totalEntities, totalChunks);
    }

}
