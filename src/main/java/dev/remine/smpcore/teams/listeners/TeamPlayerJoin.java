package dev.remine.smpcore.teams.listeners;

import com.destroystokyo.paper.profile.PlayerProfile;
import dev.remine.smpcore.SMPCore;
import dev.remine.smpcore.teams.types.Team;
import dev.remine.smpcore.teams.types.TeamMember;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TeamPlayerJoin implements Listener {

    private SMPCore instance;

    public TeamPlayerJoin(SMPCore instance)
    {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void handleJoin(PlayerJoinEvent playerJoinEvent)
    {
        Player player = playerJoinEvent.getPlayer();
        Team playerTeam = instance.teamsManager.getTeamByMember(player.getUniqueId());

        if (playerTeam != null)
        {
            if (!playerTeam.getMotd().equals("")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "\n" +
                        " &2◆ &a&lTEAM MOTD \n" +
                        " " + playerTeam.getMotd() + " \n"));
            }
            for (TeamMember member : playerTeam.getMembers())
            {
                if (Bukkit.getPlayer(member.getMemberId()) != null)
                {
                    Bukkit.getPlayer(member.getMemberId()).sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l[◆] &a+ &2" + player.getName() + " &fjoined the game."));
                    Bukkit.getPlayer(member.getMemberId()).playSound(Bukkit.getPlayer(member.getMemberId()).getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, Integer.MAX_VALUE, 1);
                }

            }
        }
        instance.teamsManager.setupPlayerTeam(player, playerTeam);
    }

}
