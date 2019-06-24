package club.mineman.antigamingchair.check.impl.autoclicker;

import club.mineman.antigamingchair.check.checks.*;
import club.mineman.antigamingchair.data.*;
import club.mineman.antigamingchair.*;
import org.bukkit.entity.*;
import club.mineman.antigamingchair.check.*;
import club.mineman.antigamingchair.event.player.*;
import net.minecraft.server.v1_8_R3.*;

public class AutoClickerC extends PacketCheck
{
    private PlayerData playerData;
    private boolean failed;
    private boolean sent;
    
    public AutoClickerC(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData);
        this.playerData = playerData;
    }
    
    @Override
    public void handleCheck(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInArmAnimation && System.currentTimeMillis() - this.playerData.getLastDelayedMovePacket() > 110L && System.currentTimeMillis() - this.playerData.getLastMovePacket().getTimestamp() < 110L && !this.playerData.isDigging() && !this.playerData.isPlacing()) {
            if (this.sent) {
                if (!this.failed) {
                    int n = (int)this.playerData.getCheckVl(this);
                    if (++n >= 5) {
                        n = 0;
                        this.alert(PlayerAlertEvent.AlertType.EXPERIMENTAL, player, "failed Auto Clicker Check C (Experimental).");
                    }
                    this.playerData.setCheckVl(n, this);
                    this.failed = true;
                }
            }
            else {
                this.sent = true;
            }
        }
        else if (packet instanceof PacketPlayInFlying) {
            this.failed = false;
            this.sent = false;
        }
    }
}
