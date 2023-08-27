package org.crackpixelmobs.items;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.crackpixelmobs.Rarity;
import org.crackpixelmobs.Stats;

import java.util.ArrayList;
import java.util.List;

public class SuperiorArmor {

    public static ItemStack createDragonHelmet() {
        ItemStack helmet = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta helmetMeta = (SkullMeta) helmet.getItemMeta();

        // Set the skull owner
        helmetMeta.setOwner("scroller"); // Replace with the skull owner's name

        // Set other attributes and lore here

        helmet.setItemMeta(helmetMeta);
        return helmet;
    }

    public static ItemStack createArmorPiece(Material material, String displayName, int health, int defense, int strength, int speed, int critChance, int critDamage, int intelligence) {
        ItemStack armorPiece = new ItemStack(material);
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) armorPiece.getItemMeta();
        armorMeta.setDisplayName(ChatColor.YELLOW + displayName);
        armorMeta.setColor(Color.YELLOW);

        Rarity rarity = Rarity.LEGENDARY; // Set rarity to LEGENDARY
        List<String> lore = new ArrayList<>();
        lore.add(rarity.getRarityColor() + "" + ChatColor.BOLD + rarity.getName()); // Add rarity lore
        armorMeta.setLore(lore); // Set the lore to the armor piece's meta

        Stats stats = new Stats();
        stats.addAttributeToArmorPiece(armorPiece, Stats.Attribute.HEALTH, health);
        stats.addAttributeToArmorPiece(armorPiece, Stats.Attribute.DEFENSE, defense);
        stats.addAttributeToArmorPiece(armorPiece, Stats.Attribute.STRENGTH, strength);
        stats.addAttributeToArmorPiece(armorPiece, Stats.Attribute.SPEED, speed);
        stats.addAttributeToArmorPiece(armorPiece, Stats.Attribute.CRIT_CHANCE, critChance);
        stats.addAttributeToArmorPiece(armorPiece, Stats.Attribute.CRIT_DAMAGE, critDamage);
        stats.addAttributeToArmorPiece(armorPiece, Stats.Attribute.INTELLIGENCE, intelligence);

        armorPiece.setItemMeta(armorMeta);
        return armorPiece;
    }

    public static ItemStack createChestplate() {
        return createArmorPiece(
                Material.LEATHER_CHESTPLATE,
                "Superior Armor Chestplate",
                150, 190, 10, 3, 2, 10, 25
        );
    }

    public static ItemStack createLeggings() {
        return createArmorPiece(
                Material.LEATHER_LEGGINGS,
                "Superior Armor Leggings",
                0, 170, 10, 3, 2, 10, 25
        );
    }

    public static ItemStack createBoots() {
        return createArmorPiece(
                Material.LEATHER_BOOTS,
                "Superior Armor Boots",
                80, 110, 10, 3, 2, 10, 25
        );
    }
}
