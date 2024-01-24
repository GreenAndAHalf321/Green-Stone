package at.foxel.greenstone.commands;

import at.foxel.greenstone.GreenStone;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        GreenStone.getPluginLogger().info("Command " + label + " used");

        return false;
    }
}
