package xdaily.voucher.data;

import org.bukkit.configuration.file.YamlConfiguration;
import xdaily.voucher.XDailyVouchers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class RedeemData {
    private final XDailyVouchers plugin;
    private final File redeemFile;
    private YamlConfiguration redeemConfig;

    public RedeemData(XDailyVouchers plugin) {
        this.plugin = plugin;
        this.redeemFile = new File(plugin.getDataFolder(), "redeem.yml");
        loadData();
    }

    public void loadData() {
        if (!redeemFile.exists()) {
            try {
                plugin.saveResource("redeem.yml", false);
            } catch (IllegalArgumentException e) {
                try {
                    redeemFile.createNewFile();
                } catch (IOException ex) {
                    plugin.getLogger().severe("Could not create redeem.yml: " + ex.getMessage());
                }
            }
        }
        redeemConfig = YamlConfiguration.loadConfiguration(redeemFile);
    }

    public void saveData() {
        try {
            redeemConfig.save(redeemFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save redeem.yml: " + e.getMessage());
        }
    }

    public boolean activateVoucher(String voucherName, int maxUsers, int maxVouchers) {
        redeemConfig.set(voucherName + ".active", true);
        redeemConfig.set(voucherName + ".maxUsers", maxUsers);
        redeemConfig.set(voucherName + ".maxVouchers", maxVouchers);
        saveData();
        return true;
    }

    public boolean deactivateVoucher(String voucherName) {
        redeemConfig.set(voucherName, null);
        saveData();
        return true;
    }

    public boolean isVoucherActive(String voucherName) {
        return redeemConfig.getBoolean(voucherName + ".active", false);
    }

    public boolean canRedeem(String voucherName, UUID playerId) {
        if (!isVoucherActive(voucherName)) return false;

        List<String> redeemedUsers = redeemConfig.getStringList(voucherName + ".redeemedUsers");
        int maxUsers = redeemConfig.getInt(voucherName + ".maxUsers");
        
        return !redeemedUsers.contains(playerId.toString()) && 
               redeemedUsers.size() < maxUsers;
    }

    public boolean addRedeemedUser(String voucherName, UUID playerId) {
        List<String> redeemedUsers = redeemConfig.getStringList(voucherName + ".redeemedUsers");
        redeemedUsers.add(playerId.toString());
        redeemConfig.set(voucherName + ".redeemedUsers", redeemedUsers);
        saveData();
        return true;
    }

    public int getMaxVouchers(String voucherName) {
        return redeemConfig.getInt(voucherName + ".maxVouchers", 0);
    }
}