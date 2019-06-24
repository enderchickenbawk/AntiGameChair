package club.mineman.antigamingchair.check.impl.range;

import club.mineman.antigamingchair.*;
import club.mineman.antigamingchair.data.*;
import org.bukkit.entity.*;
import club.mineman.antigamingchair.check.*;
import java.util.*;

public class KillAuraD extends AbstractCheck<double[]>
{
    private final Deque<Double> distances;
    
    public KillAuraD(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData, double[].class);
        this.distances = new LinkedList<Double>();
    }
    
    @Override
    public void handleCheck(final Player player, final double[] array) {
        this.distances.addLast(array[0] - array[1]);
        if (this.distances.size() == 30) {
            int n = 0;
            double n2 = 0.0;
            for (final double doubleValue : this.distances) {
                if (doubleValue < -1.0) {
                    continue;
                }
                ++n;
                n2 += doubleValue;
            }
            final double d = n2 / n;
            double checkVl = this.playerData.getCheckVl(this);
            if (d > -0.2 && n > 10) {
                this.plugin.getAlertsManager().forceAlert(String.format("failed KillAura Check D (Development). %.3f. %.2f. VL %.2f.", d, -0.2, checkVl), player);
            }
            else {
                checkVl -= 0.3;
            }
            this.playerData.setCheckVl(checkVl, this);
            this.distances.clear();
        }
    }
}
