package dev.remine.smpcore;

import dev.remine.smpcore.commands.AdminCommand;
import dev.remine.smpcore.karma.SMPKarma;
import dev.remine.smpcore.lives.SMPLives;
import dev.remine.smpcore.mechanics.GuildWhitelistMechanic;
import dev.remine.smpcore.player.SMPPlayerManager;
import net.hypixel.api.HypixelAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class SMPCore extends JavaPlugin {


    private HypixelAPI hypixelAPI;
    private String guildId;
    public SMPPlayerManager playerManager;


    @Override
    public void onEnable() {

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

        Bukkit.getPluginManager().registerEvents(new SMPKarma(this), this);
        Bukkit.getPluginManager().registerEvents(new SMPLives(this), this);


        setupMechanics();
        setupCommands();

    }

    private void setupMechanics()
    {
        Bukkit.getServer().getPluginManager().registerEvents(new GuildWhitelistMechanic(this), this);
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
        // Plugin shutdown logic
    }

    public HypixelAPI getHypixelAPI() {
        return hypixelAPI;
    }

    public String getGuildId() {
        return guildId;
    }

}
