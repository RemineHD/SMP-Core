package dev.remine.smpcore.player;

import dev.remine.smpcore.SMPCore;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class SMPPlayerManager implements Listener {

    private SMPCore instance;

    private File file;
    private FileConfiguration playerDataFile;

    public SMPPlayerManager(SMPCore instance)
    {
        this.instance = instance;

        try {
            setupPlayersStorage();
            getPlayerDataFile().options().copyDefaults(true);
            saveDataFile();
        } catch (Exception exception)
        {
            instance.getLogger().warning("Error loading players.yml. Stopping the server.");
            exception.printStackTrace();
            instance.getServer().shutdown();

        }

        Bukkit.getPluginManager().registerEvents(this, instance);

    }

    private void setupPlayersStorage() throws IOException {

        file = new File(instance.getDataFolder(), "players.yml");
        if (!file.exists())
            file.createNewFile();

        playerDataFile = YamlConfiguration.loadConfiguration(file);
    }

    private FileConfiguration getPlayerDataFile()
    {
        return playerDataFile;
    }

    private void saveDataFile() throws IOException {
        playerDataFile.save(file);
    }


    public SMPPlayer getPlayerOrCreate(UUID uniqueId)
    {
        SMPPlayer player = new SMPPlayer(uniqueId);

        if (getPlayer(uniqueId) != null) return getPlayer(uniqueId);

        player.setKarma(instance.getConfig().getInt("DefaultKarma"));
        player.setLives(instance.getConfig().getInt("DefaultLives"));

        savePlayer(player);
        instance.getLogger().info("new player created: " + player.getPlayerId());
        return player;

    }

    public SMPPlayer getPlayer(UUID uniqueId)
    {
        if (getPlayerDataFile().getString(uniqueId.toString()) != null)
        {
            SMPPlayer smpPlayer = new SMPPlayer(uniqueId);
            smpPlayer.setKarma(getPlayerDataFile().getInt(uniqueId + ".karma"));
            smpPlayer.setLives(getPlayerDataFile().getInt(uniqueId + ".lives"));
            smpPlayer.setTeamId(getPlayerDataFile().getString(uniqueId + ".teamId"));
            return smpPlayer;
        }
        return null;
    }

    public void savePlayer(SMPPlayer smpPlayer)
    {

        getPlayerDataFile().set(smpPlayer.getPlayerId().toString(), "");
        getPlayerDataFile().set(smpPlayer.getPlayerId() + ".teamId", smpPlayer.getTeamId());
        getPlayerDataFile().set(smpPlayer.getPlayerId() +  ".karma", smpPlayer.getKarma());
        getPlayerDataFile().set(smpPlayer.getPlayerId() +  ".lives", smpPlayer.getLives());

        try {
            saveDataFile();
        } catch (IOException exception)
        {
            instance.getLogger().warning("Error saving player.");
            exception.printStackTrace();
        }

    }


    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent playerJoinEvent)
    {

        if (getPlayer(playerJoinEvent.getPlayer().getUniqueId()) == null)
            getPlayerOrCreate(playerJoinEvent.getPlayer().getUniqueId());

        SMPPlayer player = getPlayer(playerJoinEvent.getPlayer().getUniqueId());
        playerJoinEvent.getPlayer().sendMessage("\n" +
                "Karma: " + player.getKarma() +
                "\n Lives: " + player.getLives() +
                "\n TeamId: " + player.getTeamId() +
                "\n");

    }

}
