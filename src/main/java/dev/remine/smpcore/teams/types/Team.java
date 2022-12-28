package dev.remine.smpcore.teams.types;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Team {

    private UUID teamId;

    private String teamName;

    private ChatColor teamColor;

    private String motd = "";

    private List<TeamMember> members = new ArrayList<>();

    public Team(String teamName, ChatColor teamColor)
    {
        teamId = UUID.randomUUID();
        this.teamName = teamName;
        this.teamColor = teamColor;
    }


    public UUID getTeamId() {
        return teamId;
    }

    public void setTeamId(UUID teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ChatColor getTeamColor() {
        return teamColor;
    }

    public void setTeamColor(ChatColor teamColor) {
        this.teamColor = teamColor;
    }

    public List<TeamMember> getMembers() {
        return members;
    }

    public void setMembers(List<TeamMember> members) {
        this.members = members;
    }

    public void addMember(TeamMember member)
    {
        members.add(member);
    }

    public void removeMember(TeamMember member)
    {
        members.remove(member);
    }

    public String getMotd() {
        return motd;
    }

    public void setMotd(String motd) {
        this.motd = motd;
    }
}
