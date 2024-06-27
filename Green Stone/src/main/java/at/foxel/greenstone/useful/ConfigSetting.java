package at.foxel.greenstone.useful;

import org.bukkit.Material;

import java.util.LinkedList;

public class ConfigSetting {

    private ConfigSetting(String id, String name, String description, Material material, Object defaultSetting) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.material = material;
        this.defaultSetting = defaultSetting;
        settings.add(this);
    }
    private static final LinkedList<ConfigSetting> settings = new LinkedList<>();
    private final String id;
    private final String name;
    private final String description;
    private final Material material;
    private final Object defaultSetting;

    public static void setupConfig() {
        //===== Command execution ======
        //Allow execution via Player
        new ConfigSetting(
                "allowExecutionViaPlayer",
                "Command execution via player",
                "Allows players to use this plugins commands (admins are alway allowed to do so)",
                Material.ZOMBIE_HEAD,
                true);
        //Allow execution via Console
        new ConfigSetting(
                "allowExecutionViaConsole",
                "Command execution via console",
                "Allows the servers console to use this plugins Commands",
                Material.REDSTONE,
                false);
        //Allow execution via command block
        new ConfigSetting(
                "allowExecutionViaCommandBLock",
                Colors.WHITE + "Command execution via command block",
                "Allows command blocks to use this plugins commands",
                Material.REDSTONE,
                false);

        //===== Recording =====
        //Record player
        new ConfigSetting(
                "recordPlayer",
                Colors.WHITE + "Record players",
                "Also record the movement of all players",
                Material.ZOMBIE_HEAD,
                false);
        //Record entities
        new ConfigSetting(
                "recordEntities",
                Colors.WHITE + "Record entities",
                "Also record the movement of all entities like animals and monsters",
                Material.AXOLOTL_SPAWN_EGG,
                false);
        //Record gaps
        new ConfigSetting(
                "recordGaps",
                "Record gaps where no block has been changed",
                "Also includes the moments were nothing has happened in the recording",
                Material.GLASS_PANE,
                false);
        //Default recording interval in milliseconds
        new ConfigSetting(
                "defaultRecordingIntervalInMilliseconds",
                "Default recording interval",
                "The default interval in which all changes to the world are saved in milliseconds (this only" +
                        "applies to recording where to interval is not specified)",
                Material.RED_STAINED_GLASS_PANE,
                1000);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Material getMaterial() {
        return material;
    }

    public Object isDefaultSetting() {
        return defaultSetting;
    }

    public LinkedList<ConfigSetting> getSettings() {
        return (LinkedList<ConfigSetting>) settings.clone();
    }
}
