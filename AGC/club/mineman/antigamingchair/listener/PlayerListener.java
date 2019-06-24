package club.mineman.antigamingchair.listener;

import club.mineman.antigamingchair.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import io.netty.buffer.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;
import org.bukkit.entity.*;
import club.mineman.antigamingchair.data.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import club.mineman.antigamingchair.util.*;
import club.mineman.antigamingchair.commands.subcommands.*;
import club.mineman.antigamingchair.check.*;
import club.mineman.paper.event.*;
import club.mineman.core.*;
import club.mineman.antigamingchair.log.*;
import club.mineman.antigamingchair.request.banwave.*;
import org.json.simple.*;
import club.mineman.core.api.*;
import club.mineman.core.api.request.*;
import club.mineman.core.mineman.*;
import net.minecraft.server.v1_8_R3.*;
import java.text.*;
import org.bukkit.*;
import java.util.function.*;
import club.mineman.core.rank.*;
import club.mineman.core.util.finalutil.*;
import java.util.*;
import club.mineman.antigamingchair.event.player.*;
import org.bukkit.command.*;
import java.beans.*;

public class PlayerListener implements Listener
{
    private final AntiGamingChair plugin;
    
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        this.plugin.getPlayerDataManager().addPlayerData(event.getPlayer());
        final PlayerConnection playerConnection;
        final PacketPlayOutCustomPayload packetPlayOutCustomPayload;
        final PlayerConnection playerConnection2;
        final PacketPlayOutCustomPayload packetPlayOutCustomPayload2;
        this.plugin.getServer().getScheduler().runTaskLater((Plugin)this.plugin, () -> {
            playerConnection = ((CraftPlayer)event.getPlayer()).getHandle().playerConnection;
            new PacketPlayOutCustomPayload("REGISTER", new PacketDataSerializer(Unpooled.wrappedBuffer("CB-Client".getBytes())));
            playerConnection.sendPacket((Packet)packetPlayOutCustomPayload);
            playerConnection2 = ((CraftPlayer)event.getPlayer()).getHandle().playerConnection;
            new PacketPlayOutCustomPayload("REGISTER", new PacketDataSerializer(Unpooled.wrappedBuffer("CC".getBytes())));
            playerConnection2.sendPacket((Packet)packetPlayOutCustomPayload2);
        }, 10L);
    }
    
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        if (this.plugin.getAlertsManager().hasAlertsToggled(event.getPlayer())) {
            this.plugin.getAlertsManager().toggleAlerts(event.getPlayer());
        }
        this.plugin.getPlayerDataManager().removePlayerData(event.getPlayer());
    }
    
    @EventHandler
    public void onTeleport(final PlayerTeleportEvent event) {
        final Player player = event.getPlayer();
        final PlayerData playerData = this.plugin.getPlayerDataManager().getPlayerData(player);
        if (playerData != null) {
            playerData.setLastTeleportTime(System.currentTimeMillis());
            playerData.setSendingVape(true);
        }
    }
    
    @EventHandler
    public void onDeath(final PlayerDeathEvent event) {
        final Player player = event.getEntity();
        final PlayerData playerData = this.plugin.getPlayerDataManager().getPlayerData(player);
        if (playerData != null) {
            playerData.setInventoryOpen(false);
        }
    }
    
    @EventHandler
    public void onPlayerChangedWorld(final PlayerChangedWorldEvent event) {
        final Player player = event.getPlayer();
        final PlayerData playerData = this.plugin.getPlayerDataManager().getPlayerData(player);
        if (playerData != null) {
            playerData.setInventoryOpen(false);
        }
    }
    
    @EventHandler
    public void onPlayerUpdatePosition(final PlayerUpdatePositionEvent event) {
        final Player player = event.getPlayer();
        if (player.getAllowFlight()) {
            return;
        }
        final PlayerData playerData = this.plugin.getPlayerDataManager().getPlayerData(player);
        if (playerData == null) {
            return;
        }
        playerData.setOnGround(BlockUtil.isOnGround(event.getTo(), 0) || BlockUtil.isOnGround(event.getTo(), 1));
        if (playerData.isOnGround()) {
            playerData.setLastGroundY(event.getTo().getY());
        }
        playerData.setInLiquid(BlockUtil.isOnLiquid(event.getTo(), 0) || BlockUtil.isOnLiquid(event.getTo(), 1));
        playerData.setInWeb(BlockUtil.isOnWeb(event.getTo(), 0));
        playerData.setOnIce(BlockUtil.isOnIce(event.getTo(), 1) || BlockUtil.isOnIce(event.getTo(), 2));
        playerData.setOnStairs(BlockUtil.isOnStairs(event.getTo(), 0) || BlockUtil.isOnStairs(event.getTo(), 1));
        playerData.setUnderBlock(BlockUtil.isOnGround(event.getTo(), -2));
        if (event.getTo().getY() != event.getFrom().getY() && playerData.getVelocityV() > 0) {
            playerData.setVelocityV(playerData.getVelocityV() - 1);
        }
        if (Math.hypot(event.getTo().getX() - event.getFrom().getX(), event.getTo().getZ() - event.getFrom().getZ()) > 0.0 && playerData.getVelocityH() > 0) {
            playerData.setVelocityH(playerData.getVelocityH() - 1);
        }
        for (final Class<? extends ICheck> checkClass : PlayerData.CHECKS) {
            if (!ToggleCommand.DISABLED_CHECKS.contains(checkClass.getSimpleName().toUpperCase())) {
                final ICheck check = (ICheck)playerData.getCheck(checkClass);
                if (check.getType() == PlayerUpdatePositionEvent.class) {
                    check.handleCheck(player, event);
                }
            }
        }
        if (playerData.getVelocityY() > 0.0 && event.getTo().getY() > event.getFrom().getY()) {
            playerData.setVelocityY(0.0);
        }
    }
    
    @EventHandler
    public void onPlayerUpdateRotation(final PlayerUpdateRotationEvent event) {
        final Player player = event.getPlayer();
        if (player.getAllowFlight()) {
            return;
        }
        final PlayerData playerData = this.plugin.getPlayerDataManager().getPlayerData(player);
        if (playerData == null) {
            return;
        }
        for (final Class<? extends ICheck> checkClass : PlayerData.CHECKS) {
            if (!ToggleCommand.DISABLED_CHECKS.contains(checkClass.getSimpleName().toUpperCase())) {
                final ICheck check = (ICheck)playerData.getCheck(checkClass);
                if (check.getType() == PlayerUpdateRotationEvent.class) {
                    check.handleCheck(player, event);
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerBanWave(final PlayerBanWaveEvent event) {
        if (!this.plugin.isAntiCheatEnabled() && !event.getReason().equals("Manual")) {
            return;
        }
        final Player player = event.getPlayer();
        if (player == null) {
            return;
        }
        final Mineman mineman = CorePlugin.getInstance().getPlayerManager().getPlayer(player.getUniqueId());
        final Log log = new Log(mineman.getId(), "was added to the next ban wave for " + event.getReason());
        this.plugin.getLogManager().addToLogQueue(log);
        CorePlugin.getInstance().getRequestManager().sendRequest((APIMessage)new AGCAddBanWaveRequest(mineman.getId(), event.getReason()), (RequestCallback)new AbstractCallback("Error adding " + player.getName() + " to the ban wave.") {
            public void callback(final JSONObject data) {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     1: ldc             "response"
                //     3: invokevirtual   org/json/simple/JSONObject.get:(Ljava/lang/Object;)Ljava/lang/Object;
                //     6: checkcast       Ljava/lang/String;
                //     9: astore_2        /* response */
                //    10: aload_2         /* response */
                //    11: ldc             "success"
                //    13: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
                //    16: ifeq            64
                //    19: aload_0         /* this */
                //    20: getfield        club/mineman/antigamingchair/listener/PlayerListener$1.this$0:Lclub/mineman/antigamingchair/listener/PlayerListener;
                //    23: invokestatic    club/mineman/antigamingchair/listener/PlayerListener.access$000:(Lclub/mineman/antigamingchair/listener/PlayerListener;)Lclub/mineman/antigamingchair/AntiGamingChair;
                //    26: invokevirtual   club/mineman/antigamingchair/AntiGamingChair.getLogger:()invokevirtual  !!! ERROR
                //    29: new             Ljava/lang/StringBuilder;
                //    32: dup            
                //    33: invokespecial   java/lang/StringBuilder.<init>:()V
                //    36: ldc             "Added "
                //    38: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //    41: aload_0         /* this */
                //    42: getfield        club/mineman/antigamingchair/listener/PlayerListener$1.val$player:Lorg/bukkit/entity/Player;
                //    45: invokeinterface org/bukkit/entity/Player.getName:()Ljava/lang/String;
                //    50: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //    53: ldc             " to the ban wave."
                //    55: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //    58: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
                //    61: invokevirtual   invokevirtual  !!! ERROR
                //    64: return         
                //    StackMapTable: 00 01 FC 00 40 07 00 27
                // 
                // The error that occurred was:
                // 
                // java.lang.IllegalStateException: Invalid BootstrapMethods attribute entry: 2 additional arguments required for method java/lang/invoke/StringConcatFactory.makeConcatWithConstants, but only 1 specified.
                //     at com.strobel.assembler.ir.Error.invalidBootstrapMethodEntry(Error.java:244)
                //     at com.strobel.assembler.ir.MetadataReader.readAttributeCore(MetadataReader.java:267)
                //     at com.strobel.assembler.metadata.ClassFileReader.readAttributeCore(ClassFileReader.java:261)
                //     at com.strobel.assembler.ir.MetadataReader.inflateAttributes(MetadataReader.java:426)
                //     at com.strobel.assembler.metadata.ClassFileReader.visitAttributes(ClassFileReader.java:1134)
                //     at com.strobel.assembler.metadata.ClassFileReader.readClass(ClassFileReader.java:439)
                //     at com.strobel.assembler.metadata.ClassFileReader.readClass(ClassFileReader.java:377)
                //     at com.strobel.assembler.metadata.MetadataSystem.resolveType(MetadataSystem.java:129)
                //     at com.strobel.assembler.metadata.MetadataSystem.resolveCore(MetadataSystem.java:81)
                //     at com.strobel.assembler.metadata.MetadataResolver.resolve(MetadataResolver.java:104)
                //     at com.strobel.assembler.metadata.CoreMetadataFactory$UnresolvedType.resolve(CoreMetadataFactory.java:616)
                //     at com.strobel.assembler.metadata.MetadataResolver.resolve(MetadataResolver.java:128)
                //     at com.strobel.assembler.metadata.CoreMetadataFactory$UnresolvedType.resolve(CoreMetadataFactory.java:626)
                //     at com.strobel.assembler.metadata.MethodReference.resolve(MethodReference.java:177)
                //     at com.strobel.decompiler.ast.TypeAnalysis.inferCall(TypeAnalysis.java:2438)
                //     at com.strobel.decompiler.ast.TypeAnalysis.doInferTypeForExpression(TypeAnalysis.java:1029)
                //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:803)
                //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:672)
                //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:655)
                //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:365)
                //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
                //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:109)
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
    
    @EventHandler
    public void onPlayerAlert(final PlayerAlertEvent event) {
        if (!this.plugin.isAntiCheatEnabled()) {
            event.setCancelled(true);
            return;
        }
        final Player player = event.getPlayer();
        if (player == null) {
            return;
        }
        final PlayerData playerData = this.plugin.getPlayerDataManager().getPlayerData(player);
        if (playerData == null) {
            return;
        }
        final double tps = MinecraftServer.getServer().tps1.getAverage();
        String fixedTPS = new DecimalFormat(".##").format(tps);
        if (tps > 20.0) {
            fixedTPS = "20.0";
        }
        final String alert = event.getAlert() + ChatColor.LIGHT_PURPLE + " Ping " + playerData.getPing() + " ms. TPS " + fixedTPS + ".";
        final Set<UUID> playerUUIDs = new HashSet<UUID>(this.plugin.getAlertsManager().getAlertsToggled());
        playerUUIDs.addAll(playerData.getPlayersWatching());
        final PlayerAlertEvent.AlertType type = event.getAlertType();
        final Mineman mineman;
        final PlayerAlertEvent.AlertType alertType;
        PlayerData alertedData;
        boolean sendAlert;
        final Iterator<String> iterator;
        String phrase;
        final String str;
        playerUUIDs.stream().map((Function<? super Object, ?>)this.plugin.getServer()::getPlayer).filter(Objects::nonNull).forEach(p -> {
            mineman = CorePlugin.getInstance().getPlayerManager().getPlayer(p.getUniqueId());
            if ((mineman.getRank().hasRank(Rank.ADMIN) && alertType == PlayerAlertEvent.AlertType.RELEASE) || mineman.getRank().hasRank(Rank.DEVELOPER)) {
                alertedData = this.plugin.getPlayerDataManager().getPlayerData(p);
                sendAlert = true;
                alertedData.getFilteredPhrases().iterator();
                while (iterator.hasNext()) {
                    phrase = iterator.next();
                    if (str.toLowerCase().contains(phrase)) {
                        sendAlert = false;
                        break;
                    }
                }
                if (sendAlert) {
                    p.sendMessage(ChatColor.LIGHT_PURPLE + event.getPlayer().getName() + CC.D_PURPLE + " " + str);
                }
            }
            return;
        });
        final Mineman mineman2 = CorePlugin.getInstance().getPlayerManager().getPlayer(player.getUniqueId());
        final Log log = new Log(mineman2.getId(), ChatColor.stripColor(alert));
        this.plugin.getLogManager().addToLogQueue(log);
    }
    
    @EventHandler
    public void onPlayerBan(final PlayerBanEvent event) {
        if (!this.plugin.isAntiCheatEnabled()) {
            event.setCancelled(true);
            return;
        }
        final Player player = event.getPlayer();
        if (player == null) {
            return;
        }
        this.plugin.getServer().broadcastMessage(CC.S + "--------------------------------------------------\n" + CC.R + "\u2718 " + CC.PINK + player.getName() + CC.D_PURPLE + " was banned by " + CC.PINK + "AntiGamingChair" + CC.D_PURPLE + " for cheating.\n" + CC.R + CC.S + "--------------------------------------------------\n");
        this.plugin.getServer().getScheduler().runTask((Plugin)this.plugin, () -> this.plugin.getServer().dispatchCommand((CommandSender)this.plugin.getServer().getConsoleSender(), "ban " + player.getName() + " Unfair Advantage -s"));
        final Mineman mineman = CorePlugin.getInstance().getPlayerManager().getPlayer(player.getUniqueId());
        final Log log = new Log(mineman.getId(), "was autobanned for " + event.getReason());
        this.plugin.getLogManager().addToLogQueue(log);
    }
    
    @ConstructorProperties({ "plugin" })
    public PlayerListener(final AntiGamingChair plugin) {
        this.plugin = plugin;
    }
}
