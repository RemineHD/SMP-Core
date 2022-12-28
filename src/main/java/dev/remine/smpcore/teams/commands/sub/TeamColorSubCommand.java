package dev.remine.smpcore.teams.commands.sub;

import dev.remine.smpcore.SMPCore;
import dev.remine.smpcore.teams.types.Team;
import dev.remine.smpcore.teams.types.TeamMember;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamColorSubCommand {

    public boolean execute(SMPCore instance, CommandSender sender, Command command, String[] args)
    {

        if (args.length == 2)
        {

            try {
                if (ChatColor.valueOf(args[1]) != null)
                {

                    ChatColor color = ChatColor.valueOf(args[1]);
                    if (color == ChatColor.RED && !sender.isOp())
                    {
                        sender.sendMessage(ChatColor.RED + "Color red is reserved for Admins.");
                        return true;
                    }

                    if (Bukkit.getPlayer(sender.getName()) != null)
                    {

                        Player player = Bukkit.getPlayer(sender.getName());
                        Team team = instance.teamsManager.getTeamByMember(player.getUniqueId());
                        if (team != null)
                        {

                            for (TeamMember member : team.getMembers())
                                if (member.getMemberId().equals(player.getUniqueId()) && member.getMemberRole() == TeamMember.Role.LEADER)
                                {
                                    team.setTeamColor(color);
                                    sender.sendMessage(ChatColor.GREEN + "Successfully updated team color to: " + color + color.toString());
                                    return true;
                                } else
                                {
                                    sender.sendMessage(ChatColor.RED + "You need to be the Team Leader to change the color.");
                                    return true;
                                }
                        } else {
                            sender.sendMessage(ChatColor.RED + "You need to be part of a Team in order to change the team color.");
                            return true;
                        }

                    }

                }
            } catch (Exception e)
            {
                sender.sendMessage(ChatColor.RED + "Color Options: BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, LIGHT_PURPLE, YELLOW, WHITE");
                return true;
            }
        }
        sender.sendMessage(ChatColor.RED + "Usage: /team color (color) " +
                "\n Color Options: BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, LIGHT_PURPLE, YELLOW, WHITE" +
                "\n Note: RED color is reserved for Admins.");

        return true;
    }

}
