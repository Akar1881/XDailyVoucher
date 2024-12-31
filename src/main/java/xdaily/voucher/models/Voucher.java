package xdaily.voucher.models;

import org.bukkit.Material;
import java.util.List;

public class Voucher {
    private final String name;
    private final String display;
    private final Material itemType;
    private final List<String> commands;
    private final List<String> rewards;

    public Voucher(String name, String display, Material itemType, List<String> commands, List<String> rewards) {
        this.name = name;
        this.display = display;
        this.itemType = itemType;
        this.commands = commands;
        this.rewards = rewards;
    }

    public String getName() { return name; }
    public String getDisplay() { return display.replace("&", "§"); }
    public Material getItemType() { return itemType; }
    public List<String> getCommands() { return commands; }
    public List<String> getRewards() { return rewards; }
}