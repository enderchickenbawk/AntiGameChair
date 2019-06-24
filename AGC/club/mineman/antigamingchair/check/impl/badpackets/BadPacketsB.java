package club.mineman.antigamingchair.check.impl.badpackets;

import club.mineman.antigamingchair.check.checks.*;
import club.mineman.antigamingchair.location.*;
import club.mineman.antigamingchair.*;
import club.mineman.antigamingchair.data.*;
import org.bukkit.entity.*;
import net.minecraft.server.v1_8_R3.*;
import club.mineman.antigamingchair.check.*;
import club.mineman.antigamingchair.event.player.*;

public class BadPacketsB extends PacketCheck
{
    private CustomLocation lastPosition;
    
    public BadPacketsB(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData);
    }
    
    @Override
    public void handleCheck(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInFlying.PacketPlayInPosition) {
            if (this.lastPosition != null) {
                final CustomLocation lastMovePacket = this.playerData.getLastMovePacket();
                if (this.lastPosition.getX() == lastMovePacket.getX() && this.lastPosition.getY() == lastMovePacket.getY() && this.lastPosition.getZ() == lastMovePacket.getZ() && Math.abs(50L - (lastMovePacket.getTimestamp() - this.lastPosition.getTimestamp())) < 4L) {
                    final int violations = this.playerData.getViolations(this, 60000L);
                    if (!this.playerData.isBanWave() && violations > 5) {
                        this.banWave(player, "Bad Packets Check F");
                    }
                    else if (!this.playerData.isBanWave()) {
                        this.alert(PlayerAlertEvent.AlertType.RELEASE, player, "failed Bad Packets Check B.");
                    }
                }
                this.lastPosition = this.playerData.getLastMovePacket();
            }
            else {
                this.lastPosition = this.playerData.getLastMovePacket();
            }
        }
    }
}
