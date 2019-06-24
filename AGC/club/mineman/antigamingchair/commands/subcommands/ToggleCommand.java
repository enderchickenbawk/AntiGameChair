package club.mineman.antigamingchair.commands.subcommands;

import org.bukkit.entity.*;
import club.mineman.core.rank.*;
import org.bukkit.command.*;
import club.mineman.core.util.finalutil.*;
import java.util.*;

public class ToggleCommand implements SubCommand
{
    public static final Set<String> DISABLED_CHECKS;
    
    @Override
    public void execute(final Player player, final Player target, final String[] args) {
        if (!PlayerUtil.testPermission((CommandSender)player, Rank.DEVELOPER)) {
            return;
        }
        final String check = args[1].toUpperCase();
        if (!ToggleCommand.DISABLED_CHECKS.remove(check)) {
            ToggleCommand.DISABLED_CHECKS.add(check);
            player.sendMessage(CC.L_PURPLE + "Enabled check " + CC.D_PURPLE + check);
        }
        else {
            player.sendMessage(CC.L_PURPLE + "Disabled check " + CC.D_PURPLE + check);
        }
    }
    
    static {
        DISABLED_CHECKS = new HashSet<String>();
    }
}
