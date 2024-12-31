package xdaily.voucher.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import xdaily.voucher.XDailyVouchers;
import xdaily.voucher.utils.DayCalculator;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class DailyItemsGui {
    private final XDailyVouchers plugin;
    private final String type;
    private final int number;
    private static final int[] ITEM_SLOTS = {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25};
    private static final int CLEAR_SLOT = 40;

    public DailyItemsGui(XDailyVouchers plugin, String type, int number) {
        this.plugin = plugin;
        this.type = type;
        this.number = number;
    }

    public void openGui(Player player) {
        Inventory gui = Bukkit.createInventory(null, 54, "Set " + type + " " + number + " Rewards");
        
        // Load existing items
        List<ItemStack> existingItems = plugin.getDailyItemsManager().getItems(
            DayCalculator.calculateTargetDay(type, number)
        );
        
        if (existingItems != null) {
            for (int i = 0; i < Math.min(existingItems.size(), ITEM_SLOTS.length); i++) {
                gui.setItem(ITEM_SLOTS[i], existingItems.get(i));
            }
        }

        // Add clear button
        ItemStack clearButton = new ItemStack(Material.BARRIER);
        ItemMeta clearMeta = clearButton.getItemMeta();
        if (clearMeta != null) {
            clearMeta.setDisplayName("§c§lClear All Items");
            clearMeta.setLore(Arrays.asList("§7Click to remove all items"));
            clearButton.setItemMeta(clearMeta);
        }
        gui.setItem(CLEAR_SLOT, clearButton);

        // Add glass panes for decoration
        ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        if (fillerMeta != null) {
            fillerMeta.setDisplayName(" ");
            filler.setItemMeta(fillerMeta);
        }
        
        for (int i = 0; i < gui.getSize(); i++) {
            if (gui.getItem(i) == null && !isItemSlot(i)) {
                gui.setItem(i, filler);
            }
        }

        player.openInventory(gui);
    }

    public void handleClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        
        int slot = event.getSlot();
        
        // Allow clicking in player inventory
        if (event.getClickedInventory() == event.getWhoClicked().getInventory()) {
            event.setCancelled(false);
            return;
        }
        
        // Handle clear button
        if (slot == CLEAR_SLOT) {
            event.setCancelled(true);
            clearItems(event.getInventory());
            return;
        }
        
        // Cancel clicks on filler items
        if (!isItemSlot(slot)) {
            event.setCancelled(true);
            return;
        }
    }

    public void handleClose(InventoryCloseEvent event) {
        List<ItemStack> items = new ArrayList<>();
        for (int slot : ITEM_SLOTS) {
            ItemStack item = event.getInventory().getItem(slot);
            if (item != null && item.getType() != Material.AIR) {
                items.add(item);
            }
        }
        
        plugin.getDailyItemsManager().saveItems(type, number, items);
        event.getPlayer().sendMessage("§aRewards saved successfully!");
    }

    private boolean isItemSlot(int slot) {
        for (int itemSlot : ITEM_SLOTS) {
            if (slot == itemSlot) return true;
        }
        return false;
    }

    private void clearItems(Inventory inventory) {
        for (int slot : ITEM_SLOTS) {
            inventory.setItem(slot, null);
        }
    }
}