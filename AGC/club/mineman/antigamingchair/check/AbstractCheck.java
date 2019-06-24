package club.mineman.antigamingchair.check;

import club.mineman.antigamingchair.*;
import club.mineman.antigamingchair.data.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import club.mineman.antigamingchair.event.player.*;
import java.beans.*;

public abstract class AbstractCheck<T> implements ICheck<T>
{
    protected final AntiGamingChair plugin;
    protected final PlayerData playerData;
    private final Class<T> clazz;
    
    @Override
    public Class<? extends T> getType() {
        return (Class<? extends T>)this.clazz;
    }
    
    protected boolean alert(final PlayerAlertEvent.AlertType alertType, final Player player, final String message) {
        final PlayerAlertEvent event = new PlayerAlertEvent(alertType, player, message);
        this.plugin.getServer().getPluginManager().callEvent((Event)event);
        if (!event.isCancelled()) {
            this.playerData.addViolation(this);
            return true;
        }
        return false;
    }
    
    protected boolean banWave(final Player player, final String message) {
        this.playerData.setBanWave(true);
        final PlayerBanWaveEvent event = new PlayerBanWaveEvent(player, message);
        this.plugin.getServer().getPluginManager().callEvent((Event)event);
        return !event.isCancelled();
    }
    
    protected boolean ban(final Player player, final String message) {
        this.playerData.setBanning(true);
        final PlayerBanEvent event = new PlayerBanEvent(player, message);
        this.plugin.getServer().getPluginManager().callEvent((Event)event);
        return !event.isCancelled();
    }
    
    public AntiGamingChair getPlugin() {
        return this.plugin;
    }
    
    public PlayerData getPlayerData() {
        return this.playerData;
    }
    
    public Class<T> getClazz() {
        return this.clazz;
    }
    
    @ConstructorProperties({ "plugin", "playerData", "clazz" })
    public AbstractCheck(final AntiGamingChair plugin, final PlayerData playerData, final Class<T> clazz) {
        this.plugin = plugin;
        this.playerData = playerData;
        this.clazz = clazz;
    }
}
