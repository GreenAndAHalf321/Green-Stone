package at.foxel.greenstone;

import org.bukkit.block.Block;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Recording {
    private final Queue<Block> blockUpdates = new ConcurrentLinkedQueue<>();
    private final LinkedList<State> states = new LinkedList<>();
    private final String name;

    public Recording(String name) {
        this.name = name;
    }

    public LinkedList<State> getStates() {
        return (LinkedList<State>) states.clone();
    }

    public String getName() {
        return name;
    }
}
