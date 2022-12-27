package dev.remine.smpcore.player;

import java.util.UUID;

public class SMPPlayer {


    public SMPPlayer(UUID playerId)
    {
        this.playerId = playerId;
    }

    private UUID playerId;
    private UUID teamId;

    private int karma;

    private int lives;

    public UUID getPlayerId() {
        return playerId;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public void setTeamId(UUID teamId) {
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
