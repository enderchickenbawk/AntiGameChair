package club.mineman.antigamingchair.util.api;

import org.bukkit.entity.*;
import club.mineman.antigamingchair.*;
import club.mineman.antigamingchair.client.*;
import club.mineman.antigamingchair.data.*;
import club.mineman.antigamingchair.check.impl.range.*;

public final class AGCAPI
{
    private AGCAPI() {
    }
    
    public static boolean isCheatBreaker(final Player player) {
        final PlayerData playerData = AntiGamingChair.getInstance().getPlayerDataManager().getPlayerData(player);
        return playerData != null && playerData.getClient() == ClientType.CHEAT_BREAKER;
    }
    
    public static int getPing(final Player player) {
        final PlayerData playerData = AntiGamingChair.getInstance().getPlayerDataManager().getPlayerData(player);
        if (playerData != null) {
            return (int)playerData.getPing();
        }
        return 0;
    }
    
    public static void spawnEntityAtCursor(final Player player) {
        final PlayerData playerData = AntiGamingChair.getInstance().getPlayerDataManager().getPlayerData(player);
        if (playerData != null) {
            final RangeC rangeC = playerData.getCheck(RangeC.class);
            if (rangeC == null) {
                return;
            }
            rangeC.spawnEntity(player);
        }
    }
    
    public static void destroyEntityAtCursor(final Player player) {
        final PlayerData playerData = AntiGamingChair.getInstance().getPlayerDataManager().getPlayerData(player);
        if (playerData != null) {
            final RangeC rangeC = playerData.getCheck(RangeC.class);
            if (rangeC == null) {
                return;
            }
            rangeC.destroyEntity(player);
        }
    }
}
