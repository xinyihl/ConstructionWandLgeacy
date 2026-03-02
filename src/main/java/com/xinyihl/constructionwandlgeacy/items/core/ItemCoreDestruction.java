package com.xinyihl.constructionwandlgeacy.items.core;

import com.xinyihl.constructionwandlgeacy.api.IWandAction;
import com.xinyihl.constructionwandlgeacy.wand.action.ActionDestruction;

public class ItemCoreDestruction extends ItemCore {
    @Override
    public int getColor() {
        return 0xFF0000;
    }

    @Override
    public IWandAction getWandAction() {
        return new ActionDestruction();
    }
}
