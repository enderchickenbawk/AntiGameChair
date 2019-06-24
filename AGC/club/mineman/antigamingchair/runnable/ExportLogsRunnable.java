package club.mineman.antigamingchair.runnable;

import club.mineman.antigamingchair.*;
import club.mineman.antigamingchair.log.*;
import org.json.simple.*;
import java.sql.*;
import club.mineman.core.*;
import club.mineman.antigamingchair.request.*;
import club.mineman.core.api.request.*;
import club.mineman.core.api.*;
import java.util.*;
import java.beans.*;

public class ExportLogsRunnable implements Runnable
{
    private final AntiGamingChair plugin;
    
    @Override
    public void run() {
        if (this.plugin.getLogManager().getLogQueue().isEmpty()) {
            return;
        }
        final Set<JSONArray> data = new HashSet<JSONArray>();
        JSONArray current = new JSONArray();
        for (final Log log : this.plugin.getLogManager().getLogQueue()) {
            final JSONObject object = new JSONObject();
            object.put((Object)"timestamp", (Object)new Timestamp(log.getTimestamp()).toString());
            object.put((Object)"player-id", (Object)log.getMinemanId());
            object.put((Object)"log", (Object)log.getLog());
            current.add((Object)object.toJSONString());
            if (current.toJSONString().length() >= 1000) {
                data.add(current);
                current = new JSONArray();
            }
        }
        if (current.size() > 0) {
            data.add(current);
        }
        for (final JSONArray array : data) {
            CorePlugin.getInstance().getRequestManager().sendRequest((APIMessage)new AGCLogRequest(array), (RequestCallback)new RequestCallback() {
                public void callback(final JSONObject data) {
                    final String response = (String)data.get((Object)"response");
                    if (!response.equals("success")) {
                        ExportLogsRunnable.this.onError(data.toJSONString());
                    }
                }
                
                public void error(final String message) {
                    ExportLogsRunnable.this.onError(message);
                }
            });
        }
        this.plugin.getLogManager().clearLogQueue();
    }
    
    private void onError(final String message) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        club/mineman/antigamingchair/runnable/ExportLogsRunnable.plugin:Lclub/mineman/antigamingchair/AntiGamingChair;
        //     4: invokevirtual   club/mineman/antigamingchair/AntiGamingChair.getAlertsManager:()Lclub/mineman/antigamingchair/manager/AlertsManager;
        //     7: new             Ljava/lang/StringBuilder;
        //    10: dup            
        //    11: invokespecial   java/lang/StringBuilder.<init>:()V
        //    14: getstatic       club/mineman/core/util/finalutil/CC.D_RED:Ljava/lang/String;
        //    17: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    20: ldc             "ERROR SAVING LOGS! Check console for details."
        //    22: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    25: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    28: invokevirtual   club/mineman/antigamingchair/manager/AlertsManager.forceAlert:(Ljava/lang/String;)V
        //    31: aload_0         /* this */
        //    32: getfield        club/mineman/antigamingchair/runnable/ExportLogsRunnable.plugin:Lclub/mineman/antigamingchair/AntiGamingChair;
        //    35: invokevirtual   club/mineman/antigamingchair/AntiGamingChair.getLogger:()invokevirtual  !!! ERROR
        //    38: aload_1         /* message */
        //    39: invokevirtual   invokevirtual  !!! ERROR
        //    42: return         
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
    
    @ConstructorProperties({ "plugin" })
    public ExportLogsRunnable(final AntiGamingChair plugin) {
        this.plugin = plugin;
    }
}
