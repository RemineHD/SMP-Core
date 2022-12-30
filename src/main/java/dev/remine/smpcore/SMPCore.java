package dev.remine.smpcore;

import dev.remine.smpcore.commands.AdminCommand;
import dev.remine.smpcore.karma.SMPKarma;
import dev.remine.smpcore.lives.SMPLives;
import dev.remine.smpcore.mechanics.GuildWhitelistMechanic;
import dev.remine.smpcore.player.SMPPlayer;
import dev.remine.smpcore.player.SMPPlayerManager;
import dev.remine.smpcore.teams.SMPTeamsManager;
import dev.remine.smpcore.teams.types.Team;
import dev.remine.smpcore.teams.types.TeamMember;
import net.hypixel.api.HypixelAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import org.inventivetalent.menubuilder.MenuBuilderPlugin;

import java.io.IOException;
import java.util.UUID;

public final class SMPCore extends JavaPlugin {

    static {
        ConfigurationSerialization.registerClass(Team.class, "Team");
        ConfigurationSerialization.registerClass(TeamMember.class, "TeamMember");
        ConfigurationSerialization.registerClass(SMPPlayer.class, "SMPPlayer");
    }


    private HypixelAPI hypixelAPI;
    private String guildId;
    public SMPPlayerManager playerManager;

    public SMPTeamsManager teamsManager;

    public MenuBuilderPlugin menuBuilderPlugin;

    @Override
    public void onEnable() {

        menuBuilderPlugin = (MenuBuilderPlugin) Bukkit.getPluginManager().getPlugin("MenuBuilder");

        setupConfig();
        FileConfiguration configuration = getConfig();

        this.guildId = configuration.getString("GuildId");
        String apiKey = configuration.getString("ApiKey");

        if (guildId == null || apiKey == null)
        {
            getLogger().warning("The Plugin is not setup correctly. Please check the configuration.");
            Bukkit.getServer().shutdown();
            return;
        }

        hypixelAPI = new HypixelAPI(UUID.fromString(apiKey));
        playerManager = new SMPPlayerManager(this);
        teamsManager = new SMPTeamsManager(this);


        Bukkit.getPluginManager().registerEvents(new SMPKarma(this), this);
        Bukkit.getPluginManager().registerEvents(new SMPLives(this), this);


        setupMechanics();
        setupCommands();

    }

    private void setupMechanics()
    {
        //Bukkit.getServer().getPluginManager().registerEvents(new GuildWhitelistMechanic(this), this);
    }

    private void setupCommands()
    {
        this.getCommand("admin").setExecutor(new AdminCommand(this));
    }

    private void setupConfig()
    {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        try {
            teamsManager.handleSave();
            playerManager.handleSave();
        } catch (IOException e) {
            getLogger().warning("Error saving teams.yml");
            throw new RuntimeException(e);
        }
    }

    public HypixelAPI getHypixelAPI() {
        return hypixelAPI;
    }

    public String getGuildId() {
        return guildId;
    }

}
