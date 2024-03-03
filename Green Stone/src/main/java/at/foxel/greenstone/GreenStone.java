package at.foxel.greenstone;

import at.foxel.greenstone.commands.Commands;
import at.foxel.greenstone.listener.BlockListener;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class GreenStone extends JavaPlugin {
    private static Logger logger;
    private static boolean isRecording = false;
    private static GreenStone plugin;

    public static boolean isRecording() {
        return isRecording;
    }

    public static void startRecording() {
        GreenStone.isRecording = true;
    }
    public static GreenStone getPlugin() { return plugin; }

    @Override
    public void onEnable() {
        logger = getLogger();
        logger.info("You have been given the time stone. Use it wisely!!");

        PluginCommand command = this.getCommand("gs");

        if(command != null)
            command.setExecutor(new Commands());

        getServer().getPluginManager().registerEvents(new BlockListener(), this);

        plugin = this;
    }

    @Override
    public void onDisable() {
        logger.info("The time stone has been taken from you! Sorry not sorry.");
    }

    public static Logger getPluginLogger() {
        return logger;
    }
}
