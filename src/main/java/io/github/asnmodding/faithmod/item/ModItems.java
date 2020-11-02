package io.github.asnmodding.faithmod.item;

import io.github.asnmodding.faithmod.FaithMod;
import io.github.asnmodding.faithmod.block.ModBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems
{
    public static final ItemGroup ITEM_GROUP = new ItemGroup(FaithMod.MODID)
    {
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(ModBlocks.MYSTICAL_FLOWER);
        }
    };

    public static void registerItems(final IForgeRegistry<Item> registry)
    {
        BlockItem flowerBlock = new BlockItem(ModBlocks.MYSTICAL_FLOWER, new Item.Properties().maxStackSize(64).group(ITEM_GROUP).setNoRepair());
        flowerBlock.setRegistryName(ModBlocks.MYSTICAL_FLOWER.getRegistryName());
        registry.register(flowerBlock);
    }
}
