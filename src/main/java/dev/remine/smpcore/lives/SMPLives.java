package dev.remine.smpcore.lives;

import dev.remine.smpcore.SMPCore;
import dev.remine.smpcore.lives.commands.LivesCommand;
import dev.remine.smpcore.player.SMPPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class SMPLives implements Listener {

    private SMPCore instance;

    public SMPLives(SMPCore instance)
    {
        this.instance = instance;
        instance.getCommand("lives").setExecutor(new LivesCommand(instance));
    }

    @EventHandler(ignoreCancelled = true)
    public void handlePlayerPreLogin(AsyncPlayerPreLoginEvent playerPreLoginEvent)
    {
        if (playerPreLoginEvent.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED) return;

        SMPPlayer player = instance.playerManager.getPlayer(playerPreLoginEvent.getUniqueId());
        if (player != null)
            if (player.getLives() == 0)
                playerPreLoginEvent.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', "&cYou have run out of lives."));
    }

    @EventHandler
    public void handleDeath(PlayerDeathEvent playerDeathEvent)
    {
        SMPPlayer player = instance.playerManager.getPlayer(playerDeathEvent.getPlayer().getUniqueId());
        if (player != null)
        {
            player.setLives(player.getLives() - 1);
            if (player.getLives() == 0)
            {
                playerDeathEvent.getPlayer().kickPlayer(ChatColor.translateAlternateColorCodes('&', "&cYou have run out of lives."));
                for (Player online : Bukkit.getServer().getOnlinePlayers())
                {
                    online.sendMessage("\n", "      &4&lPERMA DEATH     \n" +
                            "\n &e" + playerDeathEvent.getPlayer().getName() + " &fhave run out of lives. \n");
                    online.playSound(online.getLocation(), Sound.ENTITY_BLAZE_DEATH, Float.MAX_VALUE, -0.1f);
                }
            }
        }

    }

}
