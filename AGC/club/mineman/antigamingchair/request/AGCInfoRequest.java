package club.mineman.antigamingchair.request;

import java.sql.*;
import java.util.*;
import com.google.common.collect.*;

public class AGCInfoRequest extends AGCRequest
{
    private final String name;
    private final Timestamp time;
    
    public AGCInfoRequest(final String name, final Timestamp time) {
        super("get-info");
        this.name = name;
        this.time = time;
    }
    
    @Override
    public Map<String, Object> toMap() {
        return (Map<String, Object>)new ImmutableMap.Builder().put((Object)"time", (Object)this.time).put((Object)"name", (Object)this.name).putAll((Map)super.toMap()).build();
    }
}
