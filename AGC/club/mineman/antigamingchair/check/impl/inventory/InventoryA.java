package club.mineman.antigamingchair.check.impl.inventory;

import club.mineman.antigamingchair.check.checks.*;
import club.mineman.antigamingchair.*;
import club.mineman.antigamingchair.data.*;
import org.bukkit.entity.*;
import net.minecraft.server.v1_8_R3.*;
import club.mineman.antigamingchair.event.player.*;

public class InventoryA extends PacketCheck
{
    public InventoryA(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData);
    }
    
    @Override
    public void handleCheck(final Player player, final Packet packet) {
        if (!this.playerData.isInventoryOpen() && packet instanceof PacketPlayInWindowClick && ((PacketPlayInWindowClick)packet).a() == 0) {
            this.alert(PlayerAlertEvent.AlertType.EXPERIMENTAL, player, "failed Inventory Check A (Experimental).");
        }
    }
}
