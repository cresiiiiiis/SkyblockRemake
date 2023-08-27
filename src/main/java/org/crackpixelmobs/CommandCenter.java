package org.crackpixelmobs;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.crackpixelmobs.items.Hyperion;
import org.crackpixelmobs.mobs.ZealotSpawner;
import org.crackpixelmobs.mobs.ZombieSpawner;
import org.crackpixelmobs.items.Terminator;
import org.crackpixelmobs.Slayers;
import org.crackpixelmobs.items.Boomerang;

public class CommandCenter extends JavaPlugin implements CommandExecutor {

    private ZombieSpawner zombieSpawner;
    private ZealotSpawner zealotSpawner;
    private Stats playerStats;
    private ItemsGui itemsGui;
    private Slayers slayers;
    private Boomerang boomerang; // Add Boomerang reference

    private CustomScoreboard customScoreboard;

    @Override
    public void onEnable() {
        new CustomScoreboard(this);
        new DamageIndicator(this);


        playerStats = new Stats();
        Hyperion hyperion = new Hyperion(this);
        Terminator terminatorInstance = new Terminator(this);
        boomerang = new Boomerang(this); // Initialize Boomerang instance with the JavaPlugin instance
        getServer().getPluginManager().registerEvents(new Boomerang(this), this);

        itemsGui = new ItemsGui(this, playerStats, hyperion, terminatorInstance, boomerang); // Pass Boomerang instance
        slayers = new Slayers();

        // Initialize the Mayors instance

        getCommand("cpm").setExecutor(this);
        getCommand("cpa").setExecutor(this); // Register the /cpa command
        getCommand("itemsgui").setExecutor(this); // Register the /itemsgui command
        getCommand("slayers").setExecutor(this);
        getCommand("worldgen").setExecutor(this);
        getCommand("statstest").setExecutor(this);

        zombieSpawner = new ZombieSpawner(this); // Pass the instance of JavaPlugin
        getServer().getPluginManager().registerEvents(zombieSpawner, this); // Register ZombieSpawner as a listener

        zealotSpawner = new ZealotSpawner(this); // Pass the instance of JavaPlugin
        getServer().getPluginManager().registerEvents(zealotSpawner, this); // Register ZealotSpawner as a listener
    }

    @Override
    public void onDisable() {
        // Perform cleanup if needed...
    }

    private void setGameRules() {
        // Set game rules
        Bukkit.getWorlds().forEach(world -> {
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false); // Always day
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);  // No rain
        });
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("cpm")) {
            // Handle the /cpm command here
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("zombie")) {
                    // Spawn a Zombie using the instance of ZombieSpawner
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        zombieSpawner.spawnZombie(player);
                    }
                } else if (args[0].equalsIgnoreCase("special_zealot")) {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        zealotSpawner.spawnSpecialZealot(player);
                    }
                } else {
                    sender.sendMessage("Usage: /cpm <mob name>");
                }
            } else {
                sender.sendMessage("Usage: /cpm <mob name>");
            }
            return true;
        } else if (command.getName().equalsIgnoreCase("cpa")) {
            if (args.length == 2 && sender instanceof Player) {
                Player player = (Player) sender;
                String attributeName = args[0];
                double attributeValue;

                try {
                    attributeValue = Double.parseDouble(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("Invalid attribute value.");
                    return true;
                }

                Stats.Attribute attribute = Stats.Attribute.valueOf(attributeName.toUpperCase());

                playerStats.setAttribute(player, attribute, attributeValue);
                sender.sendMessage("Attribute " + attributeName + " set to " + attributeValue);
                return true;
            }
            return false;
        } else if (command.getName().equalsIgnoreCase("itemsgui")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                itemsGui.openItemsGui(player);
            } else if (command.getName().equalsIgnoreCase("statstest")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    player.sendMessage("RAW STAT OUTPUT");
                    player.sendMessage(ChatColor.RED + "Health: " + String.valueOf(playerStats.getAttributeValue(Stats.Attribute.HEALTH)));
                }
            } else if (command.getName().equalsIgnoreCase("worldtest")) {
                WorldCreator wc = new WorldCreator("neu");
                Player s = (Player) sender;
                Location p = s.getLocation();
                World w = p.getWorld();


                wc.environment(World.Environment.NORMAL);
                wc.type(WorldType.NORMAL);

                System.out.println("a.1 coretask running...");
                w = Bukkit.createWorld(wc);
                System.out.println("a.1 coretask completed sucessfully...");

                return true;
            }
        }
        return true;
    }
}
