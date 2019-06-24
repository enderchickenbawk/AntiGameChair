package club.mineman.antigamingchair.commands.subcommands;

import club.mineman.antigamingchair.*;
import org.bukkit.entity.*;
import club.mineman.antigamingchair.event.*;
import org.bukkit.event.*;
import club.mineman.antigamingchair.event.player.*;
import club.mineman.core.*;
import club.mineman.antigamingchair.request.banwave.*;
import org.json.simple.*;
import club.mineman.core.util.finalutil.*;
import club.mineman.core.api.*;
import club.mineman.core.api.request.*;
import club.mineman.antigamingchair.data.*;
import java.beans.*;

public class BanWaveCommand implements SubCommand
{
    private final AntiGamingChair plugin;
    
    @Override
    public void execute(final Player player, final Player target, final String[] args) {
        if (args.length < 2) {
            return;
        }
        final String lowerCase = args[1].toLowerCase();
        switch (lowerCase) {
            case "start": {
                final BanWaveEvent banWaveEvent = new BanWaveEvent(player.getName());
                this.plugin.getServer().getPluginManager().callEvent((Event)banWaveEvent);
                break;
            }
            case "stop": {
                this.plugin.getBanWaveManager().setBanWaveStarted(false);
                break;
            }
            case "add": {
                if (args.length < 3) {
                    return;
                }
                final Player player2 = this.plugin.getServer().getPlayer(args[2]);
                if (player2 == null) {
                    player.sendMessage(String.format(StringUtil.PLAYER_NOT_FOUND, args[2]));
                    return;
                }
                final PlayerData playerData = this.plugin.getPlayerDataManager().getPlayerData(player2);
                if (!playerData.isBanWave()) {
                    playerData.setBanWave(true);
                    final PlayerBanWaveEvent banEvent = new PlayerBanWaveEvent(player2, "Manual");
                    this.plugin.getServer().getPluginManager().callEvent((Event)banEvent);
                    player.sendMessage(CC.L_PURPLE + "Added " + CC.D_PURPLE + player2.getName() + CC.L_PURPLE + " to the ban wave.");
                    break;
                }
                break;
            }
            case "list": {
                CorePlugin.getInstance().getRequestManager().sendRequest((APIMessage)new AGCGetBanWaveRequest(), (RequestCallback)new AbstractCallback("Error fetching the ban wave list.") {
                    public void callback(final JSONObject data) {
                        // 
                        // This method could not be decompiled.
                        // 
                        // Original Bytecode:
                        // 
                        //     1: ldc             "data"
                        //     3: invokevirtual   org/json/simple/JSONObject.get:(Ljava/lang/Object;)Ljava/lang/Object;
                        //     6: checkcast       Lorg/json/simple/JSONArray;
                        //     9: astore_2        /* array */
                        //    10: new             Ljava/lang/StringBuilder;
                        //    13: dup            
                        //    14: invokespecial   java/lang/StringBuilder.<init>:()V
                        //    17: astore_3        /* list */
                        //    18: aload_2         /* array */
                        //    19: invokevirtual   org/json/simple/JSONArray.iterator:()Ljava/util/Iterator;
                        //    22: astore          4
                        //    24: aload           4
                        //    26: invokeinterface java/util/Iterator.hasNext:()Z
                        //    31: ifeq            77
                        //    34: aload           4
                        //    36: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
                        //    41: astore          object
                        //    43: aload           object
                        //    45: checkcast       Lorg/json/simple/JSONObject;
                        //    48: astore          jsonObject
                        //    50: aload           jsonObject
                        //    52: ldc             "name"
                        //    54: invokevirtual   org/json/simple/JSONObject.get:(Ljava/lang/Object;)Ljava/lang/Object;
                        //    57: checkcast       Ljava/lang/String;
                        //    60: astore          name
                        //    62: aload_3         /* list */
                        //    63: aload           name
                        //    65: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                        //    68: ldc             "\n"
                        //    70: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                        //    73: pop            
                        //    74: goto            24
                        //    77: aload_0         /* this */
                        //    78: getfield        club/mineman/antigamingchair/commands/subcommands/BanWaveCommand$1.this$0:Lclub/mineman/antigamingchair/commands/subcommands/BanWaveCommand;
                        //    81: invokestatic    club/mineman/antigamingchair/commands/subcommands/BanWaveCommand.access$000:(Lclub/mineman/antigamingchair/commands/subcommands/BanWaveCommand;)Lclub/mineman/antigamingchair/AntiGamingChair;
                        //    84: invokevirtual   club/mineman/antigamingchair/AntiGamingChair.getServer:()Lorg/bukkit/Server;
                        //    87: invokeinterface org/bukkit/Server.getScheduler:()Lorg/bukkit/scheduler/BukkitScheduler;
                        //    92: aload_0         /* this */
                        //    93: getfield        club/mineman/antigamingchair/commands/subcommands/BanWaveCommand$1.this$0:Lclub/mineman/antigamingchair/commands/subcommands/BanWaveCommand;
                        //    96: invokestatic    club/mineman/antigamingchair/commands/subcommands/BanWaveCommand.access$000:(Lclub/mineman/antigamingchair/commands/subcommands/BanWaveCommand;)Lclub/mineman/antigamingchair/AntiGamingChair;
                        //    99: aload_3         /* list */
                        //   100: aload_0         /* this */
                        //   101: getfield        club/mineman/antigamingchair/commands/subcommands/BanWaveCommand$1.val$player:Lorg/bukkit/entity/Player;
                        //   104: invokedynamic   BootstrapMethod #0, run:(Ljava/lang/StringBuilder;Lorg/bukkit/entity/Player;)Ljava/lang/Runnable;
                        //   109: invokeinterface org/bukkit/scheduler/BukkitScheduler.runTaskAsynchronously:(Lorg/bukkit/plugin/Plugin;Ljava/lang/Runnable;)Lorg/bukkit/scheduler/BukkitTask;
                        //   114: pop            
                        //   115: return         
                        //    StackMapTable: 00 02 FE 00 18 07 00 2C 07 00 2E 07 00 37 FA 00 34
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
                        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:480)
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
                break;
            }
        }
    }
    
    @ConstructorProperties({ "plugin" })
    public BanWaveCommand(final AntiGamingChair plugin) {
        this.plugin = plugin;
    }
}
