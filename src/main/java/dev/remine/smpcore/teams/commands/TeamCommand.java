package dev.remine.smpcore.teams.commands;

import dev.remine.smpcore.SMPCore;
import dev.remine.smpcore.teams.commands.sub.*;
import dev.remine.smpcore.teams.types.Team;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class TeamCommand implements CommandExecutor {

    private SMPCore instance;

    public TeamCommand(SMPCore instance)
    {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length >= 1)
        {

            if (args[0].equalsIgnoreCase("list"))
            {
                sender.sendMessage("Layout: TEAM NAME | (MEMBERS)");
                for (Team team : instance.teamsManager.getTeams())
                {
                    sender.sendMessage(team.getTeamName() + " (" + team.getMembers().size() + ")");
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("create"))
                return new TeamCreateSubCommand().execute(instance, sender, command, args);

            if (args[0].equalsIgnoreCase("invite"))
                return new TeamInviteSubCommand().execute(instance, sender, command, args);

            if (args[0].equalsIgnoreCase("kick"))
                return new TeamKickSubCommand().execute(instance, sender, command, args);

            if (args[0].equalsIgnoreCase("color"))
                return new TeamColorSubCommand().execute(instance, sender, command, args);

            if (args[0].equalsIgnoreCase("rename"))
                return new TeamRenameSubCommand().execute(instance, sender, command, args);

            if (args[0].equalsIgnoreCase("leave"))
                return new TeamLeaveSubCommand().execute(instance, sender, command, args);
        }

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "\n" +
                "&a------------------------------" +
                "\n&2Team Commands:" +
                "\n &f/team create (name)" +
                "\n &f/team invite (player)" +
                "\n &f/team kick (player)" +
                "\n &f/team color (color)" +
                "\n &f/team rename (name)" +
                "\n &f/team leave" +
                "\n&a------------------------------" +
                "\n"));

        return true;
    }

}
