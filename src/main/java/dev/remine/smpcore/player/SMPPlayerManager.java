package dev.remine.smpcore.player;

import dev.remine.smpcore.SMPCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class SMPPlayerManager {

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

        player.setTeamId(UUID.fromString("na"));
        player.setKarma(instance.getConfig().getInt("DefaultKarma"));
        player.setLives(instance.getConfig().getInt("DefaultLives"));

        savePlayer(player);
        instance.getLogger().info("new player created: " + player.getPlayerId());
        return player;

    }

    public SMPPlayer getPlayer(UUID uniqueId)
    {
        SMPPlayer player = new SMPPlayer(uniqueId);

        if (getPlayerDataFile().getString("players." + uniqueId) != null)
        {
            player.setTeamId(UUID.fromString(getPlayerDataFile().getString("players." + uniqueId + ".teamId")));
            player.setKarma(getPlayerDataFile().getInt("players." + uniqueId + ".karma"));
            player.setLives(getPlayerDataFile().getInt("players." + uniqueId + ".lives"));
            return player;
        }
        return null;
    }

    public void savePlayer(SMPPlayer smpPlayer)
    {
        getPlayerDataFile().set("players.", smpPlayer.getPlayerId());
        getPlayerDataFile().set("players." + smpPlayer.getPlayerId() + ".teamId", smpPlayer.getTeamId());
        getPlayerDataFile().set("players." + smpPlayer.getPlayerId() + ".karma", smpPlayer.getKarma());
        getPlayerDataFile().set("players." + smpPlayer.getPlayerId() + ".lives", smpPlayer.getLives());

        try {
            saveDataFile();
        } catch (IOException exception)
        {
            instance.getLogger().warning("Error saving player.");
            exception.printStackTrace();
        }

    }

}
