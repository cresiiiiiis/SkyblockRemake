package org.crackpixelmobs.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.crackpixelmobs.Rarity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Terminator implements Listener {

    private final JavaPlugin plugin;

    public Terminator(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public ItemStack createTerminatorBow() {
        ItemStack terminatorBow = new ItemStack(Material.BOW);
        ItemMeta bowMeta = terminatorBow.getItemMeta();
        ((ItemMeta) bowMeta).setDisplayName(ChatColor.GOLD + "Terminator");

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + "+310");
        lore.add(ChatColor.GRAY + "Strength: " + ChatColor.RED + "+50");
        lore.add(ChatColor.GRAY + "Crit Damage: " + ChatColor.RED + "+250%");
        lore.add("");
        lore.add(ChatColor.GOLD + "Shortbow Instantly Shoots!");
        lore.add(ChatColor.GRAY + "Shoots " + ChatColor.AQUA + "3 " + ChatColor.GRAY + "arrows at once.");
        lore.add(ChatColor.GRAY + "Can damage enderman.");
        lore.add(" ");

        Rarity rarity = Rarity.LEGENDARY;
        lore.add(rarity.getRarityColor() + "" + ChatColor.BOLD + rarity.getName());

        bowMeta.setLore(lore);
        terminatorBow.setItemMeta(bowMeta);
        return terminatorBow;
    }

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (heldItem != null && heldItem.isSimilar(createTerminatorBow())) {
            if (event.getAction().toString().contains("LEFT")) {
                // Shoot 3 arrows in a pattern
                for (int i = 0; i < 3; i++) {
                    player.launchProjectile(Arrow.class);
                }

                // Play sound and particle effects
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation(), 20);
            }
        }
    }
}
