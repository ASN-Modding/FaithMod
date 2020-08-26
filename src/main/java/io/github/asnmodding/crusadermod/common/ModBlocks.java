package io.github.asnmodding.crusadermod.common;

import io.github.asnmodding.crusadermod.common.block.BlockHolyIron;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks
{
    public static BlockHolyIron BLOCK_HOLY_IRON;

    public static void init()
    {
        BLOCK_HOLY_IRON = new BlockHolyIron();
    }

    public static void registerModels()
    {
        ModItems.registerModel(Item.getItemFromBlock(BLOCK_HOLY_IRON));
    }

    public static void registerBlocks(IForgeRegistry<Block> registry)
    {
        registry.register(BLOCK_HOLY_IRON);
        ForgeRegistries.ITEMS.register(new ItemBlock(BLOCK_HOLY_IRON).setRegistryName(BLOCK_HOLY_IRON.getRegistryName()));
    }
}
