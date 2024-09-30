// © 2024 Florian Wallner
//
// SPDX-License-Identifier: GPL-3.0-or-later

package at.foxel.greenstone;

import org.bukkit.Location;
import org.bukkit.Material;

public class BlockState {
    private final Material materialFrom;
    private final Material materialTo;
    private final Location location;

    public BlockState(Material materialFrom, Material materialTo, Location location) {
        this.materialFrom = materialFrom;
        this.materialTo = materialTo;
        this.location = location;
    }

    public Material getMaterialFrom() {
        return materialFrom;
    }
    public Material getMaterialTo() {
        return materialTo;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return materialFrom.toString() +
                ';' +
                materialTo +
                ";(" +
                location.getX() +
                ';' +
                location.getY() +
                ';' +
                location.getZ() +
                ')';
    }
}
