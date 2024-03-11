package at.foxel.greenstone;

import java.util.Timer;
import java.util.TimerTask;

public class Playback {
    private static Recording recordingToPlayBack;
    private static int index = 0;
    private static Timer timer;
    private static TimerTask task = new TimerTask() {
        @Override
        public void run() {
            recreateWorldState(recordingToPlayBack.getWorldStates().getItemByIndex(index));
            index++;
        }
    };

    public static void startPlayback(Recording recording) {
        recordingToPlayBack = recording;

        timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    private static void recreateWorldState(WorldState state) {
        for (BlockState blockState : state.getBlockStates()) {

            GreenStone.getPlugin().scheduleSyncCallable(() -> {
                blockState.getLocation().getBlock().setType(blockState.getMaterialFrom());
                return null;
            });
        }
    }
    public static boolean isPlaybackRunning() { return recordingToPlayBack != null; }
}
