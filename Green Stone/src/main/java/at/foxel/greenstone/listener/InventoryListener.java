// © 2024 Florian Wallner
//
// SPDX-License-Identifier: GPL-3.0-or-later

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
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryListener implements Listener {
    final static byte INV_ROW_SIZE = 9;

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
        byte slot = (byte) event.getSlot();
        Inventory inv = event.getClickedInventory();

        if(inv == null)
            return;

        if(slot >= 9) {
            if(inv.getItem(slot - INV_ROW_SIZE) == null)
                return;

            Material clikedItemMaterial = inv.getItem(slot - INV_ROW_SIZE).getType();
            FileConfiguration config = GreenStone.getPlugin().config;

            for(ConfigSetting setting : ConfigSetting.getSettings()) {
                if(setting.getMaterial().equals(clikedItemMaterial)) {
                    if(setting.getDefaultSetting() instanceof Boolean) {
                        config.set(setting.getId(), !config.getBoolean(setting.getId()));
                        config.saveToString();
                    } else {
                        //TODO Use Anvil GUI
                        //Commands.openSettings((Player) event.getWhoClicked(), setting);
                    }
                    break;
                }
            }
        } //else if (inv.getType().equals(InventoryType.ANVIL)) {
            //TODO Use AnvilGUI by WesJD
        //}

        //TODO Do not clear the inv when nothing happened
        byte slots = (byte) inv.getSize();
        inv.clear();
        Commands.buildConfigMenu(inv, slots); //TODO Add update method that does not recreate the whole inv

        event.setCancelled(true);
    }
}
