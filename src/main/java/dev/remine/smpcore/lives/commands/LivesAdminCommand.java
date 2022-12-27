package dev.remine.smpcore.lives.commands;

import dev.remine.smpcore.SMPCore;
import dev.remine.smpcore.player.SMPPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class LivesAdminCommand {

    public boolean execute(@NotNull SMPCore instance, @NotNull CommandSender sender, @NotNull Command command, @NotNull String[] args) {

        if (args.length >= 3)
        {
            int lives = Integer.parseInt(args[1]);
            if (Bukkit.getPlayer(args[2]) == null) {
                sender.sendMessage(ChatColor.RED + "Unable to find player.");
                return true;
            }
            SMPPlayer smpPlayer = instance.playerManager.getPlayer(Bukkit.getPlayer(args[2]).getUniqueId());
            if (smpPlayer == null)
            {
                sender.sendMessage(ChatColor.RED + "Unable to find player.");
                return true;
            }
            smpPlayer.setLives(lives);
            instance.playerManager.savePlayer(smpPlayer);
            sender.sendMessage(ChatColor.GREEN + "Successfully updated " + Bukkit.getPlayer(args[2]).getName() + "'s lives to: " + lives);
            return true;
        }
        else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: /admin setlives <amount> <player>"));
        return true;
    }

}
