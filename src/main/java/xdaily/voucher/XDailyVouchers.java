package xdaily.voucher;

import org.bukkit.plugin.java.JavaPlugin;
import xdaily.voucher.commands.CommandManager;
import xdaily.voucher.managers.VoucherManager;
import xdaily.voucher.managers.DailyRewardManager;
import xdaily.voucher.managers.DailyItemsManager;
import xdaily.voucher.listeners.GuiListener;
import xdaily.voucher.listeners.PlayerListener;
import xdaily.voucher.listeners.DailyItemsGuiListener;
import xdaily.voucher.gui.GuiManager;
import xdaily.voucher.data.UserData;
import xdaily.voucher.data.RedeemData;

public class XDailyVouchers extends JavaPlugin {
    private static XDailyVouchers instance;
    private VoucherManager voucherManager;
    private DailyRewardManager dailyRewardManager;
    private DailyItemsManager dailyItemsManager;
    private GuiManager guiManager;
    private CommandManager commandManager;
    private UserData userData;
    private RedeemData redeemData;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        
        initializeManagers();
        registerCommands();
        registerListeners();
        
        getLogger().info("XDailyVouchers has been enabled!");
    }

    private void initializeManagers() {
        this.userData = new UserData(this);
        this.redeemData = new RedeemData(this);
        this.voucherManager = new VoucherManager(this);
        this.dailyRewardManager = new DailyRewardManager(this);
        this.dailyItemsManager = new DailyItemsManager(this);
        this.guiManager = new GuiManager(this);
        this.commandManager = new CommandManager(this);
    }

    private void registerCommands() {
        getCommand("xdv").setExecutor(commandManager);
        getCommand("xdv").setTabCompleter(commandManager);
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new GuiListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new DailyItemsGuiListener(this), this);
    }

    public void reloadPlugin() {
        reloadConfig();
        this.dailyItemsManager = new DailyItemsManager(this);
        this.voucherManager = new VoucherManager(this);
        this.userData.loadData();
        this.redeemData.loadData();
    }

    @Override
    public void onDisable() {
        if (userData != null) {
            userData.saveData();
        }
        if (redeemData != null) {
            redeemData.saveData();
        }
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

    public DailyItemsManager getDailyItemsManager() {
        return dailyItemsManager;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public UserData getUserData() {
        return userData;
    }

    public RedeemData getRedeemData() {
        return redeemData;
    }
}