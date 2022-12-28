package dev.remine.smpcore.teams.commands.sub;

import dev.remine.smpcore.SMPCore;
import dev.remine.smpcore.teams.types.Team;
import dev.remine.smpcore.teams.types.TeamMember;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamRenameSubCommand {

    public boolean execute(SMPCore instance, CommandSender sender, Command command, String[] args)
    {

        if (args.length == 2)
        {

            if (Bukkit.getPlayer(sender.getName()) != null)
            {

                Player executor = Bukkit.getPlayer(sender.getName());
                Team team = instance.teamsManager.getTeamByMember(executor.getUniqueId());
                if (team != null)
                {

                    for (TeamMember member : team.getMembers())
                    {
                        if (member.getMemberId().equals(executor.getUniqueId()) && member.getMemberRole() == TeamMember.Role.LEADER)
                        {
                           if (instance.teamsManager.isNameUsed(args[1]))
                            {
                                sender.sendMessage(ChatColor.RED + "That name is already taken.");
                                return true;
                            } else {
                            team.setTeamName(args[1]);
                            sender.sendMessage(ChatColor.GREEN + "Team name updated!");
                            return true;
                           }
                        } else {
                            sender.sendMessage(ChatColor.RED + "You have to be the team leader to change the team name.");
                            return true;
                        }
                    }
                }
                sender.sendMessage(ChatColor.RED + "You have to be in a team in order to change the team name.");
                return true;
            }
        }
        sender.sendMessage(ChatColor.RED + "Usage: /team rename <name>");
        return true;
    }

}
