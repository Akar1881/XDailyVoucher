package xdaily.voucher.managers;

import xdaily.voucher.XDailyVouchers;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DailyRewardManager {
    private final XDailyVouchers plugin;
    private final Map<UUID, PlayerStreak> playerStreaks;
    private final File dataFile;
    private YamlConfiguration data;

    public DailyRewardManager(XDailyVouchers plugin) {
        this.plugin = plugin;
        this.playerStreaks = new HashMap<>();
        this.dataFile = new File(plugin.getDataFolder(), "player_data.yml");
        loadData();
    }

    private void loadData() {
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.data = YamlConfiguration.loadConfiguration(dataFile);
    }

    public void checkDailyReward(Player player) {
        UUID playerId = player.getUniqueId();
        PlayerStreak streak = playerStreaks.computeIfAbsent(playerId, 
            k -> new PlayerStreak(data.getString(playerId + ".lastClaim")));

        if (streak.canClaimToday()) {
            giveDailyReward(player, streak);
            streak.updateStreak();
            savePlayerData(playerId, streak);
        }
    }

    private void giveDailyReward(Player player, PlayerStreak streak) {
        double baseReward = plugin.getConfig().getDouble("dailyreward", 100);
        double multiplier = 1 + (streak.getStreakCount() / 7) * 
            plugin.getConfig().getDouble("strikreward", 0.1);
        double finalReward = baseReward * multiplier;

        // Execute reward command
        String command = "eco give " + player.getName() + " " + (int)finalReward;
        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);
    }

    private void savePlayerData(UUID playerId, PlayerStreak streak) {
        data.set(playerId + ".lastClaim", streak.getLastClaimDate());
        data.set(playerId + ".streak", streak.getStreakCount());
        try {
            data.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class PlayerStreak {
        private LocalDate lastClaim;
        private int streakCount;

        public PlayerStreak(String lastClaimStr) {
            this.lastClaim = lastClaimStr != null ? LocalDate.parse(lastClaimStr) : null;
            this.streakCount = 0;
        }

        public boolean canClaimToday() {
            LocalDate today = LocalDate.now();
            return lastClaim == null || !lastClaim.equals(today);
        }

        public void updateStreak() {
            LocalDate today = LocalDate.now();
            if (lastClaim != null && lastClaim.plusDays(1).equals(today)) {
                streakCount++;
            } else {
                streakCount = 1;
            }
            lastClaim = today;
        }

        public String getLastClaimDate() {
            return lastClaim.toString();
        }

        public int getStreakCount() {
            return streakCount;
        }
    }
}