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

    public Location getLocation() {
        return location;
    }
}
