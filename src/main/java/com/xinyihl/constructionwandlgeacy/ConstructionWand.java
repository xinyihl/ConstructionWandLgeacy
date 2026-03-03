package com.xinyihl.constructionwandlgeacy;

import com.xinyihl.constructionwandlgeacy.basics.CommonEvents;
import com.xinyihl.constructionwandlgeacy.basics.ReplacementRegistry;
import com.xinyihl.constructionwandlgeacy.basics.config.ConfigServer;
import com.xinyihl.constructionwandlgeacy.client.ClientEvents;
import com.xinyihl.constructionwandlgeacy.client.RenderBlockPreview;
import com.xinyihl.constructionwandlgeacy.containers.ContainerManager;
import com.xinyihl.constructionwandlgeacy.containers.ContainerRegistrar;
import com.xinyihl.constructionwandlgeacy.network.ModMessages;
import com.xinyihl.constructionwandlgeacy.wand.undo.UndoHistory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
public class ConstructionWand {

    @Mod.Instance(Tags.MOD_ID)
    public static ConstructionWand instance;

    public static Logger LOGGER;

    public ContainerManager containerManager;
    public UndoHistory undoHistory;
    private CommonEvents commonEvents;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();
        ConfigServer.sync();
        containerManager = new ContainerManager();
        undoHistory = new UndoHistory();
        commonEvents = new CommonEvents();
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(commonEvents);

        if (event.getSide() == Side.CLIENT) {
            MinecraftForge.EVENT_BUS.register(new ClientEvents());
            RenderBlockPreview renderBlockPreview = new RenderBlockPreview();
            MinecraftForge.EVENT_BUS.register(renderBlockPreview);
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ReplacementRegistry.init();
        ContainerRegistrar.register();
        ModMessages.register();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        if (commonEvents != null) {
            commonEvents.onServerStarting(event);
        }
    }

    public static ResourceLocation loc(String name) {
        return new ResourceLocation(Tags.MOD_ID, name);
    }
}
