package club.mineman.antigamingchair.commands;

import club.mineman.antigamingchair.*;
import club.mineman.core.util.finalutil.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import club.mineman.antigamingchair.data.*;

public class PingCommand extends Command
{
    private final AntiGamingChair plugin;
    
    public PingCommand(final AntiGamingChair plugin) {
        super("ping");
        this.plugin = plugin;
        this.usageMessage = CC.RED + "Usage: /ping <player>";
    }
    
    public boolean execute(final CommandSender sender, final String alias, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player)((args.length < 1 || this.plugin.getServer().getPlayer(args[0]) == null) ? sender : this.plugin.getServer().getPlayer(args[0]));
            final PlayerData data = this.plugin.getPlayerDataManager().getPlayerData(player);
            sender.sendMessage(CC.PINK + player.getName() + " has a ping of " + CC.D_PURPLE + data.getPing() + CC.PINK + " ms.");
        }
        return true;
    }
}
