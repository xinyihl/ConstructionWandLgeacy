package com.xinyihl.constructionwandlgeacy.crafting;

import com.xinyihl.constructionwandlgeacy.ConstructionWand;
import com.xinyihl.constructionwandlgeacy.Tags;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public final class ModRecipes {
    private ModRecipes() {
    }

    @SubscribeEvent
    public static void onRegisterRecipes(RegistryEvent.Register<IRecipe> event) {
        event.getRegistry().register(new RecipeWandUpgrade().setRegistryName(ConstructionWand.loc("wand_upgrade")));
    }
}