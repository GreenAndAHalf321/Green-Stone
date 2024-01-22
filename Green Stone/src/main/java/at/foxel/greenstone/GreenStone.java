package at.foxel.greenstone;

import org.bukkit.plugin.java.JavaPlugin;

public final class GreenStone extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("You have been given the time stone. Use it wisely!!");
    }

    @Override
    public void onDisable() {
        getLogger().info("The time stone has been taken from you! Sorry not sorry.");
    }
}
