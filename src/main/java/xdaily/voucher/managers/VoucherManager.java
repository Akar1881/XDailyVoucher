package xdaily.voucher.managers;

import xdaily.voucher.XDailyVouchers;
import xdaily.voucher.models.Voucher;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class VoucherManager {
    private final XDailyVouchers plugin;
    private final Map<String, Voucher> activeVouchers;
    private final File vouchersFolder;

    public VoucherManager(XDailyVouchers plugin) {
        this.plugin = plugin;
        this.activeVouchers = new HashMap<>();
        this.vouchersFolder = new File(plugin.getDataFolder(), "vouchers");
        if (!vouchersFolder.exists()) {
            vouchersFolder.mkdirs();
        }
        loadVouchers();
    }

    public void loadVouchers() {
        activeVouchers.clear();
        File[] files = vouchersFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files != null) {
            for (File file : files) {
                loadVoucher(file);
            }
        }
    }

    private void loadVoucher(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        String name = file.getName().replace(".yml", "");
        String display = config.getString(name + ".display");
        Material itemType = Material.valueOf(config.getString(name + ".itemtype"));
        List<String> commands = config.getStringList(name + ".commands");
        List<String> rewards = config.getStringList(name + ".rewards");
        
        Voucher voucher = new Voucher(name, display, itemType, commands, rewards);
        activeVouchers.put(name, voucher);
    }

    public boolean createVoucher(String name, String display, Material itemType, List<String> commands, List<String> rewards) {
        File file = new File(vouchersFolder, name + ".yml");
        if (file.exists()) return false;

        YamlConfiguration config = new YamlConfiguration();
        config.set(name + ".display", display);
        config.set(name + ".itemtype", itemType.name());
        config.set(name + ".commands", commands);
        config.set(name + ".rewards", rewards);

        try {
            config.save(file);
            loadVoucher(file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ItemStack> getVoucherItems(String voucherName) {
        File file = new File(vouchersFolder, voucherName + ".yml");
        if (!file.exists()) return new ArrayList<>();

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        @SuppressWarnings("unchecked")
        List<ItemStack> items = (List<ItemStack>) config.getList(voucherName + ".items", new ArrayList<>());
        return items;
    }

    public void saveVoucherItems(String voucherName, List<ItemStack> items) {
        File file = new File(vouchersFolder, voucherName + ".yml");
        if (!file.exists()) return;

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(voucherName + ".items", items);
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save voucher items: " + e.getMessage());
        }
    }

    public void giveVoucherItems(Player player, String voucherName) {
        List<ItemStack> items = getVoucherItems(voucherName);
        if (items != null && !items.isEmpty()) {
            for (ItemStack item : items) {
                if (item != null) {
                    player.getInventory().addItem(item.clone());
                }
            }
            player.sendMessage("§aYou received your voucher items!");
        }
    }

    public boolean activateVoucher(String name) {
        File file = new File(vouchersFolder, name + ".yml");
        if (!file.exists()) return false;
        
        if (!activeVouchers.containsKey(name)) {
            loadVoucher(file);
            return true;
        }
        return false;
    }

    public boolean deactivateVoucher(String name) {
        if (activeVouchers.containsKey(name)) {
            activeVouchers.remove(name);
            return true;
        }
        return false;
    }

    public boolean isVoucherActive(String name) {
        return activeVouchers.containsKey(name);
    }

    public Map<String, Voucher> getActiveVouchers() {
        return activeVouchers;
    }

    public void giveVoucher(Player player, String voucherName, int amount) {
        Voucher voucher = activeVouchers.get(voucherName);
        if (voucher == null) return;

        ItemStack item = createVoucherItem(voucher);
        item.setAmount(amount);
        player.getInventory().addItem(item);
    }

    private ItemStack createVoucherItem(Voucher voucher) {
        ItemStack item = new ItemStack(voucher.getItemType());
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(voucher.getDisplay());
            List<String> lore = new ArrayList<>();
            for (String reward : voucher.getRewards()) {
                lore.add("§7" + reward.replace("&", "§"));
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }
}