package at.foxel.greenstone.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
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

    @EventHandler
    public void onBlockFade(BlockFadeEvent event) {
        Bukkit.broadcastMessage("A block was fading");
    }

    //Block place
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        if(event.getBlockPlaced().getType().equals(Material.FIRE))
            return;


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

    @EventHandler
    public void onIgnite(BlockIgniteEvent event) {
        Bukkit.broadcastMessage("A block ignited");
    }

}