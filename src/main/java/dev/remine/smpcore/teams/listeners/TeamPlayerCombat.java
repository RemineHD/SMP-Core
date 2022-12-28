package dev.remine.smpcore.teams.listeners;

import dev.remine.smpcore.SMPCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TeamPlayerCombat implements Listener {

    private SMPCore instance;

    public TeamPlayerCombat(SMPCore instance)
    {
        this.instance = instance;
    }

    @EventHandler
    public void handleCombat(EntityDamageByEntityEvent entityDamageByEntityEvent)
    {

        if (entityDamageByEntityEvent.getEntity() instanceof Player && entityDamageByEntityEvent.getDamager() instanceof Player)
        {
            Player entity = ((Player) entityDamageByEntityEvent.getEntity());
            Player damager = ((Player) entityDamageByEntityEvent.getDamager());

            if (instance.teamsManager.getTeamByMember(entity.getUniqueId()).getTeamId().equals(instance.teamsManager.getTeamByMember(damager.getUniqueId())))
                entityDamageByEntityEvent.setCancelled(true);

        }
    }

}
