package at.foxel.greenstone.commands;

import at.foxel.greenstone.GreenStone;
import at.foxel.greenstone.Playback;
import at.foxel.greenstone.Recording;
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

        System.arraycopy(args, 1, subCommandArgs, 0, args.length - 1);

        if(args[0].equals("recording"))
            return onRecording(subCommandArgs, sender);

        if(args[0].equals("playback"))
            return onPlayback(subCommandArgs, sender);

        return false;
    }

    private boolean onRecording(String[] args, CommandSender sender) {
        GreenStone.getPluginLogger().info("Subcommand 'recording' used");

        if(args[0].equals("start")) {
            GreenStone.getPluginLogger().info("Starting recording...");
            Bukkit.broadcastMessage(Colors.YELLOW + sender.getName() + Colors.WHITE + " is starting a recording...");

            String name;

            if(args.length > 2)
                name = args[1];
            else
                name = "Recording #" + Recording.getCount();

            int interval;

            if(args.length > 3)
                interval = Integer.parseInt(args[2]);
            else
                interval = 1000;

            if(args.length > 4)
                if(Boolean.parseBoolean(args[3]))
                    Bukkit.broadcastMessage(Colors.RED + "Recording players is not possible yet");

            Recording rec = new Recording(name, interval);
            rec.startRecording();

            Bukkit.broadcastMessage(Colors.GREEN + "A recording named " + Colors.YELLOW + name + Colors.GREEN
            + " with an interval of " + Colors.YELLOW + interval + Colors.GREEN + " has been started...");
            return true;
        }

        if(args[0].equals("stop")) {
            GreenStone.getPluginLogger().info("Stopping recording...");
            Bukkit.broadcastMessage(Colors.YELLOW + sender.getName() + Colors.WHITE + " is stopping a recording...");

            Recording.getCurrentRecording().stopRecording();
            return true;
        }

        return false;
    }

    private boolean onPlayback(String[] args, CommandSender sender) {
        GreenStone.getPluginLogger().info("Subcommand 'playback' used");

        if(args[0].equals("start")) {
            GreenStone.getPluginLogger().info("A player is trying to start a playback");

            if(args.length < 2) {
                GreenStone.getPluginLogger().info("The player has not specified a recording name he wants to play back");

                sender.sendMessage(Colors.RED + "You have to specify a recording name");

                return false;
            }

            StringBuilder buffer = new StringBuilder();

            for(int i = 0; i < args.length - 1; i++) {
                if(i != 0)
                    buffer.append(" ");

                buffer.append(args[i + 1]);
            }

            String playbackName = buffer.toString();
            for(Recording rec : Recording.getFinishedRecordings())
                if(rec.getName().equals(playbackName)) {
                    Playback.startPlayback(rec);

                    GreenStone.getPluginLogger().info("Recording found. Starting playback..");
                    return true;
                }

            GreenStone.getPluginLogger().info("No recording with the specified name found");
            sender.sendMessage(Colors.RED + "No recording with the name " + Colors.YELLOW + playbackName
                    + Colors.RED + " could be found");
        }

        return false;
    }
}
