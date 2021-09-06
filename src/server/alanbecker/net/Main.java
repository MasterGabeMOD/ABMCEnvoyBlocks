package server.alanbecker.net;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    private World envoy;

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("ABMC Envoy successfully enabled!");

        Bukkit.getScheduler().runTaskLater(this, () -> envoy = Bukkit.getWorld("Envoy"), 1L); // By delaying 1 tick, we allow the server to start up
    }

    public void onDisable() {
        getLogger().info("ABMC Envoy successfully disabled!");
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void onPlace(BlockPlaceEvent event) { // Meaningful variable names
        Player player = event.getPlayer();
        World world = player.getWorld();
        Block block = event.getBlock();

        if (!world.getName().equalsIgnoreCase(envoy.getName()))
            return;

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
            block.setType(Material.AIR);
            world.playEffect(block.getLocation(), org.bukkit.Effect.STEP_SOUND, 49);
            System.out.println("wowi");
        }, 15 * 10);

    }
}