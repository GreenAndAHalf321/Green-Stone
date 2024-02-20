package at.foxel.greenstone;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Recording {
    private final Queue<BlockState> blockUpdates = new ConcurrentLinkedQueue<>();
    private final LinkedList<WorldState> worldStates = new LinkedList<>();
    private final String name;

    private final int timeIntervalInMilliseconds;

    private Timer timer = new Timer();
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            BlockState currentState;
            WorldState worldState = new WorldState();
            while (!blockUpdates.isEmpty()) {
                currentState = blockUpdates.poll();
                worldState.addBlockState(currentState);
            }
            worldStates.add(worldState);
        }
    };

    public Recording(String name, int timeIntervalInMilliseconds) {
        this.name = name;
        this.timeIntervalInMilliseconds = timeIntervalInMilliseconds;
    }

    public void addBlock(Block block) {
        blockUpdates.add(new BlockState(block.getType(), block.getLocation()));
    }

    public void removeBlock(Location location) {
        blockUpdates.add(new BlockState(Material.AIR, location));
    }

    public LinkedList<WorldState> getWorldStates() {
        return (LinkedList<WorldState>) worldStates.clone();
    }

    public String getName() {
        return name;
    }
}
