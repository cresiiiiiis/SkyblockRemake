package org.crackpixelmobs;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomScoreboard {

    private final JavaPlugin plugin;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d");

    public CustomScoreboard(JavaPlugin plugin) {
        this.plugin = plugin;

        new BukkitRunnable() {
            @Override
            public void run() {
                updateScoreboard();
            }
        }.runTaskTimer(plugin, 0, 1200); // Update every 1 minute (20 ticks * 60 seconds)
    }
    public static String parseTime(long time) {
        long gameTime = time;
        long hours = gameTime / 1000 + 6;
        long minutes = (gameTime % 1000) * 60 / 1000;
        String ampm = "AM";
        if (hours >= 12) {
            hours -= 12;
            ampm = "PM";
        }
        if (hours >= 12) {
            hours -= 12;
            ampm = "AM";
        }
        if (hours == 0) hours = 12;
        String mm = "0" + minutes;
        mm = mm.substring(mm.length() - 2, mm.length());
        return hours + ":" + mm + " " + ampm;
    }
    private void updateScoreboard() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            String date = dateFormat.format(new Date());
            String currentTime = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
            dateFormat.format(new Date(System.currentTimeMillis()));

            Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective obj = board.registerNewObjective("test", "",  ChatColor.YELLOW + "" + ChatColor.BOLD + "SKYBLOCK" );
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);

            Score idSkyblock = obj.getScore(ChatColor.GRAY + date + ChatColor.GRAY + " mini01A");
            idSkyblock.setScore(13);
            obj.getScore(ChatColor.GREEN + "").setScore(12);
            Score monthSkyblock = obj.getScore(ChatColor.WHITE + "Early Summer 5th");
            monthSkyblock.setScore(11);
            Score timeSkyblock = obj.getScore(ChatColor.GRAY + currentTime + ChatColor.GRAY + "am" + ChatColor.YELLOW + " ☀");
            timeSkyblock.setScore(10);
            Score locSkyblock = obj.getScore(ChatColor.GRAY + "⏣" + ChatColor.AQUA + " Village");
            locSkyblock.setScore(9);
            obj.getScore(ChatColor.DARK_GREEN + "").setScore(8);
            Score coinSkyblock = obj.getScore(ChatColor.WHITE + "Purse:" + ChatColor.GOLD + PlaceholderAPI.setPlaceholders (player, " %vault_eco_balance_commas% "));
            coinSkyblock.setScore(7);
            Score bitSkyblock = obj.getScore(ChatColor.WHITE + "Bits:" + ChatColor.AQUA + " 0");
            bitSkyblock.setScore(6);
            obj.getScore(ChatColor.BLACK + "").setScore(5);
            Score taskSkyblock = obj.getScore(ChatColor.WHITE + "Objective");
            taskSkyblock.setScore(4);
            Score taskCoreSkyblock = obj.getScore(ChatColor.YELLOW + "Overthrow Dante!");
            taskCoreSkyblock.setScore(3);
            obj.getScore(ChatColor.DARK_BLUE + "").setScore(2);
            Score addressSkyblock = obj.getScore(ChatColor.YELLOW + "mc.crackpixel.org");
            addressSkyblock.setScore(1);


            player.setScoreboard(board);


        }
    }

        public void updateScoreBoard(Player player) {

            Scoreboard board = player.getScoreboard();



        }
    }