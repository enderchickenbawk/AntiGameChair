package club.mineman.antigamingchair.check.impl.aimassist;

import club.mineman.antigamingchair.check.checks.*;
import club.mineman.antigamingchair.*;
import club.mineman.antigamingchair.data.*;
import org.bukkit.entity.*;
import club.mineman.paper.event.*;
import club.mineman.antigamingchair.event.player.*;

public class AimAssistB extends RotationCheck
{
    private float suspiciousYaw;
    
    public AimAssistB(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData);
    }
    
    @Override
    public void handleCheck(final Player player, final PlayerUpdateRotationEvent event) {
        if (System.currentTimeMillis() - this.playerData.getLastAttackPacket() >= 10000L) {
            return;
        }
        final float diff = Math.abs(event.getTo().getYaw() - event.getFrom().getYaw()) % 360.0f;
        if (diff > 1.0f && Math.round(diff * 10.0f) * 0.1f == diff && Math.round(diff) != diff) {
            if (diff == this.suspiciousYaw) {
                this.alert(PlayerAlertEvent.AlertType.EXPERIMENTAL, player, "failed Aim Assist Check B (Experimental). Y " + diff + ".");
            }
            this.suspiciousYaw = Math.round(diff * 10.0f) * 0.1f;
        }
        else {
            this.suspiciousYaw = 0.0f;
        }
    }
}
