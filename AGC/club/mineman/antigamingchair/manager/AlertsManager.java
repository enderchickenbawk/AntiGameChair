package club.mineman.antigamingchair.manager;

import club.mineman.antigamingchair.*;
import org.bukkit.entity.*;
import club.mineman.antigamingchair.data.*;
import java.util.*;
import java.util.function.*;
import net.minecraft.server.v1_8_R3.*;
import java.text.*;
import org.bukkit.*;
import club.mineman.core.util.finalutil.*;
import java.beans.*;

public class AlertsManager
{
    private final Set<UUID> alertsToggled;
    private final AntiGamingChair plugin;
    
    public boolean hasAlertsToggled(final Player player) {
        return this.alertsToggled.contains(player.getUniqueId());
    }
    
    public void toggleAlerts(final Player player) {
        if (!this.alertsToggled.remove(player.getUniqueId())) {
            this.alertsToggled.add(player.getUniqueId());
        }
    }
    
    public void forceAlert(final String message) {
        this.forceAlertWithData(message, null);
    }
    
    private void forceAlertWithData(final String message, final PlayerData playerData) {
        final Set<UUID> playerUUIDs = new HashSet<UUID>(this.plugin.getAlertsManager().getAlertsToggled());
        if (playerData != null) {
            playerUUIDs.addAll(playerData.getPlayersWatching());
        }
        playerUUIDs.stream().map((Function<? super Object, ?>)this.plugin.getServer()::getPlayer).filter(Objects::nonNull).forEach(p -> p.sendMessage(message));
    }
    
    public void forceAlert(final String message, final Player player) {
        final double tps = MinecraftServer.getServer().tps1.getAverage();
        String fixedTPS = new DecimalFormat(".##").format(tps);
        if (tps > 20.0) {
            fixedTPS = "20.0";
        }
        final PlayerData playerData = this.plugin.getPlayerDataManager().getPlayerData(player);
        final String alert = message + ChatColor.LIGHT_PURPLE + " Ping " + playerData.getPing() + " ms. TPS " + fixedTPS + ".";
        this.forceAlertWithData(ChatColor.LIGHT_PURPLE + player.getName() + CC.D_PURPLE + " " + alert, playerData);
    }
    
    @ConstructorProperties({ "plugin" })
    public AlertsManager(final AntiGamingChair plugin) {
        this.alertsToggled = new HashSet<UUID>();
        this.plugin = plugin;
    }
    
    public Set<UUID> getAlertsToggled() {
        return this.alertsToggled;
    }
}
