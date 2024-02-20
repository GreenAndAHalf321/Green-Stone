package at.foxel.greenstone;

import at.foxel.greenstone.commands.Commands;
import at.foxel.greenstone.listener.BlockListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class GreenStone extends JavaPlugin {
    private static Logger logger;
    private static boolean isRecording = false;

    public static boolean isRecording() {
        return isRecording;
    }

    public static void startRecording() {
        GreenStone.isRecording = true;
    }

    @Override
    public void onEnable() {
        logger = getLogger();
        logger.info("You have been given the time stone. Use it wisely!!");

        PluginCommand command = this.getCommand("gs");

        if(command != null)
            command.setExecutor(new Commands());

        this.getServer().getPluginManager().registerEvents(new BlockListener(), this);
    }

    @Override
    public void onDisable() {
        logger.info("The time stone has been taken from you! Sorry not sorry.");
    }

    public static Logger getPluginLogger() {
        return logger;
    }
}
