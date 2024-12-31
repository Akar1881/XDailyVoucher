// Add these cases to the switch statement in the onCommand method

case "active":
    if (args.length >= 2 && sender.hasPermission("xdv.admin")) {
        boolean activated = plugin.getVoucherManager().activateVoucher(args[1]);
        sender.sendMessage(activated ? 
            "§aVoucher " + args[1] + " has been activated!" : 
            "§cVoucher " + args[1] + " doesn't exist or is already active!");
    }
    break;

case "deactive":
    if (args.length >= 2 && sender.hasPermission("xdv.admin")) {
        boolean deactivated = plugin.getVoucherManager().deactivateVoucher(args[1]);
        sender.sendMessage(deactivated ? 
            "§aVoucher " + args[1] + " has been deactivated!" : 
            "§cVoucher " + args[1] + " is not active!");
    }
    break;