package dev.remine.smpcore.teams.types;

import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TeamMember implements ConfigurationSerializable {

    private UUID memberId;

    public String playerName;

    private Role memberRole;

    private int memberExp;

    public TeamMember(String playerName, UUID memberId, Role memberRole)
    {
        this.playerName = playerName;
        this.memberId = memberId;
        this.memberRole = memberRole;
        this.memberExp = 0;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        LinkedHashMap result = new LinkedHashMap();
        result.put("playerName", playerName);
        result.put("memberId", String.valueOf(memberId));
        result.put("memberRole", memberRole.asString());
        result.put("memberExp", memberExp);
        return result;
    }

    public static TeamMember deserialize(Map<String, Object> args)
    {

        TeamMember member = new TeamMember(null, null, null);

        if (args.containsKey("playerName"))
            member.playerName = (String) args.get("playerName");
        else return null;

        if (args.containsKey("memberId"))
            member.setMemberId(UUID.fromString((String) args.get("memberId")));
        else return null;

        if (args.containsKey("memberRole"))
            member.setMemberRole(Role.valueOf((String) args.get("memberRole")));
        else return null;

        if (args.containsKey("memberExp"))
            member.setMemberExp((Integer) args.get("memberExp"));
        else return null;

        return member;
    }

    public UUID getMemberId() {
        return memberId;
    }

    public void setMemberId(UUID memberId) {
        this.memberId = memberId;
    }

    public Role getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(Role memberRole) {
        this.memberRole = memberRole;
    }

    public int getMemberExp() {
        return memberExp;
    }

    public void setMemberExp(int memberExp) {
        this.memberExp = memberExp;
    }



    public enum Role {
        LEADER("LEADER"),
        MEMBER("LEADER");

        private String name;
        Role(String name)
        {
            this.name = name;
        }

        public String asString()
        {
            return name;
        }

        public Role getRole(String role)
        {
            if (name.equals(role))
                return this;
            return null;
        }

    }

}
