package com.ikkerens.godhealer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class HealCommand implements CommandExecutor {
    private Plugin       plugin;
    private final String TAG;

    public HealCommand( Plugin plugin, final String TAG ) {
        this.TAG = TAG;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand( CommandSender sender, Command cmd, String command, String[] args ) {
        Player target;
        if ( sender instanceof Player ) {
            if ( !sender.hasPermission( "godhealer.heal" ) ) {
                sender.sendMessage( TAG + "You do not have the required permission node to heal." );
                return true;
            }
            if ( args.length > 0 ) {
                if ( !sender.hasPermission( "godhealer.heal.other" ) ) {
                    sender.sendMessage( TAG + "You do not have the required permission node to heal someone else." );
                    return true;
                }
                target = plugin.getServer().getPlayer( args[ 0 ] );
            } else {
                target = (Player) sender;
            }
        } else {
            if ( args.length == 0 ) {
                sender.sendMessage( TAG + "You cannot heal the console." );
                return true;
            } else {
                target = plugin.getServer().getPlayer( args[ 0 ] );
            }
        }
        if ( target == null ) {
            sender.sendMessage( TAG + "The person you're trying to heal is not online." );
        } else {
            target.setHealth( target.getMaxHealth() );
            target.setFoodLevel( 20 );
            target.setRemainingAir( target.getMaximumAir() );
            target.setFireTicks( 0 );
            if ( !target.getName().equals( sender.getName() ) ) {
                sender.sendMessage( TAG + "Healed " + target.getName() + "." );
                target.sendMessage( TAG + "You were healed by " + sender.getName() );
            } else {
                target.sendMessage( TAG + "You healed yourself." );
            }
        }
        return true;
    }

}