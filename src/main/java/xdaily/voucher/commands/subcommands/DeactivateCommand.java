package xdaily.voucher.commands.subcommands;

import org.bukkit.command.CommandSender;
import xdaily.voucher.XDailyVouchers;

public class DeactivateCommand {
    private final XDailyVouchers plugin;

    public DeactivateCommand(XDailyVouchers plugin) {
        this.plugin = plugin;
    }

    public boolean execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("xdv.admin")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage("§cUsage: /xdv deactive <vouchername>");
            return true;
        }

        String voucherName = args[1];

        if (!plugin.getVoucherManager().isVoucherActive(voucherName)) {
            sender.sendMessage("§cVoucher doesn't exist!");
            return true;
        }

        boolean success = plugin.getRedeemData().deactivateVoucher(voucherName);
        if (success) {
            sender.sendMessage("§aVoucher deactivated successfully!");
        } else {
            sender.sendMessage("§cFailed to deactivate voucher!");
        }
        return true;
    }
}