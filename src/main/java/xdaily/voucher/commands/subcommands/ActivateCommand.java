package xdaily.voucher.commands.subcommands;

import org.bukkit.command.CommandSender;
import xdaily.voucher.XDailyVouchers;

public class ActivateCommand {
    private final XDailyVouchers plugin;

    public ActivateCommand(XDailyVouchers plugin) {
        this.plugin = plugin;
    }

    public boolean execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("xdv.admin")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }

        if (args.length != 4) {
            sender.sendMessage("§cUsage: /xdv active <vouchername> <maxusers> <maxvouchers>");
            return true;
        }

        String voucherName = args[1];
        
        try {
            int maxUsers = Integer.parseInt(args[2]);
            int maxVouchers = Integer.parseInt(args[3]);

            if (maxUsers <= 0 || maxVouchers <= 0) {
                sender.sendMessage("§cNumbers must be greater than 0!");
                return true;
            }

            if (!plugin.getVoucherManager().isVoucherActive(voucherName)) {
                sender.sendMessage("§cVoucher doesn't exist!");
                return true;
            }

            boolean success = plugin.getRedeemData().activateVoucher(voucherName, maxUsers, maxVouchers);
            if (success) {
                sender.sendMessage("§aVoucher activated successfully!");
            } else {
                sender.sendMessage("§cFailed to activate voucher!");
            }
            return true;

        } catch (NumberFormatException e) {
            sender.sendMessage("§cInvalid number format!");
            return true;
        }
    }
}