package club.mineman.antigamingchair.manager;

import club.mineman.antigamingchair.*;
import club.mineman.core.*;
import club.mineman.antigamingchair.request.banwave.*;
import org.json.simple.*;
import club.mineman.core.api.*;
import club.mineman.core.api.request.*;
import java.util.*;
import java.beans.*;

public class BanWaveManager
{
    private final AntiGamingChair plugin;
    private final Deque<Long> cheaters;
    private final Map<Long, String> cheaterNames;
    private boolean banWaveStarted;
    
    public void loadCheaters() {
        CorePlugin.getInstance().getRequestManager().sendRequest((APIMessage)new AGCGetBanWaveRequest(), (RequestCallback)new AbstractCallback("Error fetching the ban wave list.") {
            public void callback(final JSONObject data) {
                final JSONArray array = (JSONArray)data.get((Object)"data");
                final List<Long> cheaters = new LinkedList<Long>();
                for (final Object object : array) {
                    final JSONObject jsonObject = (JSONObject)object;
                    final long id = (long)jsonObject.get((Object)"id");
                    final String name = (String)jsonObject.get((Object)"name");
                    cheaters.add(id);
                    BanWaveManager.this.cheaterNames.put(id, name);
                }
                final String name2;
                final String otherName;
                cheaters.sort((integer, t1) -> {
                    name2 = (String)BanWaveManager.this.cheaterNames.get(integer);
                    otherName = (String)BanWaveManager.this.cheaterNames.get(t1);
                    return name2.compareToIgnoreCase(otherName);
                });
                BanWaveManager.this.cheaters.addAll(cheaters);
            }
        });
    }
    
    public void clearCheaters() {
        this.cheaters.clear();
        this.cheaterNames.clear();
    }
    
    public long queueCheater() {
        return this.cheaters.poll();
    }
    
    public String getCheaterName(final long id) {
        return this.cheaterNames.get(id);
    }
    
    public AntiGamingChair getPlugin() {
        return this.plugin;
    }
    
    public Deque<Long> getCheaters() {
        return this.cheaters;
    }
    
    public Map<Long, String> getCheaterNames() {
        return this.cheaterNames;
    }
    
    public boolean isBanWaveStarted() {
        return this.banWaveStarted;
    }
    
    @ConstructorProperties({ "plugin" })
    public BanWaveManager(final AntiGamingChair plugin) {
        this.cheaters = new LinkedList<Long>();
        this.cheaterNames = new HashMap<Long, String>();
        this.plugin = plugin;
    }
    
    public void setBanWaveStarted(final boolean banWaveStarted) {
        this.banWaveStarted = banWaveStarted;
    }
}
