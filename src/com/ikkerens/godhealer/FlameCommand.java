package com.ikkerens.godhealer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class FlameCommand implements CommandExecutor {
    private final String TAG;
    private Plugin       plugin;

    public FlameCommand( Plugin plugin, final String TAG ) {
        this.plugin = plugin;
        this.TAG = TAG;
    }

    @Override
    public boolean onCommand( CommandSender sender, Command cmd, String command, String[] args ) {
        Player target;
        if ( sender instanceof Player ) {
            if ( !sender.hasPermission( "godhealer.ignite" ) ) {
                sender.sendMessage( TAG + "You do not have the required permission node to ignite yourself." );
                return true;
            }
            if ( args.length > 0 ) {
                if ( !sender.hasPermission( "godhealer.ignite.other" ) ) {
                    sender.sendMessage( TAG + "You do not have the required permission node to ignite someone else." );
                    return true;
                }
                target = plugin.getServer().getPlayer( args[ 0 ] );
            } else {
                target = (Player) sender;
            }
        } else {
            if ( args.length == 0 ) {
                sender.sendMessage( TAG + "You cannot ignite the console." );
                return true;
            } else {
                target = plugin.getServer().getPlayer( args[ 0 ] );
            }
        }
        if ( target == null ) {
            sender.sendMessage( TAG + "The person you're trying to ignite is not online." );
        } else {
            target.setFireTicks( 2000 );
            if ( !target.getName().equals( sender.getName() ) ) {
                sender.sendMessage( TAG + "Ignited " + target.getName() + "." );
                target.sendMessage( TAG + "You were ignited by " + sender.getName() );
            } else {
                target.sendMessage( TAG + "You ignited yourself." );
            }
        }
        return true;
    }
}