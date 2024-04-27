package at.foxel.greenstone.listener;

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

        ItemStack clickedItem = null;
        assert inv != null;
        if(slot >= 9)
            clickedItem = inv.getItem(slot - 9);

        assert clickedItem != null;

        //TODO Change config data

        event.setCancelled(true);
    }
}
