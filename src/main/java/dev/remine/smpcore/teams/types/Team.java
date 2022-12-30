package dev.remine.smpcore.teams.types;

import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Team implements ConfigurationSerializable {

    private UUID teamId;

    private String teamName;

    private Color teamColor;

    private String motd = "";

    private List<TeamMember> members = new ArrayList<>();

    public Team(UUID teamId, String teamName, Color teamColor)
    {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamColor = teamColor;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        LinkedHashMap result = new LinkedHashMap();
        result.put("teamId", String.valueOf(teamId));
        result.put("teamName", teamName);
        result.put("teamColor", teamColor.getName());
        result.put("motd", motd);
        result.put("members", members);
        return result;
    }

    public static Team deserialize(Map<String, Object> args)
    {
        Team team = new Team(null, "", null);

        if (args.containsKey("teamId"))
            team.setTeamId(UUID.fromString((String) args.get("teamId")));

        if (args.containsKey("teamName"))
            team.setTeamName((String)args.get("teamName"));

        if (args.containsKey("teamColor"))
            team.setTeamColor(Color.valueOf((String) args.get("teamColor")));

        if (args.containsKey("motd"))
            team.setMotd((String)args.get("motd"));

        if (args.containsKey("members"))
            team.setMembers((List<TeamMember>) args.get("members"));

        return team;
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

    public Color getTeamColor() {
        return teamColor;
    }

    public void setTeamColor(Color teamColor) {
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

    public enum Color {

        BLACK("BLACK", "0"),
        DARK_BLUE("DARK_BLUE", "1"),
        DARK_GREEN("DARK_GREEN", "2"),
        DARK_AQUA("DARK_AQUA", "3"),
        DARK_RED("DARK_RED", "4"),
        DARK_PURPLE("DARK_PURPLE", "5"),
        GOLD("GOLD", "6"),
        GRAY("GRAY", "7"),
        DARK_GRAY("DARK_GRAY", "8"),
        BLUE("BLUE", "9"),
        GREEN("GREEN", "a"),
        AQUA("AQUA", "3"),
        RED("RED", "c"),
        LIGHT_PURPLE("LIGHT_PURPLE", "d"),
        YELLOW("YELLOW", "e"),
        WHITE("WHITE", "f");

        private String name;

        private String code;

        Color(String name, String code)
        {
            this.name = name;
            this.code = code;
        }

        public String getName()
        {
            return name;
        }

        public String getCode()
        {
            return code;
        }

    }


}
