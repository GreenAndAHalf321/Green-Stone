package at.foxel.greenstone.listener;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class BlockListener implements Listener {

    //Block break
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Bukkit.broadcastMessage("A block has been destroyed");
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        for (Block block : event.blockList()) {
            Bukkit.broadcastMessage("A block has been destroyed by an explosion");
        }
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        Bukkit.broadcastMessage("A liquid has been taken back");
    }

    //Block place
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Bukkit.broadcastMessage("A block has been placed");
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        Bukkit.broadcastMessage("A liquid has been placed");
    }

    //Block Swap (Liquid over snow for example)
    @EventHandler
    public void onBlockFromTo(BlockFromToEvent event) {
        if(event.getToBlock().getType().isAir()) {
            Bukkit.broadcastMessage("A liquid is spreading");
        } else {
            Bukkit.broadcastMessage("A liquid is spreading and destroyed a block");
        }
    }

    //Block spread (snow for example)
    @EventHandler
    public void onBlockForm(BlockFormEvent event) {
        Bukkit.broadcastMessage("A block is spreading");
    }

}
