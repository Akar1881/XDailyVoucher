package xdaily.voucher.models;

import org.bukkit.Material;

public class Voucher {
    private final String name;
    private final String display;
    private final Material itemType;
    private final String command;
    private final String reward;

    public Voucher(String name, String display, Material itemType, String command, String reward) {
        this.name = name;
        this.display = display;
        this.itemType = itemType;
        this.command = command;
        this.reward = reward;
    }

    public String getName() { return name; }
    public String getDisplay() { return display; }
    public Material getItemType() { return itemType; }
    public String getCommand() { return command; }
    public String getReward() { return reward; }
}