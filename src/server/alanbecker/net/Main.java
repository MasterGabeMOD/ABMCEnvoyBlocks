package server.alanbecker.net;


import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, (Plugin)this);
        getLogger().info("ABMC Envoy successfully enabled!");
    }

    public void onDisable() {
        getLogger().info("ABMC Envoy successfully disabled!");
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.getPlayer().getWorld() == Bukkit.getWorld("Envoy"))
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this, () -> {
                e.getBlock().setType(Material.AIR);
                e.getBlock().getWorld().playEffect(e.getBlock().getLocation(), Effect.DRAGON_BREATH, 49);
            }150L);
    }

    @EventHandler
    public void onFarmland(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Material s = p.getInventory().getItemInMainHand().getType();
        Material st = p.getInventory().getItemInOffHand().getType();
        if (e.getPlayer().getWorld() == Bukkit.getWorld("Envoy") &&
                e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            if (s.equals(Material.WOODEN_HOE) || s.equals(Material.STONE_HOE) || s.equals(Material.GOLDEN_HOE) || s.equals(Material.DIAMOND_HOE) || s.equals(Material.NETHERITE_HOE)) {
                e.setCancelled(true);
            } else if (st.equals(Material.WOODEN_HOE) || st.equals(Material.STONE_HOE) || st.equals(Material.GOLDEN_HOE) || st.equals(Material.DIAMOND_HOE) || st.equals(Material.NETHERITE_HOE)) {
                e.setCancelled(true);
            }
    }
}