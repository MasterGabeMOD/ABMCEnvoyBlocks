package server.alanbecker.net;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    static World Envoy = Bukkit.getWorld("Envoy");

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("ABMC Envoy successfully enabled!");
    }

    public void onDisable() {
        getLogger().info("ABMC Envoy successfully disabled!");
    }


    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.getPlayer().getWorld() == Envoy){
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
                e.getBlock().setType(Material.AIR);
                e.getBlock().getWorld().playEffect(e.getBlock().getLocation(), org.bukkit.Effect.STEP_SOUND, 49);
                System.out.println("wowi");
            }, 15 * 10);
        }
    }
}