// © 2024 Foxel e.U.
//
// SPDX-License-Identifier: GPL-3.0-or-later

package at.foxel.greenstone;

import at.foxel.greenstone.useful.DoubleLinkedList;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.io.FileWriter;
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
            if(blockUpdates.isEmpty())
                if(!GreenStone.getPlugin().config.getBoolean("recordGaps"))
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
            Path path = Paths.get("plugins\\Green_Stone\\Recordings");

            if(!Files.exists(path))
                Files.createDirectory(path);

            FileWriter writer = new FileWriter(path + "\\" + name + ".gsrec");

            writer.write(this.toString());

            writer.close();

        }catch (IOException ex) {
            GreenStone.getPluginLogger().info("The recording could not be saved");
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for(WorldState state : worldStates) {
            builder.append(state.toString()).append("\n");
        }

        return builder.toString();
    }
}
