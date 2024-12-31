package xdaily.voucher.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import xdaily.voucher.XDailyVouchers;

public class GuiListener implements Listener {
    private final XDailyVouchers plugin;

    public GuiListener(XDailyVouchers plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!"Daily Rewards".equals(event.getView().getTitle())) {
            return;
        }

        event.setCancelled(true);

        if (event.getClickedInventory() == null || !(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        if (plugin.getGuiManager().isRewardSlot(slot)) {
            handleRewardClick(player, slot);
        }
    }

    private void handleRewardClick(Player player, int slot) {
        int baseDay = plugin.getGuiManager().getDayFromSlot(slot);
        int streak = plugin.getUserData().getStreak(player.getUniqueId());
        int actualDay = baseDay + ((streak / 7) * 7);
        
        if (!plugin.getUserData().hasClaimedDay(player.getUniqueId(), actualDay) 
            && plugin.getUserData().canClaimDaily(player.getUniqueId())
            && baseDay == (streak % 7) + 1) {
            
            // Give daily reward (money)
            plugin.getDailyRewardManager().giveDailyReward(player);
            
            // Give daily items if any
            plugin.getDailyItemsManager().giveItems(player, actualDay);
            
            // Update claimed status and streak
            plugin.getUserData().setDayClaimed(player.getUniqueId(), actualDay);
            plugin.getUserData().setStreak(player.getUniqueId(), streak + 1);
            
            // Refresh GUI
            plugin.getGuiManager().openDailyRewardsGui(player);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if ("Daily Rewards".equals(event.getView().getTitle())) {
            event.setCancelled(true);
        }
    }
}