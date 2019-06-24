package club.mineman.antigamingchair.check.impl.badpackets;

import club.mineman.antigamingchair.check.checks.*;
import club.mineman.antigamingchair.*;
import club.mineman.antigamingchair.data.*;
import org.bukkit.entity.*;
import net.minecraft.server.v1_8_R3.*;
import club.mineman.antigamingchair.event.player.*;

public class BadPacketsA extends PacketCheck
{
    private int streak;
    
    public BadPacketsA(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData);
    }
    
    @Override
    public void handleCheck(final Player player, final Packet packet) {
        if (player.getVehicle() == null && packet instanceof PacketPlayInFlying) {
            if (((PacketPlayInFlying)packet).g()) {
                this.streak = 0;
            }
            else if (++this.streak > 20 && this.alert(PlayerAlertEvent.AlertType.RELEASE, player, "failed Bad Packets Check A.") && !this.playerData.isBanning()) {
                this.ban(player, "Bad Packets Check A");
            }
        }
    }
}
