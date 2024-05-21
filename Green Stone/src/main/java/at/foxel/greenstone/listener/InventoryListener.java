package at.foxel.greenstone.listener;

import at.foxel.greenstone.GreenStone;
import at.foxel.greenstone.Playback;
import at.foxel.greenstone.Recording;
import at.foxel.greenstone.useful.Colors;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        //TODO Use something else instead of the String title
        if (event.getView().getTitle().equals("Playbacks"))
            onPlaybacksClick(event);
        else if (event.getView().getTitle().equals("Config Settings"))
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

            assert clickedItem != null : "Clicked item should not be null";

            String clikedName = clickedItem.getItemMeta().getDisplayName();
            if(clikedName.substring(2).equals("Command execution via player")) {
                GreenStone.getPlugin().config.set("allowExecutionViaPlayer",  !GreenStone.getPlugin()
                        .config.getBoolean("allowExecutionViaPlayer"));
                GreenStone.getPlugin().saveConfig();
            } else if(clikedName.equals("Command execution via console")) {
                GreenStone.getPlugin().config.set("allowExecutionViaConsole",  !GreenStone.getPlugin()
                        .config.getBoolean("allowExecutionViaConsole"));
                GreenStone.getPlugin().saveConfig();
            } else if(clikedName.substring(2).equals("Command execution via command block")) {
                GreenStone.getPlugin().config.set("allowExecutionViaCommandBLock",  !GreenStone.getPlugin()
                        .config.getBoolean("allowExecutionViaCommandBLock"));
                GreenStone.getPlugin().saveConfig();
            } else if(clikedName.substring(2).equals("Record players")) {
                GreenStone.getPlugin().config.set("recordPlayer",  !GreenStone.getPlugin()
                        .config.getBoolean("recordPlayer"));
                GreenStone.getPlugin().saveConfig();
            } else if(clikedName.equals("Record entities like animals and monsters")) {
                GreenStone.getPlugin().config.set("recordEntities",  !GreenStone.getPlugin()
                        .config.getBoolean("recordEntities"));
                GreenStone.getPlugin().saveConfig();
            } else if(clikedName.equals("Record gaps where no block has been changed")) {
                GreenStone.getPlugin().config.set("recordGaps",  !GreenStone.getPlugin()
                        .config.getBoolean("recordGaps"));
                GreenStone.getPlugin().saveConfig();
            }
        }



        event.setCancelled(true);
    }
}
