package club.mineman.antigamingchair.check.impl.autoclicker;

import club.mineman.antigamingchair.check.checks.*;
import club.mineman.antigamingchair.*;
import club.mineman.antigamingchair.data.*;
import org.bukkit.entity.*;
import club.mineman.antigamingchair.check.*;
import club.mineman.antigamingchair.event.player.*;
import net.minecraft.server.v1_8_R3.*;

public class AutoClickerE extends PacketCheck
{
    private boolean failed;
    private boolean sent;
    
    public AutoClickerE(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData);
    }
    
    @Override
    public void handleCheck(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInArmAnimation && System.currentTimeMillis() - ((AutoClickerE)this).playerData.getLastDelayedMovePacket() > 110L && System.currentTimeMillis() - ((AutoClickerE)this).playerData.getLastMovePacket().getTimestamp() < 110L && !((AutoClickerE)this).playerData.isDigging() && !((AutoClickerE)this).playerData.isPlacing()) {
            if (((AutoClickerE)this).sent) {
                if (!((AutoClickerE)this).failed) {
                    int vl = (int)((AutoClickerE)this).playerData.getCheckVl(this);
                    if (++vl >= 5) {
                        vl = 0;
                        this.alert(PlayerAlertEvent.AlertType.EXPERIMENTAL, player, "failed Auto Clicker Check D (Experimental).");
                    }
                    this.playerData.setCheckVl(vl, this);
                    ((AutoClickerE)this).failed = true;
                }
            }
            else {
                ((AutoClickerE)this).sent = true;
            }
        }
        else if (packet instanceof PacketPlayInFlying) {
            final boolean b = false;
            ((AutoClickerE)this).failed = b;
            ((AutoClickerE)this).sent = b;
        }
    }
}
