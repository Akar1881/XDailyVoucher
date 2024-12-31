# XDailyVouchers

A comprehensive daily rewards and voucher system plugin for Minecraft servers running Paper 1.17+.

## Features

### Daily Rewards System
- Progressive daily rewards with streak multipliers
- Customizable rewards for each day
- Weekly special rewards
- Visual GUI interface
- Streak tracking and persistence

### Voucher System
- Create custom vouchers with multiple rewards
- Support for both command and item rewards
- Visual GUI for editing voucher contents
- Flexible command execution system
- Admin controls for voucher distribution
- Voucher redemption system with limits
- Player-friendly redemption interface

## Commands

### Admin Commands
- `/xdv reload` - Reload the plugin configuration
- `/xdv voucher <name>` - Create a new voucher
- `/xdv vgui <vouchername>` - Edit voucher items through GUI
- `/xdv give <voucher> <player> [amount]` - Give vouchers to players
- `/xdv dailyitem <day/week> <number>` - Set daily/weekly item rewards
- `/xdv active <vouchername> <maxusers> <maxvouchers>` - Activate voucher for redemption
- `/xdv deactive <vouchername>` - Deactivate voucher redemption
- `/xdv vlist` - List all vouchers

### Player Commands
- `/xdv daily` - Open daily rewards GUI
- `/xdv redeem <vouchername>` - Redeem an active voucher

## Permissions

- `xdv.admin` - Access to admin commands
- `xdv.use` - Access to player commands (default: true)

## Configuration

The plugin is highly configurable through `config.yml`:
```yaml
# Daily reward base amount
dailyreward: 100

# Streak reward multiplier (0.1 = 10% increase per week)
strikreward: 0.1

# Messages can be customized in the config
```

## Dependencies

- Paper 1.17+
- Vault

## Installation

1. Download the latest release from [Modrinth](https://modrinth.com/project/xdailyvoucher/)
2. Place the jar file in your plugins folder
3. Restart your server
4. Configure the plugin in `config.yml`
5. You can download latest release on github releases.

## Support

If you encounter any issues or have suggestions:
1. Check the [Issues](https://github.com/Akar1881/XDailyVoucher/issues) page
2. Create a new issue if your problem isn't already reported

## License

This project is licensed under the GNU License - see the LICENSE file for details.