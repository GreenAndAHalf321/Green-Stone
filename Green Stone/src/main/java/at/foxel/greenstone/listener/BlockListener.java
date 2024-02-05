package at.foxel.greenstone.listener;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

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

    //Block place
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Bukkit.broadcastMessage("A block has been placed");
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

}
