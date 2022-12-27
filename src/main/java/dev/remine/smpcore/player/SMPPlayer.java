package dev.remine.smpcore.player;

import java.util.UUID;

public class SMPPlayer {


    public SMPPlayer(UUID playerId)
    {
        this.playerId = playerId;
    }

    private UUID playerId;
    private String teamId;

    private int karma;

    private int lives;

    public UUID getPlayerId() {
        return playerId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
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
