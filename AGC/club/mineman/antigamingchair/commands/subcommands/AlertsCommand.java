package club.mineman.antigamingchair.commands.subcommands;

import club.mineman.antigamingchair.*;
import org.bukkit.entity.*;
import club.mineman.core.util.finalutil.*;
import java.beans.*;

public class AlertsCommand implements SubCommand
{
    private final AntiGamingChair plugin;
    
    @Override
    public void execute(final Player player, final Player target, final String[] args) {
        this.plugin.getAlertsManager().toggleAlerts(player);
        player.sendMessage(this.plugin.getAlertsManager().hasAlertsToggled(player) ? (CC.GREEN + "Subscribed to AGC alerts.") : (CC.RED + "Unsubscribed from AGC alerts."));
    }
    
    @ConstructorProperties({ "plugin" })
    public AlertsCommand(final AntiGamingChair plugin) {
        this.plugin = plugin;
    }
}
