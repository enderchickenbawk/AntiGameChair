package club.mineman.antigamingchair.commands.subcommands;

import club.mineman.antigamingchair.*;
import org.bukkit.entity.*;
import java.sql.*;
import club.mineman.core.*;
import club.mineman.antigamingchair.request.*;
import org.json.simple.*;
import club.mineman.core.util.finalutil.*;
import club.mineman.core.api.*;
import club.mineman.core.api.request.*;
import java.util.*;
import java.util.regex.*;
import java.beans.*;

public class LogsCommand implements SubCommand
{
    private static final Pattern LOG_PATTERN;
    private final AntiGamingChair plugin;
    
    @Override
    public void execute(final Player player, final Player target, final String[] args) {
        if (args.length == 1) {
            return;
        }
        final boolean detailed = args.length > 2 && args[2].equalsIgnoreCase("-d");
        final int index = detailed ? 3 : 2;
        final String timeString = (args.length > index) ? StringUtil.buildMessage(args, index) : null;
        final Timestamp time = new Timestamp(0L);
        if (timeString != null) {
            try {
                time.setTime(System.currentTimeMillis() - TimeUtil.toMillis(timeString));
            }
            catch (NumberFormatException e) {
                player.sendMessage(CC.RED + "Invalid time specified.");
                return;
            }
        }
        CorePlugin.getInstance().getRequestManager().sendRequest((APIMessage)new AGCInfoRequest(args[1], time), (RequestCallback)new AbstractCallback("Error fetching AGC logs for " + args[1] + ".") {
            public void callback(final JSONObject data) {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     3: dup            
                //     4: invokespecial   java/util/LinkedList.<init>:()V
                //     7: astore_2        /* logs */
                //     8: aload_0         /* this */
                //     9: getfield        club/mineman/antigamingchair/commands/subcommands/LogsCommand$1.val$target:Lorg/bukkit/entity/Player;
                //    12: ifnull          100
                //    15: invokestatic    club/mineman/core/CorePlugin.getInstance:()Lclub/mineman/core/CorePlugin;
                //    18: invokevirtual   club/mineman/core/CorePlugin.getPlayerManager:()Lclub/mineman/core/manager/MinemanManager;
                //    21: aload_0         /* this */
                //    22: getfield        club/mineman/antigamingchair/commands/subcommands/LogsCommand$1.val$target:Lorg/bukkit/entity/Player;
                //    25: invokeinterface org/bukkit/entity/Player.getUniqueId:()Ljava/util/UUID;
                //    30: invokevirtual   club/mineman/core/manager/MinemanManager.getPlayer:(Ljava/util/UUID;)Lclub/mineman/core/mineman/Mineman;
                //    33: astore_3        /* mineman */
                //    34: aload_0         /* this */
                //    35: getfield        club/mineman/antigamingchair/commands/subcommands/LogsCommand$1.this$0:Lclub/mineman/antigamingchair/commands/subcommands/LogsCommand;
                //    38: invokestatic    club/mineman/antigamingchair/commands/subcommands/LogsCommand.access$000:(Lclub/mineman/antigamingchair/commands/subcommands/LogsCommand;)Lclub/mineman/antigamingchair/AntiGamingChair;
                //    41: invokevirtual   club/mineman/antigamingchair/AntiGamingChair.getLogManager:()Lclub/mineman/antigamingchair/manager/LogManager;
                //    44: invokevirtual   club/mineman/antigamingchair/manager/LogManager.getLogQueue:()Ljava/util/Queue;
                //    47: invokeinterface java/util/Queue.iterator:()Ljava/util/Iterator;
                //    52: astore          4
                //    54: aload           4
                //    56: invokeinterface java/util/Iterator.hasNext:()Z
                //    61: ifeq            100
                //    64: aload           4
                //    66: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
                //    71: checkcast       Lclub/mineman/antigamingchair/log/Log;
                //    74: astore          log
                //    76: aload           log
                //    78: invokevirtual   club/mineman/antigamingchair/log/Log.getMinemanId:()I
                //    81: aload_3         /* mineman */
                //    82: invokevirtual   club/mineman/core/mineman/Mineman.getId:()I
                //    85: if_icmpne       97
                //    88: aload_2         /* logs */
                //    89: aload           log
                //    91: invokeinterface java/util/Queue.add:(Ljava/lang/Object;)Z
                //    96: pop            
                //    97: goto            54
                //   100: aload_1         /* data */
                //   101: ldc             "response"
                //   103: invokevirtual   org/json/simple/JSONObject.get:(Ljava/lang/Object;)Ljava/lang/Object;
                //   106: checkcast       Ljava/lang/String;
                //   109: astore_3        /* response */
                //   110: aload_3         /* response */
                //   111: astore          4
                //   113: iconst_m1      
                //   114: istore          5
                //   116: aload           4
                //   118: invokevirtual   java/lang/String.hashCode:()I
                //   121: lookupswitch {
                //          -1867169789: 212
                //          -1685569673: 180
                //          2063878715: 196
                //          2065248534: 164
                //          default: 225
                //        }
                //   164: aload           4
                //   166: ldc             "player-never-joined"
                //   168: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
                //   171: ifeq            225
                //   174: iconst_0       
                //   175: istore          5
                //   177: goto            225
                //   180: aload           4
                //   182: ldc             "invalid-player"
                //   184: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
                //   187: ifeq            225
                //   190: iconst_1       
                //   191: istore          5
                //   193: goto            225
                //   196: aload           4
                //   198: ldc             "no-logs"
                //   200: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
                //   203: ifeq            225
                //   206: iconst_2       
                //   207: istore          5
                //   209: goto            225
                //   212: aload           4
                //   214: ldc             "success"
                //   216: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
                //   219: ifeq            225
                //   222: iconst_3       
                //   223: istore          5
                //   225: iload           5
                //   227: tableswitch {
                //                0: 256
                //                1: 256
                //                2: 287
                //                3: 355
                //          default: 959
                //        }
                //   256: aload_0         /* this */
                //   257: getfield        club/mineman/antigamingchair/commands/subcommands/LogsCommand$1.val$player:Lorg/bukkit/entity/Player;
                //   260: getstatic       club/mineman/core/util/finalutil/StringUtil.PLAYER_NOT_FOUND:Ljava/lang/String;
                //   263: iconst_1       
                //   264: anewarray       Ljava/lang/Object;
                //   267: dup            
                //   268: iconst_0       
                //   269: aload_0         /* this */
                //   270: getfield        club/mineman/antigamingchair/commands/subcommands/LogsCommand$1.val$args:[Ljava/lang/String;
                //   273: iconst_1       
                //   274: aaload         
                //   275: aastore        
                //   276: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
                //   279: invokeinterface org/bukkit/entity/Player.sendMessage:(Ljava/lang/String;)V
                //   284: goto            959
                //   287: aload_2         /* logs */
                //   288: invokeinterface java/util/Queue.size:()I
                //   293: ifne            355
                //   296: aload_0         /* this */
                //   297: getfield        club/mineman/antigamingchair/commands/subcommands/LogsCommand$1.val$player:Lorg/bukkit/entity/Player;
                //   300: new             Ljava/lang/StringBuilder;
                //   303: dup            
                //   304: invokespecial   java/lang/StringBuilder.<init>:()V
                //   307: getstatic       club/mineman/core/util/finalutil/CC.L_PURPLE:Ljava/lang/String;
                //   310: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   313: ldc             "Player "
                //   315: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   318: getstatic       club/mineman/core/util/finalutil/CC.D_PURPLE:Ljava/lang/String;
                //   321: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   324: aload_0         /* this */
                //   325: getfield        club/mineman/antigamingchair/commands/subcommands/LogsCommand$1.val$args:[Ljava/lang/String;
                //   328: iconst_1       
                //   329: aaload         
                //   330: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   333: getstatic       club/mineman/core/util/finalutil/CC.L_PURPLE:Ljava/lang/String;
                //   336: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   339: ldc             " has no logs."
                //   341: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   344: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
                //   347: invokeinterface org/bukkit/entity/Player.sendMessage:(Ljava/lang/String;)V
                //   352: goto            959
                //   355: new             Ljava/lang/StringBuilder;
                //   358: dup            
                //   359: invokespecial   java/lang/StringBuilder.<init>:()V
                //   362: astore          sb
                //   364: aload_0         /* this */
                //   365: getfield        club/mineman/antigamingchair/commands/subcommands/LogsCommand$1.val$detailed:Z
                //   368: ifeq            637
                //   371: aload_1         /* data */
                //   372: ldc             "data"
                //   374: invokevirtual   org/json/simple/JSONObject.get:(Ljava/lang/Object;)Ljava/lang/Object;
                //   377: checkcast       Lorg/json/simple/JSONArray;
                //   380: astore          array
                //   382: aload           array
                //   384: invokevirtual   org/json/simple/JSONArray.iterator:()Ljava/util/Iterator;
                //   387: astore          8
                //   389: aload           8
                //   391: invokeinterface java/util/Iterator.hasNext:()Z
                //   396: ifeq            504
                //   399: aload           8
                //   401: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
                //   406: astore          object
                //   408: aload           object
                //   410: checkcast       Lorg/json/simple/JSONObject;
                //   413: astore          jsonObject
                //   415: aload           jsonObject
                //   417: ldc             "timestamp"
                //   419: invokevirtual   org/json/simple/JSONObject.get:(Ljava/lang/Object;)Ljava/lang/Object;
                //   422: checkcast       Ljava/lang/String;
                //   425: ldc             "T"
                //   427: ldc             " "
                //   429: invokevirtual   java/lang/String.replace:(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
                //   432: ldc             "Z"
                //   434: ldc             ""
                //   436: invokevirtual   java/lang/String.replace:(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
                //   439: invokestatic    java/sql/Timestamp.valueOf:(Ljava/lang/String;)Ljava/sql/Timestamp;
                //   442: astore          timestamp
                //   444: aload           jsonObject
                //   446: ldc             "log"
                //   448: invokevirtual   org/json/simple/JSONObject.get:(Ljava/lang/Object;)Ljava/lang/Object;
                //   451: checkcast       Ljava/lang/String;
                //   454: astore          log
                //   456: aload           sb
                //   458: ldc             "[%s] %s %s"
                //   460: iconst_3       
                //   461: anewarray       Ljava/lang/Object;
                //   464: dup            
                //   465: iconst_0       
                //   466: aload           timestamp
                //   468: invokevirtual   java/sql/Timestamp.toString:()Ljava/lang/String;
                //   471: aastore        
                //   472: dup            
                //   473: iconst_1       
                //   474: aload_0         /* this */
                //   475: getfield        club/mineman/antigamingchair/commands/subcommands/LogsCommand$1.val$args:[Ljava/lang/String;
                //   478: iconst_1       
                //   479: aaload         
                //   480: aastore        
                //   481: dup            
                //   482: iconst_2       
                //   483: aload           log
                //   485: aastore        
                //   486: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
                //   489: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   492: pop            
                //   493: aload           sb
                //   495: ldc             "\n"
                //   497: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   500: pop            
                //   501: goto            389
                //   504: aload_2         /* logs */
                //   505: invokeinterface java/util/Queue.iterator:()Ljava/util/Iterator;
                //   510: astore          8
                //   512: aload           8
                //   514: invokeinterface java/util/Iterator.hasNext:()Z
                //   519: ifeq            595
                //   522: aload           8
                //   524: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
                //   529: checkcast       Lclub/mineman/antigamingchair/log/Log;
                //   532: astore          log
                //   534: aload           sb
                //   536: ldc             "[%s] %s %s"
                //   538: iconst_3       
                //   539: anewarray       Ljava/lang/Object;
                //   542: dup            
                //   543: iconst_0       
                //   544: new             Ljava/sql/Timestamp;
                //   547: dup            
                //   548: aload           log
                //   550: invokevirtual   club/mineman/antigamingchair/log/Log.getTimestamp:()J
                //   553: invokespecial   java/sql/Timestamp.<init>:(J)V
                //   556: invokevirtual   java/sql/Timestamp.toString:()Ljava/lang/String;
                //   559: aastore        
                //   560: dup            
                //   561: iconst_1       
                //   562: aload_0         /* this */
                //   563: getfield        club/mineman/antigamingchair/commands/subcommands/LogsCommand$1.val$args:[Ljava/lang/String;
                //   566: iconst_1       
                //   567: aaload         
                //   568: aastore        
                //   569: dup            
                //   570: iconst_2       
                //   571: aload           log
                //   573: invokevirtual   club/mineman/antigamingchair/log/Log.getLog:()Ljava/lang/String;
                //   576: aastore        
                //   577: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
                //   580: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   583: pop            
                //   584: aload           sb
                //   586: ldc             "\n"
                //   588: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   591: pop            
                //   592: goto            512
                //   595: aload_0         /* this */
                //   596: getfield        club/mineman/antigamingchair/commands/subcommands/LogsCommand$1.this$0:Lclub/mineman/antigamingchair/commands/subcommands/LogsCommand;
                //   599: invokestatic    club/mineman/antigamingchair/commands/subcommands/LogsCommand.access$000:(Lclub/mineman/antigamingchair/commands/subcommands/LogsCommand;)Lclub/mineman/antigamingchair/AntiGamingChair;
                //   602: invokevirtual   club/mineman/antigamingchair/AntiGamingChair.getServer:()Lorg/bukkit/Server;
                //   605: invokeinterface org/bukkit/Server.getScheduler:()Lorg/bukkit/scheduler/BukkitScheduler;
                //   610: aload_0         /* this */
                //   611: getfield        club/mineman/antigamingchair/commands/subcommands/LogsCommand$1.this$0:Lclub/mineman/antigamingchair/commands/subcommands/LogsCommand;
                //   614: invokestatic    club/mineman/antigamingchair/commands/subcommands/LogsCommand.access$000:(Lclub/mineman/antigamingchair/commands/subcommands/LogsCommand;)Lclub/mineman/antigamingchair/AntiGamingChair;
                //   617: aload           sb
                //   619: aload_0         /* this */
                //   620: getfield        club/mineman/antigamingchair/commands/subcommands/LogsCommand$1.val$player:Lorg/bukkit/entity/Player;
                //   623: invokedynamic   BootstrapMethod #0, run:(Ljava/lang/StringBuilder;Lorg/bukkit/entity/Player;)Ljava/lang/Runnable;
                //   628: invokeinterface org/bukkit/scheduler/BukkitScheduler.runTaskAsynchronously:(Lorg/bukkit/plugin/Plugin;Ljava/lang/Runnable;)Lorg/bukkit/scheduler/BukkitTask;
                //   633: pop            
                //   634: goto            959
                //   637: new             Ljava/util/concurrent/ConcurrentHashMap;
                //   640: dup            
                //   641: invokespecial   java/util/concurrent/ConcurrentHashMap.<init>:()V
                //   644: astore          violationMap
                //   646: aload_1         /* data */
                //   647: ldc             "data"
                //   649: invokevirtual   org/json/simple/JSONObject.get:(Ljava/lang/Object;)Ljava/lang/Object;
                //   652: checkcast       Lorg/json/simple/JSONArray;
                //   655: astore          array
                //   657: aload           array
                //   659: invokevirtual   org/json/simple/JSONArray.iterator:()Ljava/util/Iterator;
                //   662: astore          9
                //   664: aload           9
                //   666: invokeinterface java/util/Iterator.hasNext:()Z
                //   671: ifeq            716
                //   674: aload           9
                //   676: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
                //   681: astore          object
                //   683: aload           object
                //   685: checkcast       Lorg/json/simple/JSONObject;
                //   688: astore          jsonObject
                //   690: aload           jsonObject
                //   692: ldc             "log"
                //   694: invokevirtual   org/json/simple/JSONObject.get:(Ljava/lang/Object;)Ljava/lang/Object;
                //   697: checkcast       Ljava/lang/String;
                //   700: astore          log
                //   702: aload_0         /* this */
                //   703: getfield        club/mineman/antigamingchair/commands/subcommands/LogsCommand$1.this$0:Lclub/mineman/antigamingchair/commands/subcommands/LogsCommand;
                //   706: aload           violationMap
                //   708: aload           log
                //   710: invokestatic    club/mineman/antigamingchair/commands/subcommands/LogsCommand.access$100:(Lclub/mineman/antigamingchair/commands/subcommands/LogsCommand;Ljava/util/Map;Ljava/lang/String;)V
                //   713: goto            664
                //   716: aload_2         /* logs */
                //   717: invokeinterface java/util/Queue.iterator:()Ljava/util/Iterator;
                //   722: astore          9
                //   724: aload           9
                //   726: invokeinterface java/util/Iterator.hasNext:()Z
                //   731: ifeq            763
                //   734: aload           9
                //   736: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
                //   741: checkcast       Lclub/mineman/antigamingchair/log/Log;
                //   744: astore          log
                //   746: aload_0         /* this */
                //   747: getfield        club/mineman/antigamingchair/commands/subcommands/LogsCommand$1.this$0:Lclub/mineman/antigamingchair/commands/subcommands/LogsCommand;
                //   750: aload           violationMap
                //   752: aload           log
                //   754: invokevirtual   club/mineman/antigamingchair/log/Log.getLog:()Ljava/lang/String;
                //   757: invokestatic    club/mineman/antigamingchair/commands/subcommands/LogsCommand.access$100:(Lclub/mineman/antigamingchair/commands/subcommands/LogsCommand;Ljava/util/Map;Ljava/lang/String;)V
                //   760: goto            724
                //   763: aload           violationMap
                //   765: invokeinterface java/util/Map.isEmpty:()Z
                //   770: ifeq            818
                //   773: aload_0         /* this */
                //   774: getfield        club/mineman/antigamingchair/commands/subcommands/LogsCommand$1.val$player:Lorg/bukkit/entity/Player;
                //   777: new             Ljava/lang/StringBuilder;
                //   780: dup            
                //   781: invokespecial   java/lang/StringBuilder.<init>:()V
                //   784: getstatic       club/mineman/core/util/finalutil/CC.GREEN:Ljava/lang/String;
                //   787: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   790: ldc             "Player "
                //   792: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   795: aload_0         /* this */
                //   796: getfield        club/mineman/antigamingchair/commands/subcommands/LogsCommand$1.val$args:[Ljava/lang/String;
                //   799: iconst_1       
                //   800: aaload         
                //   801: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   804: ldc             " has no logs."
                //   806: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   809: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
                //   812: invokeinterface org/bukkit/entity/Player.sendMessage:(Ljava/lang/String;)V
                //   817: return         
                //   818: aload           violationMap
                //   820: invokeinterface java/util/Map.keySet:()Ljava/util/Set;
                //   825: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
                //   830: astore          9
                //   832: aload           9
                //   834: invokeinterface java/util/Iterator.hasNext:()Z
                //   839: ifeq            905
                //   842: aload           9
                //   844: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
                //   849: checkcast       Ljava/lang/String;
                //   852: astore          string
                //   854: aload           sb
                //   856: getstatic       club/mineman/core/util/finalutil/CC.L_PURPLE:Ljava/lang/String;
                //   859: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   862: aload           string
                //   864: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   867: ldc             " "
                //   869: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   872: getstatic       club/mineman/core/util/finalutil/CC.D_PURPLE:Ljava/lang/String;
                //   875: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   878: ldc_w           "x"
                //   881: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   884: aload           violationMap
                //   886: aload           string
                //   888: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
                //   893: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
                //   896: ldc             "\n"
                //   898: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   901: pop            
                //   902: goto            832
                //   905: aload_0         /* this */
                //   906: getfield        club/mineman/antigamingchair/commands/subcommands/LogsCommand$1.val$player:Lorg/bukkit/entity/Player;
                //   909: new             Ljava/lang/StringBuilder;
                //   912: dup            
                //   913: invokespecial   java/lang/StringBuilder.<init>:()V
                //   916: getstatic       club/mineman/core/util/finalutil/CC.L_PURPLE:Ljava/lang/String;
                //   919: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   922: aload_0         /* this */
                //   923: getfield        club/mineman/antigamingchair/commands/subcommands/LogsCommand$1.val$args:[Ljava/lang/String;
                //   926: iconst_1       
                //   927: aaload         
                //   928: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   931: ldc_w           " logs:"
                //   934: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   937: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
                //   940: invokeinterface org/bukkit/entity/Player.sendMessage:(Ljava/lang/String;)V
                //   945: aload_0         /* this */
                //   946: getfield        club/mineman/antigamingchair/commands/subcommands/LogsCommand$1.val$player:Lorg/bukkit/entity/Player;
                //   949: aload           sb
                //   951: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
                //   954: invokeinterface org/bukkit/entity/Player.sendMessage:(Ljava/lang/String;)V
                //   959: return         
                //    StackMapTable: 00 18 FE 00 36 07 00 5A 07 00 60 07 00 62 2A F9 00 02 FE 00 3F 07 00 81 07 00 81 01 0F 0F 0F 0C 1E 1E FB 00 43 FE 00 21 07 00 A2 07 00 BB 07 00 62 FA 00 72 FC 00 07 07 00 62 FA 00 52 FA 00 29 FE 00 1A 07 01 06 07 00 BB 07 00 62 FA 00 33 FC 00 07 07 00 62 FA 00 26 36 FC 00 0D 07 00 62 FA 00 48 FF 00 35 00 04 07 00 02 07 00 7B 07 00 5A 07 00 81 00 00
                // 
                // The error that occurred was:
                // 
                // java.lang.IllegalStateException: Could not infer any expression.
                //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:374)
                //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
                //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:344)
                //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformCall(AstMethodBodyBuilder.java:1164)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:1009)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:554)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:392)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformBlock(AstMethodBodyBuilder.java:333)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:294)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
                //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
                //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
                //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
                //     at java.base/java.lang.Thread.run(Thread.java:835)
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
        });
    }
    
    private void handleLog(final Map<String, Integer> violations, final String log) {
        final Matcher matcher = LogsCommand.LOG_PATTERN.matcher(log);
        if (matcher.find()) {
            final String type = matcher.group(1);
            final String check = matcher.group(2);
            final String finalData = type + " Check " + check;
            int count = violations.getOrDefault(finalData, 0);
            violations.put(finalData, ++count);
        }
    }
    
    @ConstructorProperties({ "plugin" })
    public LogsCommand(final AntiGamingChair plugin) {
        this.plugin = plugin;
    }
    
    static {
        LOG_PATTERN = Pattern.compile("failed (.*) Check (\\D).(.*)");
    }
}
