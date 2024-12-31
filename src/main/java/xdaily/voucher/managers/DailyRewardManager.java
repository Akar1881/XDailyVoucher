package xdaily.voucher.managers;

import xdaily.voucher.XDailyVouchers;
import org.bukkit.entity.Player;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class DailyRewardManager {
    private final XDailyVouchers plugin;

    public DailyRewardManager(XDailyVouchers plugin) {
        this.plugin = plugin;
    }

    public void giveDailyReward(Player player) {
        UUID playerId = player.getUniqueId();
        updateStreak(playerId);
        int streak = plugin.getUserData().getStreak(playerId);
        
        double baseReward = plugin.getConfig().getDouble("dailyreward", 100);
        double multiplier = calculateMultiplier(streak);
        double finalReward = baseReward * multiplier;

        // Execute reward command
        String command = "eco give " + player.getName() + " " + (int)finalReward;
        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);
        
        // Send message to player
        String message = plugin.getConfig().getString("messages.reward", "&aYou received your daily reward: &6%amount% coins&a!")
            .replace("%amount%", String.valueOf((int)finalReward))
            .replace("&", "§");
        player.sendMessage(message);
    }

    private double calculateMultiplier(int streak) {
        double baseMultiplier = 1.0;
        double increasePerWeek = plugin.getConfig().getDouble("strikreward", 0.1);
        int weeks = (streak - 1) / 7;
        return baseMultiplier + (weeks * increasePerWeek);
    }

    private void updateStreak(UUID playerId) {
        String lastDailyStr = plugin.getUserData().getLastDaily(playerId);
        if (lastDailyStr == null) {
            plugin.getUserData().setStreak(playerId, 1);
            return;
        }

        LocalDate lastDaily = LocalDate.parse(lastDailyStr);
        LocalDate today = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(lastDaily, today);

        if (daysBetween == 1) {
            // Next day, increase streak
            plugin.getUserData().setStreak(playerId, plugin.getUserData().getStreak(playerId) + 1);
        } else if (daysBetween > 1) {
            // Streak broken
            plugin.getUserData().setStreak(playerId, 1);
        }
        // If daysBetween == 0, same day, don't update streak
    }

    public void checkDailyReward(Player player) {
        UUID playerId = player.getUniqueId();
        if (plugin.getUserData().canClaimDaily(playerId)) {
            String message = plugin.getConfig().getString("messages.available", "&aYou have a daily reward available! Use &6/xdv daily&a to claim it!")
                .replace("&", "§");
            player.sendMessage(message);
        }
    }
}