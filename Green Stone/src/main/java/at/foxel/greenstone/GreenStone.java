package at.foxel.greenstone;

import at.foxel.greenstone.commands.Commands;
import at.foxel.greenstone.listener.BlockListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class GreenStone extends JavaPlugin {
    private static Logger logger;

    @Override
    public void onEnable() {
        logger = getLogger();
        logger.info("You have been given the time stone. Use it wisely!!");

        try {
            this.getCommand("gs").setExecutor(new Commands());

            this.getServer().getPluginManager().registerEvents(new BlockListener(), this);
        } catch (NullPointerException e) {
            logger.warning("Could not load commands");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        logger.info("The time stone has been taken from you! Sorry not sorry.");
    }

    public static Logger getPluginLogger() {
        return logger;
    }
}
