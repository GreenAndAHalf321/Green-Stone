package at.foxel.greenstone;

import at.foxel.greenstone.useful.DoubleLinkedList;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Recording {
    private static int count = 0;
    private static Recording currentRecording;
    private final Queue<BlockState> blockUpdates = new ConcurrentLinkedQueue<>();
    private final DoubleLinkedList<WorldState> worldStates = new DoubleLinkedList<>();
    private static final LinkedList<Recording> finishedRecordings = new LinkedList<>();
    private final String name;

    private final int timeIntervalInMilliseconds;

    private Timer timer;
    private final TimerTask task = new TimerTask() {
        @Override
        public void run() {
            //TODO make this optional
            if(blockUpdates.isEmpty())
                return;

            BlockState currentState;
            WorldState worldState = new WorldState();
            while (!blockUpdates.isEmpty()) {
                currentState = blockUpdates.poll();
                worldState.addBlockState(currentState);
            }
            worldStates.addLast(worldState);
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

        finishedRecordings.add(currentRecording);

        currentRecording = null;

        saveRecording();
    }

    public void addBlock(Block block) {
        //TODO in case a block is placed inside a liquid the FROM block is not AIR
        blockUpdates.add(new BlockState(Material.AIR, block.getType(), block.getLocation()));
    }

    public void removeBlock(Block removedBlock) {
        blockUpdates.add(new BlockState(removedBlock.getType(), Material.AIR, removedBlock.getLocation()));
    }

    public DoubleLinkedList<WorldState> getWorldStates() {
        return worldStates;
    }

    public static LinkedList<Recording> getFinishedRecordings() {
        return (LinkedList<Recording>) finishedRecordings.clone();
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

    private void saveRecording() {

        try{
            Path path = Paths.get("plugins\\Recordings");

            if(!Files.exists(path))
                Files.createDirectory(path);

            File recordingFile = new File(path  + "\\" + name + ".rec");

            if (recordingFile.createNewFile()) {
                GreenStone.getPluginLogger().info("The recording was saved");
            }else {
                GreenStone.getPluginLogger().info("The recording could not be saved because it already exists");
            }
        }catch (IOException ex) {
            GreenStone.getPluginLogger().info("The recording could not be saved");
        }
    }
}
