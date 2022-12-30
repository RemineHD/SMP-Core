package dev.remine.smpcore.teams.commands.sub;

import dev.remine.smpcore.SMPCore;
import dev.remine.smpcore.teams.types.Team;
import dev.remine.smpcore.teams.types.TeamMember;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.inventivetalent.menubuilder.chat.ChatListener;
import org.inventivetalent.menubuilder.chat.ChatMenuBuilder;
import org.inventivetalent.menubuilder.chat.LineBuilder;

public class TeamInviteSubCommand {

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

                            if (Bukkit.getPlayer(args[1]) != null)
                            {
                                Player invited = Bukkit.getPlayer(args[1]);
                                if (instance.teamsManager.getTeamByMember(invited.getUniqueId()) != null)
                                {
                                    sender.sendMessage(ChatColor.RED + "That player is already member of a team.");
                                    return true;
                                }

                                new ChatMenuBuilder().withLine(new LineBuilder().append(player -> {
                                    team.addMember(new TeamMember(invited.getName(), invited.getUniqueId(), TeamMember.Role.MEMBER));
                                    instance.teamsManager.setupPlayerTeam(invited, team);
                                    for (TeamMember teamMember : team.getMembers())
                                    {
                                        if (Bukkit.getPlayer(teamMember.getMemberId()) != null)
                                            Bukkit.getPlayer(teamMember.getMemberId()).sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l[◆] &e" + invited.getName() + " &fjoined the Team."));
                                    }
                                }, new TextComponent(ChatColor.translateAlternateColorCodes('&', "&e" +executor.getName() + " &finvited you to the team: " + team.getTeamColor() + team.getTeamName() + "&f. &aClick here to accept.")))).show(invited);
                                executor.sendMessage(ChatColor.GREEN + "Successfully invited " + invited.getName() + " to the team.");
                                return true;
                            } else {
                                sender.sendMessage(ChatColor.RED + "Unable to find player. Is he connected?");
                                return true;
                            }
                        } else
                        {
                            sender.sendMessage(ChatColor.RED + "You have to be the leader to invite players to the team.");
                            return true;
                        }
                } else {
                    sender.sendMessage(ChatColor.RED + "You need to be in a Team to invite people.");
                    return true;
                }
            }
        }
        sender.sendMessage(ChatColor.RED + "Usage: /team invite <player>");
        return true;
    }

}
