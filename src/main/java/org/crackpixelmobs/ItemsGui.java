package org.crackpixelmobs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.crackpixelmobs.items.AOTJItem;
import org.crackpixelmobs.items.AOTE;
import org.crackpixelmobs.items.Hyperion;
import org.crackpixelmobs.items.SuperiorArmor;
import org.crackpixelmobs.items.Terminator;
import org.crackpixelmobs.items.Boomerang;

public class ItemsGui implements Listener {

    private final JavaPlugin plugin;
    private final AOTJItem aotjItem;
    private final Stats stats;
    private final Hyperion hyperion;
    private final Terminator terminator;
    private final Boomerang boomerang; // Add Boomerang reference

    public ItemsGui(JavaPlugin plugin, Stats stats, Hyperion hyperion, Terminator terminator, Boomerang boomerang) {
        this.plugin = plugin;
        this.aotjItem = new AOTJItem(plugin);
        this.stats = stats;
        this.hyperion = hyperion;
        this.terminator = terminator;
        this.boomerang = boomerang; // Assign the Boomerang instance
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void openItemsGui(Player player) {
        Inventory gui = Bukkit.createInventory(null, 18, ChatColor.BOLD + "Crackpixel Items");

        ItemStack aotjSword = aotjItem.createAspectOfTheJerry();
        gui.addItem(aotjSword);

        AOTE aoteItem = new AOTE(plugin, stats);
        ItemStack aoteSword = aoteItem.createAspectOfTheEnd();
        gui.addItem(aoteSword);

        ItemStack hyperionSword = hyperion.createHyperion();
        gui.addItem(hyperionSword);

        ItemStack terminatorBow = terminator.createTerminatorBow();
        gui.addItem(terminatorBow);

        // Add Boomerang item to the GUI
        ItemStack boomerangItem = boomerang.createBoomerang();
        gui.addItem(boomerangItem);

        // Add the superior armor pieces to the GUI
        ItemStack dragonHelmet = SuperiorArmor.createDragonHelmet();
        gui.addItem(dragonHelmet);

        ItemStack chestplate = SuperiorArmor.createChestplate();
        gui.addItem(chestplate);

        ItemStack leggings = SuperiorArmor.createLeggings();
        gui.addItem(leggings);

        ItemStack boots = SuperiorArmor.createBoots();
        gui.addItem(boots);

        player.openInventory(gui);
    }


    // Placeholder method for demonstration
    public void someMethodInItemsGui() {
        // Implement your logic here
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedInventory == null || clickedItem == null) {
            return;
        }

        if (clickedInventory.getType() == InventoryType.CHEST) {
            String inventoryTitle = event.getView().getTitle();
            if (inventoryTitle.equals(ChatColor.BOLD + "Crackpixel Items")) {
                event.setCancelled(true);
                giveAbilityToPlayer(player, clickedItem); // Give the ability to the player
                player.getInventory().addItem(clickedItem); // Add the clicked item to the player's inventory
            }
        }
    }

    // New method to handle dragging items from GUI
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getInventory().getType() == InventoryType.CHEST) {
            String inventoryTitle = event.getView().getTitle();
            if (inventoryTitle.equals(ChatColor.BOLD + "Crackpixel Items")) {
                event.setCancelled(true);
            }
        }
    }

    private void giveAbilityToPlayer(Player player, ItemStack item) {
        // Implement your ability logic here based on the item
    }
}
