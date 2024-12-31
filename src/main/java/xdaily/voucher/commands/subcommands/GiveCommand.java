package xdaily.voucher.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xdaily.voucher.XDailyVouchers;

public class GiveCommand {
    private final XDailyVouchers plugin;

    public GiveCommand(XDailyVouchers plugin) {
        this.plugin = plugin;
    }

    public boolean execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("xdv.admin")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage("§cUsage: /xdv give <voucher> <player> [amount]");
            return true;
        }

        String voucherName = args[1];
        String playerName = args[2];
        int amount = args.length >= 4 ? Integer.parseInt(args[3]) : 1;

        Player target = Bukkit.getPlayer(playerName);
        if (target == null) {
            sender.sendMessage("§cPlayer not found!");
            return true;
        }

        plugin.getVoucherManager().giveVoucher(target, voucherName, amount);
        sender.sendMessage("§aGave " + amount + " " + voucherName + " voucher(s) to " + playerName);
        return true;
    }
}