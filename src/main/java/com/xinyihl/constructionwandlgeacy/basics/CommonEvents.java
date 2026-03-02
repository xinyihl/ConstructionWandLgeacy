package com.xinyihl.constructionwandlgeacy.basics;

import com.xinyihl.constructionwandlgeacy.ConstructionWand;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class CommonEvents {
    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (ConstructionWand.instance != null && ConstructionWand.instance.undoHistory != null && event.player != null) {
            ConstructionWand.instance.undoHistory.clearHistory(event.player.getUniqueID());
        }
    }

    public void onServerStarting(FMLServerStartingEvent event) {
        ReplacementRegistry.init();
    }
}
