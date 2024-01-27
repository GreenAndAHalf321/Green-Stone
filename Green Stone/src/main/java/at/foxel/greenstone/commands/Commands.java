package at.foxel.greenstone.commands;

import at.foxel.greenstone.GreenStone;
import at.foxel.greenstone.useful.Colors;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

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

        String[] subCommandArgs = new String[args.length - 1];

        for(int i = 1; i < args.length; i++)
            subCommandArgs[i - 1] = args[i];


        if(args[0].equals("recording"))
            return onRecording(subCommandArgs, sender);

        return false;
    }

    private boolean onRecording(String[] args, CommandSender sender) {
        GreenStone.getPluginLogger().info("Subcommand 'recording' used");

        if(args[0].equals("start")) {
            GreenStone.getPluginLogger().info("Starting recording...");
            Bukkit.broadcastMessage(Colors.YELLOW + sender.getName() + Colors.WHITE + " is starting a recording...");
        }

        return false;
    }
}
