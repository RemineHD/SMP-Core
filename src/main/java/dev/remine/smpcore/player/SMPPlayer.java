package dev.remine.smpcore.player;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class SMPPlayer implements ConfigurationSerializable {


    public SMPPlayer(UUID playerId)
    {
        this.playerId = playerId;
    }

    private UUID playerId;

    private int karma;

    private int lives;

    @Override
    public @NotNull Map<String, Object> serialize() {
        LinkedHashMap result = new LinkedHashMap();
        result.put("playerId", String.valueOf(playerId));
        result.put("karma", karma);
        result.put("lives", lives);
        return result;
    }

    public static SMPPlayer deserialize(Map<String, Object> args)
    {
        SMPPlayer player = new SMPPlayer(null);

        if (args.containsKey("playerId"))
            player.playerId = UUID.fromString((String) args.get("playerId"));

        if (args.containsKey("karma"))
            player.setKarma((Integer) args.get("karma"));

        if (args.containsKey("lives"))
            player.setLives((Integer) args.get("lives"));

        return player;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public int getKarma() {
        return karma;
    }

    public void setKarma(int karma) {
        this.karma = karma;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }


}
