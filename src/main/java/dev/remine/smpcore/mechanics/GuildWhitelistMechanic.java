package dev.remine.smpcore.mechanics;

import dev.remine.smpcore.SMPCore;
import net.hypixel.api.reply.GuildReply;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class GuildWhitelistMechanic implements Listener {

    private SMPCore instance;

    private ArrayList<UUID> allowedLogins = new ArrayList<>();

    public GuildWhitelistMechanic(SMPCore instance)
    {
        this.instance = instance;
        new BukkitRunnable() {
            @Override
            public void run() {
                allowedLogins.clear();
                instance.getLogger().info("Cleared Hypixel API cache.");
            }
        }.runTaskTimerAsynchronously(instance, 0, 36000);
    }

    public boolean isLoginAllowed(UUID playerId)
    {
        return allowedLogins.contains(playerId);
    }

    @EventHandler
    public void handlePlayerPreLogin(AsyncPlayerPreLoginEvent playerJoinEvent)
    {

        if (playerJoinEvent.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED) return;

        if (isLoginAllowed(playerJoinEvent.getUniqueId()))
            return;

        GuildReply.Guild guild;
        try {
            guild = instance.getHypixelAPI().getGuildByPlayer(playerJoinEvent.getUniqueId()).get().getGuild();
        } catch (ExecutionException | InterruptedException e) {
            playerJoinEvent.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', "&cThe Hypixel API is down. Please try again in a few seconds."));
            throw new RuntimeException(e);
        }

        if (guild == null || instance.getGuildId() != guild.get_id())
        {
            disallow(playerJoinEvent);
            return;
        }

        allowedLogins.add(playerJoinEvent.getUniqueId());

    }

    private void disallow(AsyncPlayerPreLoginEvent asyncPlayerPreLoginEvent)
    {
        asyncPlayerPreLoginEvent.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', "&cYou need to be part of the Guild to join the server."));
        instance.getLogger().info(asyncPlayerPreLoginEvent.getName() + " tried to join the server, but is not a member of the Guild.");
    }


}
