package xdaily.voucher;

import org.bukkit.plugin.java.JavaPlugin;
import xdaily.voucher.commands.CommandManager;
import xdaily.voucher.managers.VoucherManager;
import xdaily.voucher.managers.DailyRewardManager;
import xdaily.voucher.listeners.PlayerListener;
import xdaily.voucher.gui.GuiManager;

public class XDailyVouchers extends JavaPlugin {
    private static XDailyVouchers instance;
    private VoucherManager voucherManager;
    private DailyRewardManager dailyRewardManager;
    private GuiManager guiManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        
        this.voucherManager = new VoucherManager(this);
        this.dailyRewardManager = new DailyRewardManager(this);
        this.guiManager = new GuiManager(this);
        
        getCommand("xdv").setExecutor(new CommandManager(this));
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        
        getLogger().info("XDailyVouchers has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("XDailyVouchers has been disabled!");
    }

    public static XDailyVouchers getInstance() {
        return instance;
    }

    public VoucherManager getVoucherManager() {
        return voucherManager;
    }

    public DailyRewardManager getDailyRewardManager() {
        return dailyRewardManager;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }
}