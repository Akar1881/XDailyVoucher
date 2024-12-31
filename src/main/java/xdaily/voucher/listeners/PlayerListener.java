package xdaily.voucher.listeners;

import xdaily.voucher.XDailyVouchers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
    private final XDailyVouchers plugin;

    public PlayerListener(XDailyVouchers plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getDailyRewardManager().checkDailyReward(event.getPlayer());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            String itemName = item.getItemMeta().getDisplayName();
            Player player = event.getPlayer();
            
            plugin.getVoucherManager().getActiveVouchers().values().stream()
                .filter(voucher -> voucher.getDisplay().equals(itemName))
                .findFirst()
                .ifPresent(voucher -> {
                    // Execute all voucher commands
                    for (String command : voucher.getCommands()) {
                        String processedCommand = command.replace("%player_name%", player.getName());
                        plugin.getServer().dispatchCommand(
                            plugin.getServer().getConsoleSender(), 
                            processedCommand
                        );
                    }
                    
                    // Remove one voucher from the player's hand
                    ItemStack handItem = player.getInventory().getItemInMainHand();
                    if (handItem.getAmount() > 1) {
                        handItem.setAmount(handItem.getAmount() - 1);
                    } else {
                        player.getInventory().setItemInMainHand(null);
                    }
                    
                    player.sendMessage("§aVoucher redeemed successfully!");
                    event.setCancelled(true);
                });
        }
    }
}