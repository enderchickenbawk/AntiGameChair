package club.mineman.antigamingchair.check.impl.killaura;

import club.mineman.antigamingchair.check.checks.*;
import club.mineman.antigamingchair.*;
import club.mineman.antigamingchair.data.*;
import org.bukkit.entity.*;
import club.mineman.antigamingchair.event.player.*;
import club.mineman.antigamingchair.check.*;
import net.minecraft.server.v1_8_R3.*;

public class KillAuraA extends PacketCheck
{
    private boolean sent;
    
    public KillAuraA(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData);
    }
    
    @Override
    public void handleCheck(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInUseEntity && ((PacketPlayInUseEntity)packet).a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK) {
            if (!this.sent) {
                if (this.alert(PlayerAlertEvent.AlertType.RELEASE, player, "failed Kill Aura Check A.")) {
                    final int violations = this.playerData.getViolations(this, 60000L);
                    if (!this.playerData.isBanning() && violations > 5) {
                        this.ban(player, "Kill Aura Check A");
                    }
                }
            }
            else {
                this.sent = false;
            }
        }
        else if (packet instanceof PacketPlayInArmAnimation) {
            this.sent = true;
        }
        else if (packet instanceof PacketPlayInFlying) {
            this.sent = false;
        }
    }
}
