package com.ikkerens.godhealer;

import java.util.ArrayList;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class GodListener implements Listener {
    private ArrayList< String > godModers;

    public GodListener( ArrayList< String > godModers ) {
        this.godModers = godModers;
    }

    @EventHandler( priority = EventPriority.HIGH )
    public void damageListener( EntityDamageEvent event ) {
        // Basics
        if ( event.isCancelled() )
            return;

        // Is receiver in godmode?
        if ( event.getEntity() instanceof Player ) {
            Player pl = (Player) event.getEntity();
            if ( godModers.contains( pl.getName() ) ) {
                if ( !pl.hasPermission( "godhealer.godmode.overrideRemove" ) || pl.isOp() ) {
                    event.setCancelled( true );
                    return;
                }
            }
        }

        // Is attacker in godmode?
        if ( event instanceof EntityDamageByEntityEvent ) {
            Entity damager = ( (EntityDamageByEntityEvent) event ).getDamager();
            if ( damager instanceof Arrow ) {
                damager = ( (Arrow) damager ).getShooter();
            }

            if ( !( event.getEntity() instanceof Player ) )
                return;

            if ( damager instanceof Player ) {
                Player attacker = (Player) damager;
                if ( godModers.contains( attacker.getName() ) ) {
                    if ( !attacker.hasPermission( "godhealer.godmode.overrideRemove" ) || attacker.isOp() ) {
                        event.setCancelled( true );
                        return;
                    }
                }
            }
        }
    }
}
