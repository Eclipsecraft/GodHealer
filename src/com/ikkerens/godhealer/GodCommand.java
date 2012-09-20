package com.ikkerens.godhealer;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class GodCommand implements CommandExecutor {
    private final String        TAG;
    private Plugin              plugin;
    private ArrayList< String > godModers;

    public GodCommand( Plugin plugin, final String TAG, ArrayList< String > godModers ) {
        this.plugin = plugin;
        this.TAG = TAG;
        this.godModers = godModers;
    }

    @Override
    public boolean onCommand( CommandSender sender, Command cmd, String command, String[] args ) {
        Player target;
        if ( sender instanceof Player ) {
            if ( !sender.hasPermission( "godhealer.godmode" ) ) {
                sender.sendMessage( TAG + "You do not have the required permission node to set godmode." );
                return true;
            }
            if ( args.length > 0 ) {
                if ( !sender.hasPermission( "godhealer.godmode.other" ) ) {
                    sender.sendMessage( TAG + "You do not have the required permission node to set godmode to someone else." );
                    return true;
                }
                target = plugin.getServer().getPlayer( args[ 0 ] );
            } else {
                target = (Player) sender;
            }
        } else {
            if ( args.length == 0 ) {
                sender.sendMessage( TAG + "You cannot give the console godmode." );
                return true;
            } else {
                target = plugin.getServer().getPlayer( args[ 0 ] );
            }
        }
        if ( target == null ) {
            sender.sendMessage( TAG + "The person you're trying to set godmode is not online." );
        } else {
            if ( target.hasPermission( "godhealer.godmode.overlord" ) && ( !target.getName().equals( sender.getName() ) ) ) {
                sender.sendMessage( TAG + "You are not permitted to set this persons godmode." );
                target.sendMessage( TAG + sender.getName() + " tried to change your godmode status." );
                return true;
            }
            if ( godModers.contains( target.getName() ) ) {
                godModers.remove( target.getName() );
                if ( !target.getName().equals( sender.getName() ) ) {
                    sender.sendMessage( TAG + "Removed " + target.getName() + "'s godmode." );
                    target.sendMessage( TAG + "Your godmode was removed by " + sender.getName() );
                } else {
                    target.sendMessage( TAG + "You are no longer in godmode." );
                }
            } else {
                godModers.add( target.getName() );
                if ( !target.getName().equals( sender.getName() ) ) {
                    sender.sendMessage( TAG + "Added " + target.getName() + "'s godmode." );
                    target.sendMessage( TAG + "You were given godmode by " + sender.getName() );
                } else {
                    target.sendMessage( TAG + "You are now in godmode." );
                }
            }
        }
        return true;
    }

}