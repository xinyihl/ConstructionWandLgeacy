package com.xinyihl.constructionwandlgeacy.items.core;

import com.xinyihl.constructionwandlgeacy.api.IWandAction;
import com.xinyihl.constructionwandlgeacy.wand.action.ActionAngel;

public class ItemCoreAngel extends ItemCore {
    @Override
    public int getColor() {
        return 0xE9B115;
    }

    @Override
    public IWandAction getWandAction() {
        return new ActionAngel();
    }
}
