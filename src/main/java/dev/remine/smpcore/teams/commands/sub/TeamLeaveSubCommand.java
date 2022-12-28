package dev.remine.smpcore.teams.commands.sub;

import dev.remine.smpcore.SMPCore;
import dev.remine.smpcore.teams.types.Team;
import dev.remine.smpcore.teams.types.TeamMember;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamLeaveSubCommand {

    public boolean execute(SMPCore instance, CommandSender sender, Command command, String[] args)
    {
        if (args.length < 2)
        {

            if (Bukkit.getPlayer(sender.getName()) != null)
            {

                Player executor = Bukkit.getPlayer(sender.getName());
                Team team = instance.teamsManager.getTeamByMember(executor.getUniqueId());
                if (team != null)
                {

                    for (TeamMember member : team.getMembers())
                    {
                        if (member.getMemberId().equals(executor.getUniqueId()))
                        {
                            if (member.getMemberRole() == TeamMember.Role.LEADER)
                            {
                                team.removeMember(member);
                                for (TeamMember teamMember : team.getMembers())
                                {
                                    teamMember.setMemberRole(TeamMember.Role.LEADER);
                                    if (Bukkit.getPlayer(teamMember.getMemberId()) != null)
                                        Bukkit.getPlayer(teamMember.getMemberId()).sendMessage(ChatColor.GOLD + "You are the new Team Leader.");
                                    return true;
                                }
                                sender.sendMessage(ChatColor.GOLD + "You left the team.");
                                executor.setDisplayName(ChatColor.GRAY + executor.getName());
                                return true;
                            }
                            sender.sendMessage(ChatColor.GOLD + "You left the team.");
                            executor.setDisplayName(ChatColor.GRAY + executor.getName());
                            team.removeMember(member);
                            return true;
                        }
                    }
                }
                else {
                    sender.sendMessage(ChatColor.RED + "You cannot leave the team because you are not part of any team.");
                    return true;
                }
            }
        }
        sender.sendMessage(ChatColor.RED + "Usage: /team leave");
        return true;
    }

}
