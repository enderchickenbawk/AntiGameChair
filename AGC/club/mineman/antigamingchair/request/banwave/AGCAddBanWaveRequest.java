package club.mineman.antigamingchair.request.banwave;

import club.mineman.antigamingchair.request.*;
import java.util.*;
import com.google.common.collect.*;

public class AGCAddBanWaveRequest extends AGCRequest
{
    private final int id;
    private final String reason;
    
    public AGCAddBanWaveRequest(final int id, final String reason) {
        super("add-ban-wave");
        this.id = id;
        this.reason = reason;
    }
    
    @Override
    public Map<String, Object> toMap() {
        return (Map<String, Object>)new ImmutableMap.Builder().put((Object)"id", (Object)this.id).put((Object)"reason", (Object)this.reason).putAll((Map)super.toMap()).build();
    }
}
