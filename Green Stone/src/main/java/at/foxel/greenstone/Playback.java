package at.foxel.greenstone;

import at.foxel.greenstone.useful.DoubleReferenceNode;
import org.bukkit.Bukkit;

public class Playback {
    private static Recording recordingToPlayBack;
    private static DoubleReferenceNode<WorldState> currentWorldState;

    public static void startPlayback(Recording recording) {
        recordingToPlayBack = recording;

        currentWorldState = recording.getWorldStates().getLast();

        while (!currentWorldState.equals(recording.getWorldStates().getFirst())) {
            resetWorldState(currentWorldState.item);
            currentWorldState = currentWorldState.previews;
        }
        resetWorldState(currentWorldState.item);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(GreenStone.getPlugin(), () -> {
            recreateWorldState(currentWorldState.item);

            if(currentWorldState.next == null)
                Bukkit.getScheduler().cancelTasks(GreenStone.getPlugin());

            currentWorldState = currentWorldState.next;
        }, 0, 1000);
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
