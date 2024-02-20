package at.foxel.greenstone;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Recording {
    private final Queue<BlockState> blockUpdates = new ConcurrentLinkedQueue<>();
    private final LinkedList<BlockState> states = new LinkedList<>();
    private final String name;

    public Recording(String name) {
        this.name = name;
    }

    public void addBlock(Block block) {
        blockUpdates.add(new BlockState(block.getType(), block.getLocation()));
    }

    public void removeBlock(Location location) {
        blockUpdates.add(new BlockState(Material.AIR, location));
    }

    public LinkedList<BlockState> getStates() {
        return (LinkedList<BlockState>) states.clone();
    }

    public String getName() {
        return name;
    }
}
