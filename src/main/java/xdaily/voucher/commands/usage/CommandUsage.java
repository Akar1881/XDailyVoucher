package xdaily.voucher.commands.usage;

public class CommandUsage {
    private final String command;
    private final String usage;
    private final String permission;

    public CommandUsage(String command, String usage, String permission) {
        this.command = command;
        this.usage = usage;
        this.permission = permission;
    }

    public String getCommand() {
        return command;
    }

    public String getUsage() {
        return usage;
    }

    public String getPermission() {
        return permission;
    }
}