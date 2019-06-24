package club.mineman.antigamingchair.listener;

import club.mineman.antigamingchair.*;
import club.mineman.antigamingchair.event.*;
import club.mineman.core.util.finalutil.*;
import club.mineman.antigamingchair.runnable.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;
import java.beans.*;

public class BanWaveListener implements Listener
{
    private final AntiGamingChair plugin;
    
    @EventHandler
    public void onBanWave(final BanWaveEvent e) {
        this.plugin.getServer().broadcastMessage(CC.S + "--------------------------------------------------\n" + CC.R + "\u2718 " + CC.PINK + "AntiGamingChair has been ordered to commence a ban wave by " + CC.D_PURPLE + ((e.getInstigator() == null) ? "CONSOLE" : e.getInstigator()) + CC.PINK + ".\n" + CC.R + CC.S + "--------------------------------------------------\n");
        this.plugin.getBanWaveManager().setBanWaveStarted(true);
        this.plugin.getBanWaveManager().loadCheaters();
        new BanWaveRunnable(this.plugin).runTaskTimerAsynchronously((Plugin)this.plugin, 20L, 20L);
    }
    
    @ConstructorProperties({ "plugin" })
    public BanWaveListener(final AntiGamingChair plugin) {
        this.plugin = plugin;
    }
}
