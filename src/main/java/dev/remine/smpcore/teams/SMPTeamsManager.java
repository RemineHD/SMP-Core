package dev.remine.smpcore.teams;

import dev.remine.smpcore.SMPCore;
import dev.remine.smpcore.teams.commands.TeamCommand;
import dev.remine.smpcore.teams.listeners.TeamPlayerCombat;
import dev.remine.smpcore.teams.listeners.TeamPlayerJoin;
import dev.remine.smpcore.teams.types.Team;
import dev.remine.smpcore.teams.types.TeamMember;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SMPTeamsManager {

    private SMPCore instance;

    private List<Team> teams = new ArrayList<>();

    private File file;

    private FileConfiguration teamsDataFile;

    public SMPTeamsManager(SMPCore instance)
    {
        this.instance = instance;

        try {
            setupTeamsStorage();
            teamsDataFile.getList("teams", teams);
        } catch (Exception exception)
        {
            instance.getLogger().warning("Error loading teams.yml. Stopping the server.");
            exception.printStackTrace();
            instance.getServer().shutdown();
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    handleSave();
                } catch (IOException e) {
                    instance.getLogger().warning("Error saving teams.yml");
                    throw new RuntimeException(e);
                }
            }
        }.runTaskTimerAsynchronously(instance, 0, 36000);

        Bukkit.getPluginManager().registerEvents(new TeamPlayerCombat(instance), instance);
        Bukkit.getPluginManager().registerEvents(new TeamPlayerJoin(instance), instance);
        Objects.requireNonNull(instance.getCommand("team")).setExecutor(new TeamCommand(instance));

    }

    public void handleSave() throws IOException {
        getTeamsDataFile().set("teams", Collections.singletonList(teams));
        saveDataFile();
    }

    private void setupTeamsStorage() throws IOException {
        file = new File(instance.getDataFolder(), "teams.yml");
        if (!file.exists())
            file.createNewFile();

        teamsDataFile = YamlConfiguration.loadConfiguration(file);
    }

    private FileConfiguration getTeamsDataFile()
    {
        return teamsDataFile;
    }

    private void saveDataFile() throws IOException {
        teamsDataFile.save(file);
    }

    public Team getTeam(UUID teamId)
    {

        for (Team team : teams)
        {
            if (team.getTeamId().equals(teamId))
                return team;
        }
        return null;
    }

    public boolean createTeam(String ownerName, UUID ownerId, String name, ChatColor color)
    {

        if (isNameUsed(name))
        {
            Bukkit.getPlayer(ownerId).sendMessage(ChatColor.RED + "That name is already taken.");
            return false;
        }
        Team team = new Team(name, color);
        team.addMember(new TeamMember(ownerName, ownerId, TeamMember.Role.LEADER));
        teams.add(team);
        instance.getLogger().info("New Team Registered: " + name + " (" + team.getTeamId() + ")");
        return true;
    }

    public Team getTeamByMember(UUID memberId)
    {
        for (Team team : teams)
            for (TeamMember member : team.getMembers())
                if (member.getMemberId().equals(memberId))
                    return team;
        return null;
    }

    public boolean isNameUsed(String name)
    {
        for (Team team : teams)
        {
            if (team.getTeamName().equalsIgnoreCase(name))
            {
                return true;
            }
        }
        return false;
    }

}
