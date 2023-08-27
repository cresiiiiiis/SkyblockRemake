package org.crackpixelmobs.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.crackpixelmobs.Rarity;
import org.crackpixelmobs.Stats;

import java.util.ArrayList;
import java.util.List;

public class AOTE implements Listener {

    private final JavaPlugin plugin;
    private final Stats stats; // Add this line

    public AOTE(JavaPlugin plugin, Stats stats) { // Add the Stats parameter
        this.plugin = plugin;
        this.stats = stats; // Initialize the stats instance
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public ItemStack createAspectOfTheEnd() {
        ItemStack aoteSword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta aoteMeta = aoteSword.getItemMeta();
        aoteMeta.setDisplayName(ChatColor.BLUE + "Aspect of The End");

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + "+100"); // Closing the add call
        lore.add(ChatColor.GRAY + "Strength: " + ChatColor.RED + "+100"); // Closing the add call
        lore.add(ChatColor.GOLD + "Ability: Instant Transmission" + ChatColor.YELLOW + ChatColor.BOLD + " RIGHT CLICK"); // Closing the add call
        lore.add(ChatColor.GRAY + "Teleport " + ChatColor.GREEN + "8 blocks " + ChatColor.GRAY + "ahead of you and gain "
                + ChatColor.GREEN + "+50" + ChatColor.WHITE + "⭐ Speed for"); // Closing the add call
        lore.add(ChatColor.GREEN + "3 seconds"); // Closing the add call
        lore.add(""); // Closing the add call
        lore.add(ChatColor.DARK_GRAY + "Cooldown " + ChatColor.GREEN + "5s"); // Closing the add call
        lore.add(ChatColor.GRAY + "Mana cost: " + ChatColor.AQUA + "50"); // Closing the add call
        lore.add(""); // Closing the add call
        lore.add(ChatColor.DARK_GRAY + "This item can be reforged"); // Closing the add call
        lore.add("");
        Rarity rarity = Rarity.RARE;
        lore.add(rarity.getRarityColor() + "" + ChatColor.BOLD + "" + rarity.getName());

        aoteMeta.setLore(lore); // Set the lore to the ItemStack's meta
        aoteMeta.setUnbreakable(true); // Make the sword unbreakable
        aoteSword.setItemMeta(aoteMeta);
        return aoteSword;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        if (heldItem != null && heldItem.isSimilar(createAspectOfTheEnd()) &&
                event.getAction().toString().contains("RIGHT")) {
            double baseDamage = 100.0; // Set your base damage here
            double calculatedDamage = stats.calculatePlayerDamage(player, baseDamage);
            player.damage(calculatedDamage); // Apply damage to the player

            player.teleport(player.getLocation().add(player.getLocation().getDirection().normalize().multiply(8)));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 1));
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
            player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation().add(0.0D, 1.0D, 0.0D), 50);
        }
    }
}
