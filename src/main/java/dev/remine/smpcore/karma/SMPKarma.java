package dev.remine.smpcore.karma;

import dev.remine.smpcore.SMPCore;
import dev.remine.smpcore.karma.commands.KarmaCommand;
import dev.remine.smpcore.player.SMPPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class SMPKarma implements Listener {

    private SMPCore instance;

    public SMPKarma(SMPCore instance)
    {
        this.instance = instance;
        instance.getCommand("karma").setExecutor(new KarmaCommand(instance));
    }

    @EventHandler
    public void handlePlayerKill(PlayerDeathEvent playerDeathEvent)
    {
        if (playerDeathEvent.getEntity().getKiller() != null)
        {
            Player killer = playerDeathEvent.getPlayer().getKiller();
            SMPPlayer smpPlayer = instance.playerManager.getPlayer(killer.getUniqueId());
            if (smpPlayer != null)
            {
                smpPlayer.setKarma(smpPlayer.getKarma() - 1);
                killer.sendMessage(ChatColor.RED + "You have lost 1 of karma because you've killed another player.");
            }
        }
    }


}
