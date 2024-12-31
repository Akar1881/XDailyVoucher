package xdaily.voucher.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xdaily.voucher.XDailyVouchers;

import java.util.ArrayList;
import java.util.List;

public class DailyRewardItem {
    private final XDailyVouchers plugin;

    public DailyRewardItem(XDailyVouchers plugin) {
        this.plugin = plugin;
    }

    public ItemStack create(Player player, int baseDay) {
        int streak = plugin.getUserData().getStreak(player.getUniqueId());
        int actualDay = baseDay + ((streak / 7) * 7);
        
        ItemStack item = new ItemStack(Material.GOLD_INGOT);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6Day " + actualDay);
            List<String> lore = new ArrayList<>();
            
            boolean claimed = plugin.getUserData().hasClaimedDay(player.getUniqueId(), actualDay);
            boolean canClaim = plugin.getUserData().canClaimDaily(player.getUniqueId());
            boolean isCurrentDay = baseDay == (streak % 7) + 1;
            
            if (claimed) {
                lore.add("§cAlready claimed");
                item.setType(Material.BARRIER);
            } else if (!isCurrentDay) {
                lore.add("§7Complete previous days first!");
                item.setType(Material.GRAY_DYE);
            } else if (canClaim) {
                lore.add("§aClick to claim!");
                lore.add("§7Reward: " + calculateReward(actualDay));
                item.setType(Material.GOLD_INGOT);
            } else {
                lore.add("§7Come back tomorrow!");
                item.setType(Material.CLOCK);
            }
            
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    private String calculateReward(int day) {
        double baseReward = plugin.getConfig().getDouble("dailyreward", 100);
        double multiplier = 1 + ((day - 1) / 7) * plugin.getConfig().getDouble("strikreward", 0.1);
        return String.format("%.0f", baseReward * multiplier);
    }
}