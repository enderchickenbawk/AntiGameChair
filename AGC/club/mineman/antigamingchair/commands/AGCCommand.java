package club.mineman.antigamingchair.commands;

import club.mineman.antigamingchair.*;
import java.util.*;
import club.mineman.antigamingchair.commands.subcommands.*;
import org.bukkit.command.*;
import club.mineman.core.rank.*;
import club.mineman.core.util.finalutil.*;
import org.bukkit.entity.*;

public class AGCCommand extends Command
{
    private final Map<String, SubCommand> subCommandMap;
    private final AntiGamingChair plugin;
    
    public AGCCommand(final AntiGamingChair plugin) {
        super("agc");
        this.subCommandMap = new HashMap<String, SubCommand>();
        this.plugin = plugin;
        this.setUsage(CC.RED + "Usage: /agc <subcommand> [player]");
        this.setPermissionMessage(StringUtil.NO_PERMISSION);
        this.subCommandMap.put("alerts", new AlertsCommand(plugin));
        this.subCommandMap.put("sniff", (SubCommand)new SniffCommand(plugin));
        this.subCommandMap.put("watch", new WatchCommand(plugin));
        this.subCommandMap.put("info", new InfoCommand(plugin));
        this.subCommandMap.put("logs", new LogsCommand(plugin));
        this.subCommandMap.put("banwave", new BanWaveCommand(plugin));
        this.subCommandMap.put("rangevl", new RangeVlCommand(plugin));
        this.subCommandMap.put("toggle", new ToggleCommand());
        this.subCommandMap.put("filter", new FilterCommand(plugin));
    }
    
    public boolean execute(final CommandSender sender, final String alias, final String[] args) {
        if (!PlayerUtil.testPermission(sender, Rank.ADMIN)) {
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(StringUtil.PLAYER_ONLY);
            return true;
        }
        final Player player = (Player)sender;
        final String subCommandString = (args.length < 1) ? "help" : args[0].toLowerCase();
        final SubCommand subCommand = this.subCommandMap.get(subCommandString);
        if (subCommand == null) {
            player.sendMessage(CC.RED + this.getUsage());
            return true;
        }
        final Player target = (args.length > 1) ? this.plugin.getServer().getPlayer(args[1]) : null;
        subCommand.execute(player, target, args);
        return true;
    }
}
