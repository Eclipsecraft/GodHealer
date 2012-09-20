package com.ikkerens.godhealer;

import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerNotifier implements Listener {
    private final String        TAG;
    private ArrayList< String > godModers;

    public PlayerNotifier( final String TAG, ArrayList< String > godModers ) {
        this.TAG = TAG;
        this.godModers = godModers;
    }

    @EventHandler( priority = EventPriority.LOWEST )
    public void onLogin( PlayerJoinEvent e ) {
        if ( godModers.contains( e.getPlayer().getName() ) ) {
            e.getPlayer().sendMessage( TAG + "You are still in godmode from your last login." );
        }
    }

    @EventHandler( priority = EventPriority.LOWEST )
    public void onWorldSwitch( PlayerChangedWorldEvent e ) {
        if ( godModers.contains( e.getPlayer().getName() ) && e.getPlayer().hasPermission( "godhealer.godmode.overrideRemove" ) && !e.getPlayer().isOp() ) {
            e.getPlayer().sendMessage( TAG + "Your godmode is not active in this world." );
        }
    }
}
