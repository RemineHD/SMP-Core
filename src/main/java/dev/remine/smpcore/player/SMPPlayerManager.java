package dev.remine.smpcore.player;

import dev.remine.smpcore.SMPCore;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class SMPPlayerManager implements Listener {

    private SMPCore instance;

    private File file;
    private FileConfiguration playerDataFile;

    private List<SMPPlayer> players = new ArrayList<>();

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

        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    handleSave();
                } catch (IOException e) {
                    instance.getLogger().warning("Error saving players.yml");
                    throw new RuntimeException(e);
                }
            }
        }.runTaskTimerAsynchronously(instance, 36000, 0);

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

        players.add(player);
        instance.getLogger().info("new player created: " + player.getPlayerId());
        return player;

    }

    public SMPPlayer getPlayer(UUID uniqueId)
    {
        for (SMPPlayer player : players)
        {
            if (player.getPlayerId().equals(uniqueId))
                return player;
        }
        return null;
    }


    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent playerJoinEvent)
    {

        playerDataFile.getList("players", Collections.emptyList()).forEach(obj -> {
            if (obj instanceof SMPPlayer) {
                SMPPlayer player = (SMPPlayer) obj;
                if (player.getPlayerId().equals(playerJoinEvent.getPlayer().getUniqueId())) players.add((SMPPlayer) obj);
            }
        });

        if (getPlayer(playerJoinEvent.getPlayer().getUniqueId()) == null)
            getPlayerOrCreate(playerJoinEvent.getPlayer().getUniqueId());

    }

    @EventHandler
    public void handlePlayerQuit(PlayerQuitEvent playerQuitEvent)
    {

        players.removeIf(smpP -> {
            if (smpP.getPlayerId().equals(playerQuitEvent.getPlayer().getUniqueId()))
                return true;
            else
                return false;
        });
        try {
            handleSave();
        } catch (Exception e)
        {
            System.out.println("Error saving players file in Player Quit Event.");
            e.printStackTrace();
        }

    }

    public void handleSave() throws IOException {
        instance.getLogger().info("successfully saved players.yml");
        getPlayerDataFile().set("players", players);
        saveDataFile();
    }

}
