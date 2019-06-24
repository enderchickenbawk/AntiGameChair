package club.mineman.antigamingchair.check.impl.entity;

import club.mineman.antigamingchair.check.checks.*;
import club.mineman.antigamingchair.*;
import club.mineman.antigamingchair.data.*;
import org.bukkit.entity.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import java.util.*;
import club.mineman.antigamingchair.util.dummy.*;
import net.minecraft.server.v1_8_R3.*;

public class PlayerGuard extends PacketCheck
{
    private final Map<UUID, Set<Integer>> playerGuardMap;
    
    public PlayerGuard(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData);
        this.playerGuardMap = new HashMap<UUID, Set<Integer>>();
    }
    
    @Override
    public void handleCheck(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInUseEntity) {
            final PacketPlayInUseEntity useEntity = (PacketPlayInUseEntity)packet;
            if (useEntity.a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK) {
                final Entity entity = useEntity.a(((CraftPlayer)player).getHandle().world);
                if (entity == null) {
                    return;
                }
                if (entity instanceof EntityPlayer) {}
            }
        }
    }
    
    public void handleOutboundPacket(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayOutEntity.PacketPlayOutRelEntityMove || packet instanceof PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook) {
            final Entity targetEntity = ((CraftPlayer)player).getHandle().getWorld().a(((PacketPlayOutEntity)packet).getA());
            if (targetEntity instanceof EntityPlayer) {
                final Set<Integer> playerGuard = this.playerGuardMap.get(targetEntity.getUniqueID());
                if (playerGuard == null) {
                    this.createPlayerGuard(player, targetEntity);
                    return;
                }
                final PacketPlayOutEntity movePacket = (PacketPlayOutEntity)packet;
                for (final int id : playerGuard) {
                    for (final Entity entity : ((CraftPlayer)player).getHandle().getWorld().entityList) {
                        if (entity.getBukkitEntity().getEntityId() == id) {
                            final PacketPlayOutEntity.PacketPlayOutRelEntityMove move = new PacketPlayOutEntity.PacketPlayOutRelEntityMove(id, movePacket.getB(), movePacket.getC(), movePacket.getD(), false);
                            ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)move);
                            break;
                        }
                    }
                }
            }
        }
        else if (packet instanceof PacketPlayOutNamedEntitySpawn) {}
    }
    
    private void createPlayerGuard(final Player player, final Entity entity) {
        final Set<Integer> playerGuard = new HashSet<Integer>();
        for (int i = 0; i < 6; ++i) {
            final DummyPlayer entityPlayer = new DummyPlayer(entity, "nigger_" + i);
            entityPlayer.setInvisible(true);
            final PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn((EntityHuman)entityPlayer);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)spawn);
            playerGuard.add(entityPlayer.getId());
        }
        this.playerGuardMap.put(entity.getUniqueID(), playerGuard);
    }
}
