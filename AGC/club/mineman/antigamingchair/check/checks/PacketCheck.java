package club.mineman.antigamingchair.check.checks;

import club.mineman.antigamingchair.check.*;
import net.minecraft.server.v1_8_R3.*;
import club.mineman.antigamingchair.*;
import club.mineman.antigamingchair.data.*;

public abstract class PacketCheck extends AbstractCheck<Packet>
{
    public PacketCheck(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData, Packet.class);
    }
}
