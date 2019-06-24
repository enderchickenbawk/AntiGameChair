package club.mineman.antigamingchair.event.player;

import org.bukkit.event.*;
import org.bukkit.entity.*;
import java.beans.*;

public class PlayerAlertEvent extends Event implements Cancellable
{
    private static final HandlerList HANDLER_LIST;
    private final AlertType alertType;
    private final Player player;
    private final String alert;
    private boolean cancelled;
    
    public static HandlerList getHandlerList() {
        return PlayerAlertEvent.HANDLER_LIST;
    }
    
    public HandlerList getHandlers() {
        return PlayerAlertEvent.HANDLER_LIST;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    public AlertType getAlertType() {
        return this.alertType;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public String getAlert() {
        return this.alert;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @ConstructorProperties({ "alertType", "player", "alert" })
    public PlayerAlertEvent(final AlertType alertType, final Player player, final String alert) {
        this.alertType = alertType;
        this.player = player;
        this.alert = alert;
    }
    
    static {
        HANDLER_LIST = new HandlerList();
    }
    
    public enum AlertType
    {
        RELEASE, 
        EXPERIMENTAL, 
        DEVELOPMENT;
    }
}
