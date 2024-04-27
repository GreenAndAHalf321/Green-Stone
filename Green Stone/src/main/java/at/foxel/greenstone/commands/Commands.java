package at.foxel.greenstone.commands;

import at.foxel.greenstone.GreenStone;
import at.foxel.greenstone.Playback;
import at.foxel.greenstone.Recording;
import at.foxel.greenstone.useful.Colors;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        GreenStone.getPluginLogger().info("Command " + label + " used");

        //TODO Do not allow recording or playback while a playback/recording is running
        if(sender instanceof BlockCommandSender) {
            GreenStone.getPluginLogger().info("Executed by Command Block");
            if(!GreenStone.getPlugin().config.getBoolean("allowExecutionViaCommandBLock")) {
                GreenStone.getPluginLogger().info("Command Block execution not allowed in config");
                Bukkit.broadcastMessage(Colors.RED + "A command block is trying to use the time stone. " +
                        Colors.YELLOW + "(This is not allowed by the config god)");
                return false;
            }
        }else if (sender instanceof ConsoleCommandSender) {
            GreenStone.getPluginLogger().info("Executed by Console");
            if(!GreenStone.getPlugin().config.getBoolean("allowExecutionViaConsole")) {
                GreenStone.getPluginLogger().info("Console execution not allowed in config");
                return false;
            }
        }else {
            GreenStone.getPluginLogger().info("Executed by Player");
            if(!GreenStone.getPlugin().config.getBoolean("allowExecutionViaPlayer")) {
                GreenStone.getPluginLogger().info("Player execution not allowed in config");
                sender.sendMessage(Colors.RED + "Players are not allowed to use the time stone. " +
                        Colors.YELLOW + "(This is not allowed by the config god)");
                return false;
            }
        }

        String[] subCommandArgs = new String[args.length - 1];

        System.arraycopy(args, 1, subCommandArgs, 0, args.length - 1);

        if(args[0].equals("recording"))
            return onRecording(subCommandArgs, sender);

        if(args[0].equals("playback"))
            return onPlayback(subCommandArgs, sender);

        //TODO only let a player execute this command
        if(args[0].equals("playbacks"))
            return  onPlaybacks(sender);

        //TODO only let a player execute this command
        if(args[0].equals("config"))
            return  onConfig(sender);

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

            //TODO make players and entities recordable
            if(GreenStone.getPlugin().config.getBoolean("recordPlayer"))
                Bukkit.broadcastMessage(Colors.RED + "Recording players is not possible yet");
            if(GreenStone.getPlugin().config.getBoolean("recordEntities"))
                Bukkit.broadcastMessage(Colors.RED + "Recording entities is not possible yet");

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

    private boolean onPlaybacks(CommandSender sender) {
        LinkedList<Recording> listOfPlaybacks = Recording.getFinishedRecordings();

        byte inventoryRows = (byte) (listOfPlaybacks.size() / 9);

        if(listOfPlaybacks.size() % 9.0 > 0)
            inventoryRows++;

        Inventory playbacks = Bukkit.createInventory(null, 9 * inventoryRows, "Playbacks");

        for(Recording playback : listOfPlaybacks) {
            ItemStack playbackItem = new ItemStack(Material.DIAMOND, 10);
            ItemMeta playbackItemMeta = playbackItem.getItemMeta();

            assert playbackItemMeta != null;
            playbackItemMeta.setDisplayName(playback.getName());
            playbackItem.setItemMeta(playbackItemMeta);

            playbacks.addItem(playbackItem);
        }

        ((Player) sender).openInventory(playbacks);

        return true;
    }

    private boolean onConfig(CommandSender sender) {
        int emptySlots = 9*4;

        Inventory configs = Bukkit.createInventory(null, emptySlots, "Config Settings");

        FileConfiguration config = GreenStone.getPlugin().config;

        configs.setItem(10, new ItemStack(Material.SKELETON_SKULL));
        emptySlots--;
        configs.setItem(11, new ItemStack(Material.SKELETON_SKULL));
        emptySlots--;
        configs.setItem(12, new ItemStack(Material.COMMAND_BLOCK));
        emptySlots--;
        configs.setItem(13, new ItemStack(Material.SKELETON_SKULL));
        emptySlots--;
        configs.setItem(14, new ItemStack(Material.AXOLOTL_SPAWN_EGG));
        emptySlots--;
        configs.setItem(15, new ItemStack(Material.GLASS_PANE));
        emptySlots--;

        ItemStack red = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta redMeta = red.getItemMeta();
        ItemStack green = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta greenMeta = green.getItemMeta();

        redMeta.setDisplayName(Colors.RED + "FALSE");
        greenMeta.setDisplayName(Colors.GREEN + "TRUE");

        red.setItemMeta(redMeta);
        green.setItemMeta(greenMeta);

        configs.setItem(19, config.getBoolean("allowExecutionViaPlayer") ? green : red);
        emptySlots--;
        configs.setItem(20, config.getBoolean("allowExecutionViaConsole") ? green : red);
        emptySlots--;
        configs.setItem(21, config.getBoolean("allowExecutionViaCommandBLock") ? green : red);
        emptySlots--;
        configs.setItem(22, config.getBoolean("recordPlayer") ? green : red);
        emptySlots--;
        configs.setItem(23, config.getBoolean("recordEntities") ? green : red);
        emptySlots--;
        configs.setItem(24, config.getBoolean("recordGaps") ? green : red);
        emptySlots--;
        //TODO Added settings for the time interval

        for(int i = 0; i < emptySlots; i++)
            configs.addItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

        ((Player) sender).openInventory(configs);

        return true;
    }
}
