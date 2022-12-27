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
            SMPPlayer smpPlayer = instance.playerManager.getPlayer(Bukkit.getPlayer(args[2]).getUniqueId());
            if (smpPlayer == null)
            {
                sender.sendMessage(ChatColor.RED + "Unable to find player.");
                return false;
            }
            smpPlayer.setLives(lives);
            instance.playerManager.savePlayer(smpPlayer);
            return true;
        }
        else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: /admin setlives <amount> <player>"));
        return false;
    }

}