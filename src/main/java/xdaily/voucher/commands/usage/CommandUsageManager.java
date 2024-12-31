package xdaily.voucher.commands.usage;

import java.util.HashMap;
import java.util.Map;

public class CommandUsageManager {
    private final Map<String, CommandUsage> usages;

    public CommandUsageManager() {
        this.usages = new HashMap<>();
        registerDefaultUsages();
    }

    private void registerDefaultUsages() {
        register("reload", "/xdv reload", "xdv.admin");
        register("voucher", "/xdv voucher <name>", "xdv.admin");
        register("give", "/xdv give <voucher> <player> [amount]", "xdv.admin");
        register("daily", "/xdv daily", "xdv.use");
        register("dailyitem", "/xdv dailyitem <day/week> <number>", "xdv.admin");
    }

    public void register(String command, String usage, String permission) {
        usages.put(command.toLowerCase(), new CommandUsage(command, usage, permission));
    }

    public CommandUsage getUsage(String command) {
        return usages.get(command.toLowerCase());
    }

    public String getUsageString(String command) {
        CommandUsage usage = getUsage(command);
        return usage != null ? usage.getUsage() : null;
    }
}