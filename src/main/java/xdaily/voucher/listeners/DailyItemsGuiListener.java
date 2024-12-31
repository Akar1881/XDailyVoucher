package xdaily.voucher.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import xdaily.voucher.XDailyVouchers;
import xdaily.voucher.gui.DailyItemsGui;

public class DailyItemsGuiListener implements Listener {
    private final XDailyVouchers plugin;

    public DailyItemsGuiListener(XDailyVouchers plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (title.startsWith("Set ")) {
            String[] parts = title.split(" ");
            if (parts.length >= 4) {
                String type = parts[1];
                int number = Integer.parseInt(parts[2]);
                
                DailyItemsGui gui = new DailyItemsGui(plugin, type, number);
                gui.handleClick(event);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        String title = event.getView().getTitle();
        if (title.startsWith("Set ")) {
            String[] parts = title.split(" ");
            if (parts.length >= 4) {
                String type = parts[1];
                int number = Integer.parseInt(parts[2]);
                
                DailyItemsGui gui = new DailyItemsGui(plugin, type, number);
                gui.handleClose(event);
            }
        }
    }
}