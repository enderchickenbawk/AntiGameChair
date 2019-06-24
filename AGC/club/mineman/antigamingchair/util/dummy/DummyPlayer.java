package club.mineman.antigamingchair.util.dummy;

import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;

public class DummyPlayer extends EntityPlayer
{
    public DummyPlayer(final Entity entity, final String name) {
        super(MinecraftServer.getServer(), (WorldServer)entity.getWorld(), new GameProfile(UUID.randomUUID(), name), (PlayerInteractManager)new DummyPlayerInteractManager(entity.getWorld()));
    }
}
