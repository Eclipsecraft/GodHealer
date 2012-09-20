package com.ikkerens.godhealer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private final String        TAG = ChatColor.WHITE + "[" + ChatColor.GREEN + "GodHealer" + ChatColor.WHITE + "] " + ChatColor.GREEN;
    private Logger              log;
    private ArrayList< String > godModers;

    @Override
    public void onEnable() {
        // Initializing logger
        log = Logger.getLogger( "Minecraft" );

        // Initialize list
        godModers = new ArrayList< String >();
        loadGodModers();

        // Register commands
        GodCommand gc = new GodCommand( this, TAG, godModers );
        this.getCommand( "god" ).setExecutor( gc );
        this.getCommand( "godmode" ).setExecutor( gc );
        this.getCommand( "nopvp" ).setExecutor( gc );

        HealCommand hc = new HealCommand( this, TAG );
        this.getCommand( "heal" ).setExecutor( hc );

        FlameCommand fc = new FlameCommand( this, TAG );
        this.getCommand( "ignite" ).setExecutor( fc );
        this.getCommand( "flame" ).setExecutor( fc );
        this.getCommand( "burn" ).setExecutor( fc );

        // Register events
        GodListener gl = new GodListener( godModers );
        this.getServer().getPluginManager().registerEvents( gl, this );

        PlayerNotifier pn = new PlayerNotifier( TAG, godModers );
        this.getServer().getPluginManager().registerEvents( pn, this );

        log.info( TAG + "Registered heal/god commands and listeners." );
    }

    @Override
    public void onDisable() {
        log.info( TAG + "Unloading commands and listeners" );
        saveGodModers();
    }

    private void saveGodModers() {
        try {
            FileOutputStream fileOut = new FileOutputStream( "plugins/GodHealer/godmodeplayers.dat" );
            ObjectOutputStream out = new ObjectOutputStream( fileOut );
            out.writeObject( godModers );
            out.close();
            fileOut.close();
            log.info( TAG + "Saved all god moders." );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings( "unchecked" )
    private void loadGodModers() {
        try {
            FileInputStream fileIn = new FileInputStream( "plugins/GodHealer/godmodeplayers.dat" );
            ObjectInputStream in = new ObjectInputStream( fileIn );
            godModers = (ArrayList< String >) in.readObject();
            in.close();
            fileIn.close();
            log.info( TAG + "Loaded all god moders." );
        } catch ( IOException e ) {
            e.printStackTrace();
            return;
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
        }
    }
}
