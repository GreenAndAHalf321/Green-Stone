package at.foxel.greenstone.commands;

import at.foxel.greenstone.GreenStone;
import org.bukkit.command.*;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        GreenStone.getPluginLogger().info("Command " + label + " used");

        //TODO Add settings to allow or disallow execution via command block, console or player
        if(sender instanceof BlockCommandSender) {
            GreenStone.getPluginLogger().info("Executed by Command Block");
        }else if (sender instanceof ConsoleCommandSender) {
            GreenStone.getPluginLogger().info("Executed by Console");
        }else {
            GreenStone.getPluginLogger().info("Executed by Player");
        }



        return false;
    }

    private boolean onRecording() {
        return false;
    }
}