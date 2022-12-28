package dev.remine.smpcore.teams.types;

import java.util.UUID;

public class TeamMember {

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
        LEADER,
        MEMBER
    }

}
