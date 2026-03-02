package com.xinyihl.constructionwandlgeacy.items.core;

import com.xinyihl.constructionwandlgeacy.ConstructionWand;
import com.xinyihl.constructionwandlgeacy.api.IWandAction;
import com.xinyihl.constructionwandlgeacy.api.IWandCore;
import com.xinyihl.constructionwandlgeacy.wand.action.ActionConstruction;
import net.minecraft.util.ResourceLocation;

public class CoreDefault implements IWandCore {
    @Override
    public int getColor() {
        return -1;
    }

    @Override
    public IWandAction getWandAction() {
        return new ActionConstruction();
    }

    @Override
    public ResourceLocation getRegistryName() {
        return ConstructionWand.loc("default");
    }
}
