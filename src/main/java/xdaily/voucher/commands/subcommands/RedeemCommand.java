package xdaily.voucher.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xdaily.voucher.XDailyVouchers;

public class RedeemCommand {
    private final XDailyVouchers plugin;

    public RedeemCommand(XDailyVouchers plugin) {
        this.plugin = plugin;
    }

    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players!");
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage("§cUsage: /xdv redeem <vouchername>");
            return true;
        }

        Player player = (Player) sender;
        String voucherName = args[1];

        if (!plugin.getVoucherManager().isVoucherActive(voucherName)) {
            sender.sendMessage("§cVoucher doesn't exist!");
            return true;
        }

        if (!plugin.getRedeemData().isVoucherActive(voucherName)) {
            sender.sendMessage("§cThis voucher is not currently active for redemption!");
            return true;
        }

        if (!plugin.getRedeemData().canRedeem(voucherName, player.getUniqueId())) {
            sender.sendMessage("§cYou have already redeemed this voucher or the maximum number of users has been reached!");
            return true;
        }

        int maxVouchers = plugin.getRedeemData().getMaxVouchers(voucherName);
        plugin.getVoucherManager().giveVoucher(player, voucherName, maxVouchers);
        plugin.getRedeemData().addRedeemedUser(voucherName, player.getUniqueId());
        
        sender.sendMessage("§aSuccessfully redeemed " + maxVouchers + " " + voucherName + " voucher(s)!");
        return true;
    }
}