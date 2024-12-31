package xdaily.voucher.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xdaily.voucher.XDailyVouchers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiManager {
    private final XDailyVouchers plugin;
    private final Map<Integer, Integer> slotDayMap;
    private final DailyRewardItem dailyRewardItem;
    private static final int STATUS_SLOT = 35;

    public GuiManager(XDailyVouchers plugin) {
        this.plugin = plugin;
        this.slotDayMap = new HashMap<>();
        this.dailyRewardItem = new DailyRewardItem(plugin);
        initializeDaySlots();
    }

    private void initializeDaySlots() {
        slotDayMap.put(19, 1); // Day 1
        slotDayMap.put(11, 2); // Day 2
        slotDayMap.put(21, 3); // Day 3
        slotDayMap.put(13, 4); // Day 4
        slotDayMap.put(23, 5); // Day 5
        slotDayMap.put(15, 6); // Day 6
        slotDayMap.put(25, 7); // Day 7
    }

    public void openDailyRewardsGui(Player player) {
        Inventory gui = Bukkit.createInventory(null, 36, "Daily Rewards");

        // Set daily reward items
        for (Map.Entry<Integer, Integer> entry : slotDayMap.entrySet()) {
            gui.setItem(entry.getKey(), dailyRewardItem.create(player, entry.getValue()));
        }

        // Set player status item
        gui.setItem(STATUS_SLOT, createStatusItem(player));

        player.openInventory(gui);
    }

    private ItemStack createStatusItem(Player player) {
        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§bYour Status");
            List<String> lore = new ArrayList<>();
            int streak = plugin.getUserData().getStreak(player.getUniqueId());
            int week = (streak / 7) + 1;
            lore.add("§7Current streak: " + streak + " days");
            lore.add("§7Current week: " + week);
            lore.add("§7Multiplier: " + String.format("%.1fx", 1 + (streak / 7) * 0.1));
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public boolean isRewardSlot(int slot) {
        return slotDayMap.containsKey(slot);
    }

    public int getDayFromSlot(int slot) {
        return slotDayMap.getOrDefault(slot, -1);
    }
}