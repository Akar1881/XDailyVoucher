package xdaily.voucher.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import xdaily.voucher.XDailyVouchers;
import xdaily.voucher.commands.subcommands.*;
import xdaily.voucher.commands.usage.CommandUsageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandManager implements CommandExecutor, TabCompleter {
    private final XDailyVouchers plugin;
    private final DailyCommand dailyCommand;
    private final VoucherCommand voucherCommand;
    private final GiveCommand giveCommand;
    private final DailyItemCommand dailyItemCommand;
    private final ActivateCommand activateCommand;
    private final DeactivateCommand deactivateCommand;
    private final RedeemCommand redeemCommand;
    private final CommandUsageManager usageManager;

    public CommandManager(XDailyVouchers plugin) {
        this.plugin = plugin;
        this.dailyCommand = new DailyCommand(plugin);
        this.voucherCommand = new VoucherCommand(plugin);
        this.giveCommand = new GiveCommand(plugin);
        this.dailyItemCommand = new DailyItemCommand(plugin);
        this.activateCommand = new ActivateCommand(plugin);
        this.deactivateCommand = new DeactivateCommand(plugin);
        this.redeemCommand = new RedeemCommand(plugin);
        this.usageManager = new CommandUsageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        String subCommand = args[0].toLowerCase();
        
        switch (subCommand) {
            case "reload":
                if (!sender.hasPermission("xdv.admin")) {
                    sender.sendMessage("§cYou don't have permission to use this command!");
                    return true;
                }
                plugin.reloadPlugin();
                sender.sendMessage(plugin.getConfig().getString("messages.reload", "§aPlugin reloaded!"));
                break;

            case "voucher":
                if (args.length < 2) {
                    sender.sendMessage("§c" + usageManager.getUsageString("voucher"));
                    return true;
                }
                return voucherCommand.execute(sender, args[1]);

            case "give":
                if (args.length < 3) {
                    sender.sendMessage("§c" + usageManager.getUsageString("give"));
                    return true;
                }
                return giveCommand.execute(sender, args);

            case "dailyitem":
                if (args.length < 3) {
                    sender.sendMessage("§c" + usageManager.getUsageString("dailyitem"));
                    return true;
                }
                return dailyItemCommand.execute(sender, args);

            case "daily":
                return dailyCommand.execute(sender);

            case "active":
                return activateCommand.execute(sender, args);

            case "deactive":
                return deactivateCommand.execute(sender, args);

            case "redeem":
                return redeemCommand.execute(sender, args);

            default:
                sender.sendMessage("§cUnknown command. Type /xdv for help.");
                break;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            List<String> commands = new ArrayList<>();
            if (sender.hasPermission("xdv.admin")) {
                commands.add("reload");
                commands.add("voucher");
                commands.add("give");
                commands.add("dailyitem");
                commands.add("active");
                commands.add("deactive");
            }
            commands.add("daily");
            commands.add("redeem");
            
            return commands.stream()
                .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                .collect(Collectors.toList());
        } else if (args.length == 2 && (args[0].equalsIgnoreCase("active") || 
                                      args[0].equalsIgnoreCase("deactive") || 
                                      args[0].equalsIgnoreCase("redeem"))) {
            return plugin.getVoucherManager().getActiveVouchers().keySet().stream()
                .filter(s -> s.toLowerCase().startsWith(args[1].toLowerCase()))
                .collect(Collectors.toList());
        }
        
        return completions;
    }

    private void sendHelp(CommandSender sender) {
        for (String line : plugin.getConfig().getStringList("messages.help")) {
            sender.sendMessage(line.replace("&", "§"));
        }
    }
}