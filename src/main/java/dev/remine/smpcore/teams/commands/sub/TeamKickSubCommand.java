package dev.remine.smpcore.teams.commands.sub;

import dev.remine.smpcore.SMPCore;
import dev.remine.smpcore.teams.types.Team;
import dev.remine.smpcore.teams.types.TeamMember;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamKickSubCommand {

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
                        if (member.getMemberId().equals(executor.getUniqueId()) && member.getMemberRole() == TeamMember.Role.LEADER)
                        {

                            for (TeamMember kickedMember : team.getMembers())
                            {
                                if (kickedMember.playerName.equalsIgnoreCase(args[1]))
                                {
                                    if (kickedMember.getMemberId().equals(executor.getUniqueId()))
                                    {
                                        sender.sendMessage(ChatColor.RED + "You can't kick yourself!");
                                        return true;
                                    } else {
                                        team.removeMember(kickedMember);
                                        sender.sendMessage(ChatColor.GREEN + "Successfully kicked " + kickedMember.playerName);

                                        if (Bukkit.getPlayer(kickedMember.getMemberId()) != null)
                                        {
                                            instance.teamsManager.setupPlayerTeam(Bukkit.getPlayer(kickedMember.getMemberId()), null);
                                            Bukkit.getPlayer(kickedMember.getMemberId()).sendMessage(ChatColor.RED + "You have been kicked off the team: " + team.getTeamName());
                                        }
                                        return true;
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.RED + "Unable to find Team Member.");
                                    return true;
                                }
                            }

                        } else
                        {
                            sender.sendMessage(ChatColor.RED + "You have to be the team leader in order to kick someone.");
                            return true;
                        }
                }
                else
                {
                    sender.sendMessage(ChatColor.RED + "You have to be in a Team in order to kick someone.");
                    return true;
                }
            }
        }
        sender.sendMessage(ChatColor.RED + "Usage: /team kick <player>");
        return true;
    }

}
