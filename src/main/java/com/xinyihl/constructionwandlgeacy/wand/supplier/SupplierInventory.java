package com.xinyihl.constructionwandlgeacy.wand.supplier;

import com.xinyihl.constructionwandlgeacy.ConstructionWand;
import com.xinyihl.constructionwandlgeacy.api.IWandSupplier;
import com.xinyihl.constructionwandlgeacy.basics.ReplacementRegistry;
import com.xinyihl.constructionwandlgeacy.basics.WandUtil;
import com.xinyihl.constructionwandlgeacy.basics.option.WandOptions;
import com.xinyihl.constructionwandlgeacy.basics.pool.IPool;
import com.xinyihl.constructionwandlgeacy.basics.pool.OrderedPool;
import com.xinyihl.constructionwandlgeacy.containers.ContainerManager;
import com.xinyihl.constructionwandlgeacy.wand.undo.PlaceSnapshot;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SupplierInventory implements IWandSupplier {
    protected final EntityPlayer player;
    protected final WandOptions options;

    protected Map<ItemBlock, Integer> itemCounts;
    protected IPool<ItemBlock> itemPool;

    public SupplierInventory(EntityPlayer player, WandOptions options) {
        this.player = player;
        this.options = options;
    }

    @Override
    public void getSupply(@Nullable ItemBlock target) {
        itemCounts = new LinkedHashMap<>();
        itemPool = new OrderedPool<>();

        ItemStack offhandStack = player.getHeldItemOffhand();
        if (!offhandStack.isEmpty() && offhandStack.getItem() instanceof ItemBlock) {
            addBlockItem((ItemBlock) offhandStack.getItem());
            return;
        }

        if (target != null) {
            addBlockItem(target);
            if (options.match.get() != WandOptions.MATCH.EXACT) {
                for (Item item : ReplacementRegistry.getMatchingSet(target)) {
                    if (item instanceof ItemBlock) {
                        addBlockItem((ItemBlock) item);
                    }
                }
            }
        }
    }

    protected void addBlockItem(ItemBlock item) {
        int count = player.isCreative() ? Integer.MAX_VALUE : WandUtil.countItem(player, item);
        if (count > 0) {
            itemCounts.put(item, count);
            itemPool.add(item);
        }
    }

    @Nullable
    @Override
    public PlaceSnapshot getPlaceSnapshot(World world, BlockPos pos, RayTraceResult rayTraceResult, @Nullable IBlockState supportingBlock) {
        if (!WandUtil.isPositionPlaceable(world, player, pos, options.replace.get())) {
            return null;
        }
        itemPool.reset();

        while (true) {
            ItemBlock item = itemPool.draw();
            if (item == null) {
                return null;
            }

            Integer count = itemCounts.get(item);
            if (count == null || count == 0) {
                continue;
            }

            PlaceSnapshot snapshot = PlaceSnapshot.get(world, player, rayTraceResult, pos, item, supportingBlock, options);
            if (snapshot != null) {
                int remaining = count - 1;
                itemCounts.put(item, remaining);
                if (remaining <= 0) {
                    itemPool.remove(item);
                }
                return snapshot;
            }
        }
    }

    @Override
    public int takeItemStack(ItemStack stack) {
        int count = stack.getCount();
        if (count <= 0 || player.isCreative()) {
            return 0;
        }

        Item item = stack.getItem();
        List<ItemStack> hotbar = WandUtil.getHotbarWithOffhand(player);
        List<ItemStack> mainInv = WandUtil.getMainInv(player);

        count = takeItemsInvList(count, item, mainInv, false);
        count = takeItemsInvList(count, item, mainInv, true);
        count = takeItemsInvList(count, item, hotbar, true);
        count = takeItemsInvList(count, item, hotbar, false);
        return count;
    }

    private int takeItemsInvList(int count, Item item, List<ItemStack> inventory, boolean container) {
        ContainerManager containerManager = ConstructionWand.instance.containerManager;

        for (ItemStack inventoryStack : inventory) {
            if (count == 0) {
                break;
            }

            if (container) {
                count = containerManager.useItems(player, new ItemStack(item), inventoryStack, count);
                continue;
            }

            if (WandUtil.stackEquals(inventoryStack, item)) {
                int toTake = Math.min(count, inventoryStack.getCount());
                inventoryStack.shrink(toTake);
                count -= toTake;
                player.inventory.markDirty();
            }
        }

        return count;
    }
}
