package club.mineman.antigamingchair.request;

import club.mineman.core.api.*;
import java.util.*;
import com.google.common.collect.*;
import java.beans.*;

public abstract class AGCRequest implements APIMessage
{
    private final String subChannel;
    
    public String getChannel() {
        return "AGC";
    }
    
    public Map<String, Object> toMap() {
        return (Map<String, Object>)ImmutableMap.of((Object)"sub-channel", (Object)this.subChannel, (Object)"agc-key", (Object)"#EH3geeftrlfroT4J9yvgD)k#*h#ndxJ!8f");
    }
    
    @ConstructorProperties({ "subChannel" })
    public AGCRequest(final String subChannel) {
        this.subChannel = subChannel;
    }
}
