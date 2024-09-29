// © 2024 Foxel e.U.
//
// SPDX-License-Identifier: GPL-3.0-or-later

package at.foxel.greenstone;

import at.foxel.greenstone.commands.Commands;
import at.foxel.greenstone.listener.BlockListener;
import at.foxel.greenstone.listener.InventoryListener;
import at.foxel.greenstone.useful.ConfigSetting;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

public final class GreenStone extends JavaPlugin {
    private static Logger logger;
    private static boolean isRecording = false;
    private static GreenStone plugin;
    public FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        logger = getLogger();
        logger.info("You have been given the time stone. Use it wisely!!");

        PluginCommand command = this.getCommand("gs");

        if(command != null)
            command.setExecutor(new Commands());

        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);

        //Setup config file

        ConfigSetting.setupConfig();

        for (ConfigSetting setting : ConfigSetting.getSettings()) {
            config.addDefault(setting.getId(), setting.getDefaultSetting());
        }

        config.options().copyDefaults(true);
        saveConfig();

        plugin = this;
    }

    @Override
    public void onDisable() {
        logger.info("The time stone has been taken from you! Sorry not sorry.");
    }

    public void scheduleSyncCallable(Callable<Object> callable) {
        getServer().getScheduler().callSyncMethod(this, callable);
    }

    public static void startRecording() {
        GreenStone.isRecording = true;
    }

    public static boolean isRecording() {
        return isRecording;
    }
    public static GreenStone getPlugin() { return plugin; }
    public static Logger getPluginLogger() {
        return logger;
    }
}
