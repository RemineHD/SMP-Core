package dev.remine.smpcore.teams.commands.sub;

import dev.remine.smpcore.SMPCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class TeamCreateSubCommand {

    public boolean execute(SMPCore instance, CommandSender sender, Command command, String[] args)
    {
        if (args.length == 2)
        {
            if (Bukkit.getPlayer(sender.getName()) != null)
            {
                if (instance.teamsManager.getTeamByMember(Bukkit.getPlayer(sender.getName()).getUniqueId()) != null)
                {
                    sender.sendMessage(ChatColor.RED + "Seems like you already are a member of a team. Please use /team leave and try again.");
                    return true;
                }
                if (instance.teamsManager.createTeam(sender.getName(), Bukkit.getPlayer(sender.getName()).getUniqueId(), args[1], ChatColor.GRAY))
                {
                    sender.sendMessage(ChatColor.GREEN + "Successfully created the team: " + ChatColor.GRAY + args[1]);
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "An error has occurred while creating your team.");
                return true;
            } else
            {
                sender.sendMessage(ChatColor.RED + "An error has occurred. try again later");
                return true;
            }
        }
        sender.sendMessage(ChatColor.RED + "Usage: /team create <Team Name>");
        return true;
    }

}
