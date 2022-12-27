package dev.remine.smpcore.lives.commands;

import dev.remine.smpcore.SMPCore;
import dev.remine.smpcore.player.SMPPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class LivesCommand implements CommandExecutor {

    private SMPCore instance;

    public LivesCommand(SMPCore instance)
    {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1)
        {

            if (Bukkit.getPlayer(args[0]) == null) {
                sender.sendMessage(ChatColor.RED + "Unable to find player.");
                return true;
            }
            SMPPlayer smpPlayer = instance.playerManager.getPlayer(Bukkit.getPlayer(args[0]).getUniqueId());
            if (smpPlayer != null)
            {
                sender.sendMessage(ChatColor.GREEN + Bukkit.getPlayer(args[0]).getName() + " have " + smpPlayer.getLives() + " lives.");
                return true;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "Unable to find player.");
                return true;
            }
        } else
        {
            SMPPlayer smpPlayer = instance.playerManager.getPlayer(Bukkit.getPlayer(sender.getName()).getUniqueId());
            if (smpPlayer != null)
            {
                sender.sendMessage(ChatColor.GREEN + "You have " + smpPlayer.getLives() + " lives remaining.");
                return true;
            }
            sender.sendMessage(ChatColor.RED + "An error has occurred. try again later");
        }
        return true;
    }

}
