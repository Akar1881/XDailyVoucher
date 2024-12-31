package xdaily.voucher.commands.subcommands;

import org.bukkit.command.CommandSender;
import xdaily.voucher.XDailyVouchers;
import xdaily.voucher.models.Voucher;
import java.util.Map;

public class VoucherListCommand {
    private final XDailyVouchers plugin;

    public VoucherListCommand(XDailyVouchers plugin) {
        this.plugin = plugin;
    }

    public boolean execute(CommandSender sender) {
        if (!sender.hasPermission("xdv.admin")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }

        Map<String, Voucher> vouchers = plugin.getVoucherManager().getActiveVouchers();
        
        if (vouchers.isEmpty()) {
            sender.sendMessage("§cNo vouchers found!");
            return true;
        }

        sender.sendMessage("§6Available Vouchers:");
        for (Map.Entry<String, Voucher> entry : vouchers.entrySet()) {
            Voucher voucher = entry.getValue();
            sender.sendMessage("§e- " + entry.getKey() + " §7(" + voucher.getDisplay() + "§7)");
        }

        return true;
    }
}