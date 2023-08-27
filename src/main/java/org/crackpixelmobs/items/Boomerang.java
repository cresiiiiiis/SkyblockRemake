package org.crackpixelmobs.items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.crackpixelmobs.Rarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Boomerang implements Listener {

    private final JavaPlugin plugin;
    private final HashMap<UUID, Boolean> returningMap = new HashMap<>();

    public Boomerang(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public ItemStack createBoomerang() {
        ItemStack boomerang = new ItemStack(Material.BONE);
        ItemMeta meta = boomerang.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Bonemerang");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + "+270");
        lore.add(ChatColor.GRAY + "Strength: " + ChatColor.RED + "+130");
        lore.add(" ");
        lore.add(ChatColor.GOLD + "Ability Swing:" + ChatColor.YELLOW + ChatColor.BOLD + " RIGHT CLICK");
        lore.add(ChatColor.GRAY + "Throw a bone a short distance.");
        lore.add(ChatColor.GRAY + "dealing the damage an arrow");
        lore.add(ChatColor.GRAY + "would");
        Rarity rarity = Rarity.LEGENDARY;
        lore.add(rarity.getRarityColor() + "" +
                "" + ChatColor.BOLD + "" + rarity.getName());

        // Add any other attributes, lore, and metadata needed for your server
        meta.setLore(lore);
        boomerang.setItemMeta(meta);
        return boomerang;
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getEntity();
            if (arrow.getCustomName() != null && arrow.getCustomName().equals("Bonemerang")) {
                if (returningMap.getOrDefault(arrow.getUniqueId(), false)) {
                    double damage = arrow.getDamage() * 2; // Double damage while returning
                    // Implement logic to apply double damage and possibly shatter the boomerang
                    // Example: Damage the hit entity, check if the boomerang should shatter, etc.
                } else {
                    returningMap.put(arrow.getUniqueId(), true);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            returningMap.remove(arrow.getUniqueId());
                            arrow.remove();
                            // Simulate replacing the boomerang with a ghast tear
                            arrow.getLocation().getWorld().dropItemNaturally(arrow.getLocation(), new ItemStack(Material.GHAST_TEAR));
                        }
                    }.runTaskLater(plugin, 60L); // Replace '60L' with the desired delay (3 seconds)
                }
            }
        }
    }

    public void fireBoomerang(Player player) {
        Arrow arrow = player.launchProjectile(Arrow.class);
        arrow.setCustomName("Bonemerang");
        arrow.setDamage(1.0); // Adjust damage as needed
        // Set other arrow properties like velocity, effects, etc.
        // Example: arrow.setVelocity(player.getEyeLocation().getDirection().multiply(2));
        followPlayer(arrow.getUniqueId(), player);
    }

    public void followPlayer(UUID arrowUUID, Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (returningMap.containsKey(arrowUUID)) {
                    Arrow arrow = getArrowByUUID(arrowUUID);
                    if (arrow != null) {
                        arrow.setVelocity(player.getEyeLocation().subtract(arrow.getLocation()).toVector().normalize());
                    } else {
                        cancel();
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 1L); // Change the interval as needed
    }

    private Arrow getArrowByUUID(UUID uuid) {
        for (Arrow arrow : Bukkit.getWorlds().get(0).getEntitiesByClass(Arrow.class)) {
            if (arrow.getUniqueId().equals(uuid)) {
                return arrow;
            }
        }
        return null;
    }
}
