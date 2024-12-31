package xdaily.voucher.data;

import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;
import xdaily.voucher.XDailyVouchers;

public class UserData {
    private final XDailyVouchers plugin;
    private final File userFile;
    private YamlConfiguration userConfig;

    public UserData(XDailyVouchers plugin) {
        this.plugin = plugin;
        this.userFile = new File(plugin.getDataFolder(), "users.yml");
        loadData();
    }

    public void loadData() {
        if (!userFile.exists()) {
            try {
                plugin.saveResource("users.yml", false);
            } catch (IllegalArgumentException e) {
                try {
                    userFile.getParentFile().mkdirs();
                    userFile.createNewFile();
                } catch (IOException ex) {
                    plugin.getLogger().severe("Could not create users.yml: " + ex.getMessage());
                }
            }
        }
        userConfig = YamlConfiguration.loadConfiguration(userFile);
    }

    public void saveData() {
        try {
            userConfig.save(userFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save users.yml: " + e.getMessage());
        }
    }

    public int getStreak(UUID playerId) {
        return userConfig.getInt(playerId + ".streak", 0);
    }

    public void setStreak(UUID playerId, int streak) {
        userConfig.set(playerId + ".streak", streak);
        userConfig.set(playerId + ".lastDaily", LocalDate.now().toString());
        saveData();
    }

    public String getLastDaily(UUID playerId) {
        return userConfig.getString(playerId + ".lastDaily");
    }

    public boolean canClaimDaily(UUID playerId) {
        String lastDaily = getLastDaily(playerId);
        if (lastDaily == null) return true;
        return !LocalDate.parse(lastDaily).equals(LocalDate.now());
    }

    public boolean hasClaimedDay(UUID playerId, int day) {
        return userConfig.getBoolean(playerId + ".claimed." + day, false);
    }

    public void setDayClaimed(UUID playerId, int day) {
        userConfig.set(playerId + ".claimed." + day, true);
        saveData();
    }
}