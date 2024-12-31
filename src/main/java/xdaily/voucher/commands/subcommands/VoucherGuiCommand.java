package xdaily.voucher.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xdaily.voucher.XDailyVouchers;
import xdaily.voucher.gui.VoucherItemsGui;

public class VoucherGuiCommand {
    private final XDailyVouchers plugin;

    public VoucherGuiCommand(XDailyVouchers plugin) {
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

        if (args.length != 2) {
            sender.sendMessage("§cUsage: /xdv vgui <vouchername>");
            return true;
        }

        String voucherName = args[1];
        if (!plugin.getVoucherManager().isVoucherActive(voucherName)) {
            sender.sendMessage("§cVoucher doesn't exist!");
            return true;
        }

        VoucherItemsGui gui = new VoucherItemsGui(plugin, voucherName);
        gui.openGui((Player) sender);
        return true;
    }
}