package club.mineman.antigamingchair.manager;

import java.util.*;
import club.mineman.antigamingchair.log.*;
import java.util.concurrent.*;

public class LogManager
{
    private final Queue<Log> logQueue;
    
    public LogManager() {
        this.logQueue = new ConcurrentLinkedQueue<Log>();
    }
    
    public void addToLogQueue(final Log log) {
        this.logQueue.add(log);
    }
    
    public void clearLogQueue() {
        this.logQueue.clear();
    }
    
    public Queue<Log> getLogQueue() {
        return this.logQueue;
    }
}
