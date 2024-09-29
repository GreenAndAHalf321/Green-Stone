package at.foxel.greenstone.listener;

import at.foxel.greenstone.GreenStone;
import at.foxel.greenstone.Playback;
import at.foxel.greenstone.Recording;
import at.foxel.greenstone.commands.Commands;
import at.foxel.greenstone.useful.Colors;
import at.foxel.greenstone.useful.ConfigSetting;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        //TODO Use something else instead of the String title
        if (event.getView().getTitle().equals("Playbacks"))
            onPlaybacksClick(event);
        else if (event.getView().getTitle().equals("Config Settings") || event.getView().getTitle().contains("Change "))
            onConfigSettingsClick(event);
    }

    private void onPlaybacksClick(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null)
            return;

        for(Recording playback : Recording.getFinishedRecordings()) {
            if(playback.getName().equals(clickedItem.getItemMeta().getDisplayName())) {
                //TODO ask the player for confirmation if a playback or recording is already running
                Playback.startPlayback(playback);
                break;
            }
        }

        HumanEntity playerWhoClicked = event.getWhoClicked();
        playerWhoClicked.sendMessage(Colors.GREEN + "You started the playback " + Colors.YELLOW
                + clickedItem.getItemMeta().getDisplayName());
        playerWhoClicked.closeInventory();
        event.setCancelled(true);
    }

    private void onConfigSettingsClick(InventoryClickEvent event) {
        int slot = event.getSlot();
        Inventory inv = event.getClickedInventory();

        if(inv == null)
            return;

        ItemStack clickedItem = null;
        if(slot >= 9) {
            clickedItem = inv.getItem(slot - 9);

            if(clickedItem == null)
                return;

            Material clikedItemMaterial = clickedItem.getType();
            FileConfiguration config = GreenStone.getPlugin().config;

            for(ConfigSetting setting : ConfigSetting.getSettings()) {
                if(setting.getMaterial().equals(clikedItemMaterial)) {
                    if(setting.getDefaultSetting() instanceof Boolean) {
                        config.set(setting.getId(), !config.getBoolean(setting.getId()));
                        config.saveToString();
                    } else {
                        Commands.openSettings((Player) event.getWhoClicked(), setting);
                        event.setCancelled(true); //TODO Remove this later
                        return;
                    }
                    break;
                }
            }
        } else if (inv.getType().equals(InventoryType.ANVIL)) {
            clickedItem = event.getCurrentItem();

            if(clickedItem == null)
                return;

            String newValue = clickedItem.getItemMeta().getDisplayName();
            if(!newValue.matches("\\d+")) {
                GreenStone.getPluginLogger().info("Wrong input: " + newValue);
                event.setCancelled(true);
                return;
            }

            FileConfiguration config = GreenStone.getPlugin().config;
            for(ConfigSetting setting : ConfigSetting.getSettings())
                if(event.getView().getTitle().contains(setting.getName())) {
                    config.set(setting.getId(), Integer.parseInt(newValue));
                    config.saveToString();
                }

            //TODO Change this!!!
            inv = Bukkit.createInventory(null, 9*4, "Config Settings");
            event.getWhoClicked().openInventory(inv);
        }

        //TODO Do not clear the inv when nothing happened
        int slots = inv.getSize();
        inv.clear();
        Commands.buildConfigMenu(inv, slots); //TODO Add update method that does not recreate the whole inv

        event.setCancelled(true);
    }
}
