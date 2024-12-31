// Add these methods to the existing VoucherManager class

public boolean activateVoucher(String name) {
    File file = new File(vouchersFolder, name + ".yml");
    if (!file.exists()) return false;
    
    if (!activeVouchers.containsKey(name)) {
        loadVoucher(file);
        return true;
    }
    return false;
}

public boolean deactivateVoucher(String name) {
    if (activeVouchers.containsKey(name)) {
        activeVouchers.remove(name);
        return true;
    }
    return false;
}

public boolean isVoucherActive(String name) {
    return activeVouchers.containsKey(name);
}