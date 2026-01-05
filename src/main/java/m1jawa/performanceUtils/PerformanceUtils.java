package m1jawa.performanceUtils;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Logger;

public final class PerformanceUtils extends JavaPlugin {

    static PerformanceUtils plugin;
    private Logger logger = getServer().getLogger();
    private final TpsCheck tpsCheck = new TpsCheck(logger);


    @Override
    public void onEnable() {
        plugin = this;

        int frequency = getConfig().getInt("checks.frequency");
        saveDefaultConfig();


        new BukkitRunnable(){
            public void run() {
                tpsCheck.checkTps();
            }
        }.runTaskTimer(this, 0, 20L * frequency);
    }
}
