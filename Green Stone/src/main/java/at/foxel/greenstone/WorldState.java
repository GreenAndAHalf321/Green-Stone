// © 2024 Foxel e.U.
//
// SPDX-License-Identifier: GPL-3.0-or-later

package at.foxel.greenstone;

import java.util.LinkedList;

public class WorldState {
    private final LinkedList<BlockState> blockStates = new LinkedList<>();

    public void addBlockState(BlockState state) {

        for (BlockState stateInList : blockStates)
            if(stateInList.getLocation().equals(state.getLocation())) {
                blockStates.remove();
                break;
            }
        blockStates.add(state);
    }
    public LinkedList<BlockState> getBlockStates() {
        return (LinkedList<BlockState>) blockStates.clone();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for(BlockState blockState : blockStates)
            builder.append(blockState.toString()).append("\n");

        return builder.toString();
    }
}
