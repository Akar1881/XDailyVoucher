package xdaily.voucher.gui;

import xdaily.voucher.XDailyVouchers;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiManager {
    private final XDailyVouchers plugin;

    public GuiManager(XDailyVouchers plugin) {
        this.plugin = plugin;
    }

    public void openDailyRewardsGui(Player player) {
        Inventory gui = Bukkit.createInventory(null, 36, "Daily Rewards");
        
        for (int i = 0; i < 35; i++) {
            ItemStack item = createDayItem(i + 1);
            gui.setItem(i, item);
        }

        // Streak information in last slot
        gui.setItem(35, createStreakItem(player));
        
        player.openInventory(gui);
    }

    private ItemStack createDayItem(int day) {
        ItemStack item = new ItemStack(Material.GOLD_INGOT);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6Day " + day);
            List<String> lore = new ArrayList<>();
            lore.add("§7Reward: " + calculateReward(day));
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    private ItemStack createStreakItem(Player player) {
        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§bYour Streak");
            List<String> lore = new ArrayList<>();
            lore.add("§7Current streak: " + getCurrentStreak(player));
            lore.add("§7Multiplier: " + getMultiplier(player));
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    private String calculateReward(int day) {
        double baseReward = plugin.getConfig().getDouble("dailyreward", 100);
        double multiplier = 1 + (day / 7) * plugin.getConfig().getDouble("strikreward", 0.1);
        return String.format("%.0f", baseReward * multiplier);
    }

    private int getCurrentStreak(Player player) {
        // This should be implemented to get the actual streak from DailyRewardManager
        return 0;
    }

    private String getMultiplier(Player player) {
        int streak = getCurrentStreak(player);
        double multiplier = 1 + (streak / 7) * plugin.getConfig().getDouble("strikreward", 0.1);
        return String.format("%.1fx", multiplier);
    }
}