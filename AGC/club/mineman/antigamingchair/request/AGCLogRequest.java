package club.mineman.antigamingchair.request;

import org.json.simple.*;
import java.util.*;
import com.google.common.collect.*;

public class AGCLogRequest extends AGCRequest
{
    private final JSONArray data;
    
    public AGCLogRequest(final JSONArray data) {
        super("insert-logs");
        this.data = data;
    }
    
    @Override
    public Map<String, Object> toMap() {
        return (Map<String, Object>)new ImmutableMap.Builder().put((Object)"data", (Object)this.data.toJSONString()).putAll((Map)super.toMap()).build();
    }
}
