package xdaily.voucher.managers;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xdaily.voucher.XDailyVouchers;
import xdaily.voucher.utils.DayCalculator;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DailyItemsManager {
    private final XDailyVouchers plugin;
    private final File rewardsFile;
    private YamlConfiguration rewardsConfig;

    public DailyItemsManager(XDailyVouchers plugin) {
        this.plugin = plugin;
        this.rewardsFile = new File(plugin.getDataFolder(), "dailyrewards.yml");
        loadConfig();
    }

    private void loadConfig() {
        if (!rewardsFile.exists()) {
            plugin.saveResource("dailyrewards.yml", false);
        }
        rewardsConfig = YamlConfiguration.loadConfiguration(rewardsFile);
    }

    public void saveItems(String type, int number, List<ItemStack> items) {
        int targetDay = DayCalculator.calculateTargetDay(type, number);
        String path = "day_" + targetDay + ".items";
        rewardsConfig.set(path, items);
        try {
            rewardsConfig.save(rewardsFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save daily rewards: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public List<ItemStack> getItems(int day) {
        String path = "day_" + day + ".items";
        return (List<ItemStack>) rewardsConfig.getList(path);
    }

    public void giveItems(Player player, int day) {
        List<ItemStack> items = getItems(day);
        if (items != null && !items.isEmpty()) {
            for (ItemStack item : items) {
                if (item != null) {
                    player.getInventory().addItem(item.clone());
                }
            }
            player.sendMessage("§aYou received your day " + day + " item rewards!");
        }
    }
}