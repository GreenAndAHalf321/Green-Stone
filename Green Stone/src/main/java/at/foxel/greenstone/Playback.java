package at.foxel.greenstone;

import at.foxel.greenstone.useful.DoubleReferenceNode;

import java.util.Timer;
import java.util.TimerTask;

public class Playback {
    private static Recording recordingToPlayBack;
    private static DoubleReferenceNode<WorldState> currentWorldState;
    private static Timer timer;
    private static TimerTask task = new TimerTask() {
        @Override
        public void run() {
            recreateWorldState(currentWorldState.item);

            if(currentWorldState.next == null)
                timer.cancel();

            currentWorldState = currentWorldState.next;
        }
    };

    public static void startPlayback(Recording recording) {
        recordingToPlayBack = recording;

        currentWorldState = recording.getWorldStates().getLast();

        while (!currentWorldState.equals(recording.getWorldStates().getFirst())) {
            resetWorldState(currentWorldState.item);
            currentWorldState = currentWorldState.previews;
        }
        resetWorldState(currentWorldState.item);

        timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, 1000);
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
