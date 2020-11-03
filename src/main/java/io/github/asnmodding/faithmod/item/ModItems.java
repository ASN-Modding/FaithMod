package io.github.asnmodding.faithmod.item;

import io.github.asnmodding.faithmod.FaithMod;
import io.github.asnmodding.faithmod.block.ModBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems
{
    public static final HolyWater HOLY_WATER = new HolyWater();

    public static void registerItems(final IForgeRegistry<Item> registry)
    {
        registry.register(HOLY_WATER);

        BlockItem flowerBlock = new BlockItem(ModBlocks.MYSTICAL_FLOWER, new Item.Properties().maxStackSize(64).group(FaithMod.ITEM_GROUP).setNoRepair());
        flowerBlock.setRegistryName(ModBlocks.MYSTICAL_FLOWER.getRegistryName());
        registry.register(flowerBlock);
    }
}
