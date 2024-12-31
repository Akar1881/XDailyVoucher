package xdaily.voucher.commands.subcommands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import xdaily.voucher.XDailyVouchers;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class VoucherCommand {
    private final XDailyVouchers plugin;

    public VoucherCommand(XDailyVouchers plugin) {
        this.plugin = plugin;
    }

    public boolean execute(CommandSender sender, String name) {
        if (!sender.hasPermission("xdv.admin")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }

        List<String> commands = new ArrayList<>();
        commands.add("eco give %player_name% 500");
        commands.add("give %player_name% diamond 1");

        List<String> rewards = new ArrayList<>();
        rewards.add("&6500 coins");
        rewards.add("&b1 Diamond");

        boolean success = plugin.getVoucherManager().createVoucher(
            name,
            "&4Test &cVoucher",
            Material.GOLD_BLOCK,
            commands,
            rewards
        );
        
        sender.sendMessage(success ? "§aVoucher created!" : "§cVoucher already exists!");
        return true;
    }
}