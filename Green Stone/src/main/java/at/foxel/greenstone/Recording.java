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
    private static int count = 0;
    private static Recording currentRecording;
    private final Queue<BlockState> blockUpdates = new ConcurrentLinkedQueue<>();
    private final LinkedList<WorldState> worldStates = new LinkedList<>();
    private final String name;

    private final int timeIntervalInMilliseconds;

    private Timer timer;
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
        count++;
        this.name = name;
        this.timeIntervalInMilliseconds = timeIntervalInMilliseconds;
    }

    public static boolean isRecording() {
        return currentRecording != null;
    }

    public void startRecording() {
        currentRecording = this;
        timer = new Timer();
        timer.schedule(task, timeIntervalInMilliseconds, timeIntervalInMilliseconds);
    }

    public void pauseRecording() {
        timer.cancel();
    }

    public void resumeRecording() {
        timer.cancel();
        timer = new Timer();

        timer.schedule(task, timeIntervalInMilliseconds, timeIntervalInMilliseconds);
    }

    public void stopRecording() {
        timer.cancel();
        timer = null;

        currentRecording = null;
        //TODO Save the recording
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

    public static Recording getCurrentRecording() {
        return currentRecording;
    }

    public String getName() {
        return name;
    }

    public static int getCount() {
        return count;
    }
}
