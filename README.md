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
- Flexible command execution system
- Admin controls for voucher distribution
- Voucher redemption system with limits
- Player-friendly redemption interface

## Commands

### Admin Commands
- `/xdv reload` - Reload the plugin configuration
- `/xdv voucher <name>` - Create a new voucher
- `/xdv give <voucher> <player> [amount]` - Give vouchers to players
- `/xdv dailyitem <day/week> <number>` - Set daily/weekly item rewards
- `/xdv active <vouchername> <maxusers> <maxvouchers>` - Activate voucher for redemption
- `/xdv deactive <vouchername>` - Deactivate voucher redemption

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

1. Download the latest release
2. Place the jar file in your plugins folder
3. Restart your server
4. Configure the plugin in `config.yml`

## Support

If you encounter any issues or have suggestions:
1. Check the [Issues](https://github.com/Akar1881/XDailyVoucher/issues) page
2. Create a new issue if your problem isn't already reported

## License

This project is licensed under the MIT License - see the LICENSE file for details.