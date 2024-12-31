package xdaily.voucher.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xdaily.voucher.XDailyVouchers;
import xdaily.voucher.gui.DailyItemsGui;
import xdaily.voucher.utils.DayCalculator;

public class DailyItemCommand {
    private final XDailyVouchers plugin;

    public DailyItemCommand(XDailyVouchers plugin) {
        this.plugin = plugin;
    }

    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players!");
            return true;
        }

        if (!sender.hasPermission("xdv.admin")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }

        if (args.length != 3) {
            sender.sendMessage("§cUsage: /xdv dailyitem <day/week> <number>");
            return true;
        }

        String type = args[1].toLowerCase();
        if (!type.equals("day") && !type.equals("week")) {
            sender.sendMessage("§cType must be either 'day' or 'week'!");
            return true;
        }

        try {
            int number = Integer.parseInt(args[2]);
            if (number < 1) {
                sender.sendMessage("§cNumber must be greater than 0!");
                return true;
            }

            int targetDay = DayCalculator.calculateTargetDay(type, number);
            sender.sendMessage("§aConfiguring rewards for day " + targetDay);

            DailyItemsGui gui = new DailyItemsGui(plugin, type, number);
            gui.openGui((Player) sender);
            return true;
        } catch (NumberFormatException e) {
            sender.sendMessage("§cInvalid number format!");
            return true;
        }
    }
}