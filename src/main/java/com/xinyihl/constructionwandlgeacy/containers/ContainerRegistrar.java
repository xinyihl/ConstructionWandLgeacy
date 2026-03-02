package com.xinyihl.constructionwandlgeacy.containers;

import com.xinyihl.constructionwandlgeacy.ConstructionWand;
import com.xinyihl.constructionwandlgeacy.containers.handlers.HandlerCapability;
import com.xinyihl.constructionwandlgeacy.containers.handlers.HandlerShulkerbox;
import net.minecraftforge.fml.common.Loader;

public final class ContainerRegistrar {
    private ContainerRegistrar() {
    }

    public static void register() {
        ConstructionWand.instance.containerManager.register(new HandlerCapability());
        ConstructionWand.instance.containerManager.register(new HandlerShulkerbox());

        if (Loader.isModLoaded("botania")) {
            ConstructionWand.LOGGER.info("Botania detected; integration handler will be added in next migration step.");
        }
    }
}
