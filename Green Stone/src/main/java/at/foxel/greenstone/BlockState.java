package at.foxel.greenstone;

import org.bukkit.Location;
import org.bukkit.Material;

public class BlockState {
    private final Material material;
    private final Location location;

    public BlockState(Material material, Location location) {
        this.material = material;
        this.location = location;
    }

    public Material getMaterial() {
        return material;
    }

    public Location getLocation() {
        return location;
    }
}
