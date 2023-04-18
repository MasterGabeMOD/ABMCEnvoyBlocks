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
        this.getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("ABMC Envoy successfully enabled!");
    }

    public void onDisable() {
        getLogger().info("ABMC Envoy successfully disabled!");
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.getBlock().getWorld().getName().equals("Envoy"))
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this, () -> {
                e.getBlock().setType(Material.AIR);
                e.getBlock().getWorld().playEffect(e.getBlock().getLocation(), Effect.STEP_SOUND, 49);
                e.getBlock().getWorld().playEffect(e.getBlock().getLocation(), Effect.DRAGON_BREATH, 49);
                getLogger().info("wowi");
            }, 15 * 10);
    }

    @EventHandler
    public void onToolUse(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Material heldItem = p.getInventory().getItemInHand().getType();
        if (e.getPlayer().getWorld().getName().equals("Envoy") && 
            e.getAction().equals(Action.RIGHT_CLICK_BLOCK) &&
            (isPickaxe(heldItem) || isHoe(heldItem) || isShovel(heldItem))) {
            e.setCancelled(true);
        }  
    }
    
    private boolean isPickaxe(Material material) {
        return (material == Material.WOODEN_PICKAXE || 
                material == Material.STONE_PICKAXE || 
                material == Material.GOLDEN_PICKAXE || 
                material == Material.DIAMOND_PICKAXE || 
                material == Material.NETHERITE_PICKAXE);
    }
    
    private boolean isHoe(Material material) {
        return (material == Material.WOODEN_HOE || 
                material == Material.STONE_HOE || 
                material == Material.GOLDEN_HOE || 
                material == Material.DIAMOND_HOE || 
                material == Material.NETHERITE_HOE);
    }
    
    private boolean isShovel(Material material) {
        return (material == Material.WOODEN_SHOVEL || 
                material == Material.STONE_SHOVEL || 
                material == Material.GOLDEN_SHOVEL || 
                material == Material.DIAMOND_SHOVEL || 
                material == Material.NETHERITE_SHOVEL);
    }
}

// Lots of blocks