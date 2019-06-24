package club.mineman.antigamingchair.commands.subcommands;

import club.mineman.antigamingchair.*;
import org.bukkit.entity.*;
import club.mineman.core.util.finalutil.*;
import java.beans.*;

public class RangeVlCommand implements SubCommand
{
    private final AntiGamingChair plugin;
    
    @Override
    public void execute(final Player player, final Player target, final String[] args) {
        if (args.length == 1) {
            player.sendMessage(CC.RED + "The current range VL is " + this.plugin.getRangeVl());
            return;
        }
        this.plugin.setRangeVl(Double.parseDouble(args[1]));
        player.sendMessage(CC.GREEN + "Successfully set range VL");
    }
    
    @ConstructorProperties({ "plugin" })
    public RangeVlCommand(final AntiGamingChair plugin) {
        this.plugin = plugin;
    }
}
