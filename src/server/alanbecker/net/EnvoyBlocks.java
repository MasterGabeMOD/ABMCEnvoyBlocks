package server.alanbecker.net;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.block.Action;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class EnvoyBlocks extends JavaPlugin implements Listener, CommandExecutor {
    private static final EnumSet<Material> TOOLS = EnumSet.of(
        Material.WOODEN_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE,
        Material.GOLDEN_PICKAXE, Material.DIAMOND_PICKAXE, Material.NETHERITE_PICKAXE,
        Material.WOODEN_HOE, Material.STONE_HOE, Material.IRON_HOE,
        Material.GOLDEN_HOE, Material.DIAMOND_HOE, Material.NETHERITE_HOE,
        Material.WOODEN_SHOVEL, Material.STONE_SHOVEL, Material.IRON_SHOVEL,
        Material.GOLDEN_SHOVEL, Material.DIAMOND_SHOVEL, Material.NETHERITE_SHOVEL,
        Material.TRIDENT 
    );

    private long despawnDelay;
    private Effect primaryEffect;
    private Effect secondaryEffect;
    private List<Material> whitelist;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfig();
        getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("envoyblocks").setExecutor(this);
        getLogger().info("ABMC Envoy successfully enabled!");
    }

    private void loadConfig() {
        reloadConfig();
        FileConfiguration config = getConfig();
        despawnDelay = config.getLong("despawn-delay");
        primaryEffect = Effect.valueOf(config.getString("effect.primary"));
        secondaryEffect = Effect.valueOf(config.getString("effect.secondary"));
        whitelist = config.getStringList("whitelist").stream()
                .map(Material::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public void onDisable() {
        getLogger().info("ABMC Envoy successfully disabled!");
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (!"Envoy".equals(e.getBlock().getWorld().getName()) || e.getPlayer().hasPermission("abmc.envoy.bypass")) {
            return;
        }

        if (!whitelist.contains(e.getBlockPlaced().getType())) {
            e.setCancelled(true);
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                e.getBlock().setType(Material.AIR);
                e.getBlock().getWorld().playEffect(e.getBlock().getLocation(), primaryEffect, 0);
                e.getBlock().getWorld().playEffect(e.getBlock().getLocation(), secondaryEffect, 0);
            }
        }.runTaskLater(this, despawnDelay);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("envoyblocks.reload")) {
                sender.sendMessage("You do not have permission to perform this command.");
                return true;
            }

            loadConfig();
            sender.sendMessage("Configuration reloaded.");
            return true;
        }
        return false;
    }

    @EventHandler
    public void onToolUse(PlayerInteractEvent e) {
        if (!"Envoy".equals(e.getPlayer().getWorld().getName()) ||
            e.getAction() != Action.RIGHT_CLICK_BLOCK ||
            !TOOLS.contains(e.getPlayer().getInventory().getItemInMainHand().getType())) {
            return;
        }

        e.setCancelled(true);
    }
}