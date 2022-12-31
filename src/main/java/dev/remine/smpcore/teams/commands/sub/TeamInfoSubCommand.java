package dev.remine.smpcore.teams.commands.sub;

import dev.remine.smpcore.SMPCore;
import dev.remine.smpcore.teams.types.Team;
import dev.remine.smpcore.teams.types.TeamMember;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class TeamInfoSubCommand {

    public boolean execute(SMPCore instance, CommandSender sender, Command command, String[] args)
    {

        if (args.length == 2)
        {
            for (Team team : instance.teamsManager.getTeams())
            {
                if (team.getTeamName().equalsIgnoreCase(args[2]))
                {
                    getTeam(sender, team);
                    return true;
                }
            }

        } else
        {
            if (instance.teamsManager.getTeamByMember(Bukkit.getPlayer(sender.getName()).getUniqueId()) != null)
            {
                Team team = instance.teamsManager.getTeamByMember(Bukkit.getPlayer(sender.getName()).getUniqueId());
                getTeam(sender, team);
            } else {
                sender.sendMessage(ChatColor.RED + "You are not in a team.");
            }
        }
        return true;
    }


    private void getTeam(CommandSender sender, Team team)
    {
        String leaderName = "";
        List<String> membersName = new ArrayList<>();

        for (TeamMember member : team.getMembers())
        {

            if (member.getMemberRole() == TeamMember.Role.LEADER)
                leaderName = member.playerName;
            else
                membersName.add(member.playerName);

        }

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "\n" +
                        "&a------------------------------" +
                        "\n&" + team.getTeamColor().getCode() + team.getTeamName() + " | &fInfo" +
                        "\n" +
                        "\n &fMember Count: &2" + team.getMembers().size() +
                        "\n &fTeam Leader: &2" + leaderName +
                        "\n &fTeam Members: &2" + String.join("&f,&2 ", membersName) +
                        "\n" +
                        "\n&a------------------------------" +
                        "\n"));
    }

}
