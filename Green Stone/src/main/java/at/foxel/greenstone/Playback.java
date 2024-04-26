package at.foxel.greenstone;

import at.foxel.greenstone.useful.DoubleReferenceNode;
import org.bukkit.Bukkit;

public class Playback {
    private static Recording recordingToPlayBack;
    private static DoubleReferenceNode<WorldState> currentWorldState;

    private static int currentPlaybackId;

    public static void startPlayback(Recording recording) {
        //TODO Check if a playback or recording is already running
        recordingToPlayBack = recording;

        currentWorldState = recording.getWorldStates().getLast();

        while (!currentWorldState.equals(recording.getWorldStates().getFirst())) {
            resetWorldState(currentWorldState.item);
            currentWorldState = currentWorldState.previews;
        }
        resetWorldState(currentWorldState.item);

        currentPlaybackId = Bukkit.getScheduler().scheduleSyncRepeatingTask(GreenStone.getPlugin(), () -> {
            recreateWorldState(currentWorldState.item);

            if(currentWorldState.next == null)
                Bukkit.getScheduler().cancelTask(currentPlaybackId);

            currentWorldState = currentWorldState.next;
        }, 0, 20);
    }

    private static void recreateWorldState(WorldState state) {
        for (BlockState blockState : state.getBlockStates()) {

            GreenStone.getPlugin().scheduleSyncCallable(() -> {
                blockState.getLocation().getBlock().setType(blockState.getMaterialTo());
                return null;
            });
        }
    }

    private static void resetWorldState(WorldState state) {
        for (BlockState blockState : state.getBlockStates()) {

            GreenStone.getPlugin().scheduleSyncCallable(() -> {
                blockState.getLocation().getBlock().setType(blockState.getMaterialFrom());
                return null;
            });
        }
    }
    public static boolean isPlaybackRunning() { return recordingToPlayBack != null; }
}
