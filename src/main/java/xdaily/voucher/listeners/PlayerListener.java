// Add this method to handle voucher redemption in the PlayerListener class

@EventHandler
public void onPlayerInteract(PlayerInteractEvent event) {
    ItemStack item = event.getItem();
    if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
        String itemName = item.getItemMeta().getDisplayName();
        Player player = event.getPlayer();
        
        // Check if the item is a voucher by comparing with active vouchers
        plugin.getVoucherManager().getActiveVouchers().values().stream()
            .filter(voucher -> voucher.getDisplay().equals(itemName))
            .findFirst()
            .ifPresent(voucher -> {
                // Execute voucher command
                String command = voucher.getCommand()
                    .replace("%player_name%", player.getName());
                plugin.getServer().dispatchCommand(
                    plugin.getServer().getConsoleSender(), 
                    command
                );
                
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