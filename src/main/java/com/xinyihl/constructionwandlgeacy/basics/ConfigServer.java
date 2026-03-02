package com.xinyihl.constructionwandlgeacy.basics;

import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public final class ConfigServer {
    private ConfigServer() {
    }

    public static final int LIMIT_CREATIVE = 1024;

    public static String[] similarBlocks = new String[0];

    private static final WandProperties DEFAULT_PROPERTIES = new WandProperties(9, 131, 32, 0, true);
    private static final Map<Item, WandProperties> WAND_PROPERTIES = new HashMap<>();

    public static void registerWandProperties(Item item, WandProperties properties) {
        WAND_PROPERTIES.put(item, properties);
    }

    public static WandProperties getWandProperties(Item item) {
        if (item == null) {
            return DEFAULT_PROPERTIES;
        }
        WandProperties properties = WAND_PROPERTIES.get(item);
        return properties == null ? DEFAULT_PROPERTIES : properties;
    }

    public static final class WandProperties {
        private final int limit;
        private final int durability;
        private final int angel;
        private final int destruction;
        private final boolean upgradeable;

        public WandProperties(int limit, int durability, int angel, int destruction, boolean upgradeable) {
            this.limit = limit;
            this.durability = durability;
            this.angel = angel;
            this.destruction = destruction;
            this.upgradeable = upgradeable;
        }

        public int getLimit() {
            return limit;
        }

        public int getDurability() {
            return durability;
        }

        public int getAngel() {
            return angel;
        }

        public int getDestruction() {
            return destruction;
        }

        public boolean isUpgradeable() {
            return upgradeable;
        }
    }
}
