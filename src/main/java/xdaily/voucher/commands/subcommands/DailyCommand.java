package xdaily.voucher.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xdaily.voucher.XDailyVouchers;

public class DailyCommand {
    private final XDailyVouchers plugin;

    public DailyCommand(XDailyVouchers plugin) {
        this.plugin = plugin;
    }

    public boolean execute(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players!");
            return true;
        }
        
        plugin.getGuiManager().openDailyRewardsGui((Player) sender);
        return true;
    }
}