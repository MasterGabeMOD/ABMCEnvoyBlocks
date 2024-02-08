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
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EnumSet;

public class Main extends JavaPlugin implements Listener {
    private static final EnumSet<Material> TOOLS = EnumSet.of(
        Material.WOODEN_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE,
        Material.GOLDEN_PICKAXE, Material.DIAMOND_PICKAXE, Material.NETHERITE_PICKAXE,
        Material.WOODEN_HOE, Material.STONE_HOE, Material.IRON_HOE,
        Material.GOLDEN_HOE, Material.DIAMOND_HOE, Material.NETHERITE_HOE,
        Material.WOODEN_SHOVEL, Material.STONE_SHOVEL, Material.IRON_SHOVEL,
        Material.GOLDEN_SHOVEL, Material.DIAMOND_SHOVEL, Material.NETHERITE_SHOVEL
    );
    private long despawnDelay;
    private Effect primaryEffect;
    private Effect secondaryEffect;

    @Override
    public void onEnable() {
        saveDefaultConfig(); 
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("ABMC Envoy successfully enabled!");

        despawnDelay = getConfig().getLong("despawn-delay");
        primaryEffect = Effect.valueOf(getConfig().getString("effect.primary"));
        secondaryEffect = Effect.valueOf(getConfig().getString("effect.secondary"));
    }

    @Override
    public void onDisable() {
        getLogger().info("ABMC Envoy successfully disabled!");
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.getBlock().getWorld().getName().equals("Envoy") && !e.getPlayer().hasPermission("abmc.envoy.bypass")) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
                e.getBlock().setType(Material.AIR);
                e.getBlock().getWorld().playEffect(e.getBlock().getLocation(), primaryEffect, Material.BEDROCK.createBlockData());
                e.getBlock().getWorld().playEffect(e.getBlock().getLocation(), secondaryEffect, 0);
            }, despawnDelay); 
        }
    }

    @EventHandler
    public void onToolUse(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Material heldItem = p.getInventory().getItemInMainHand().getType();
        if ("Envoy".equals(p.getWorld().getName()) && 
            e.getAction() == Action.RIGHT_CLICK_BLOCK &&
            TOOLS.contains(heldItem)) {
            e.setCancelled(true);
        }
    }
}
