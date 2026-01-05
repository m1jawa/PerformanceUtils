package m1jawa.performanceUtils;

import org.bukkit.Bukkit;

import static m1jawa.performanceUtils.PerformanceUtils.plugin;

public class DiscordPing {
    public static void ping(String msg){

        boolean rolePings = plugin.getConfig().getBoolean("discord.ping-role");
        if (rolePings) {
            String roleId = plugin.getConfig().getString("discord.role-id");
            msg += "\n<@&" + roleId + ">";
        }

        String finalMessage = msg;
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> DiscordWebhook.sendMessage(finalMessage) );
    }
}
