package xdaily.voucher.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import xdaily.voucher.XDailyVouchers;
import xdaily.voucher.gui.VoucherItemsGui;

public class VoucherItemsGuiListener implements Listener {
    private final XDailyVouchers plugin;

    public VoucherItemsGuiListener(XDailyVouchers plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (title.startsWith("Edit Voucher: ")) {
            String voucherName = title.substring("Edit Voucher: ".length());
            VoucherItemsGui gui = new VoucherItemsGui(plugin, voucherName);
            gui.handleClick(event);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        String title = event.getView().getTitle();
        if (title.startsWith("Edit Voucher: ")) {
            String voucherName = title.substring("Edit Voucher: ".length());
            VoucherItemsGui gui = new VoucherItemsGui(plugin, voucherName);
            gui.handleClose(event);
        }
    }
}