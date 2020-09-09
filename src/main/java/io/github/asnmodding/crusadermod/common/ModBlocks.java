package io.github.asnmodding.crusadermod.common;

import io.github.asnmodding.crusadermod.common.block.BlockHolyIron;
import io.github.asnmodding.crusadermod.common.block.hook.BlockGrapplingHook;
import io.github.asnmodding.crusadermod.common.block.hook.BlockGrapplingHookRope;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks
{
    public static BlockHolyIron BLOCK_HOLY_IRON;
    public static BlockGrapplingHook BLOCK_GRAPPLING_HOOK;
    public static BlockGrapplingHookRope BLOCK_GRAPPLING_HOOK_LINE;

    public static void init()
    {
        BLOCK_HOLY_IRON = new BlockHolyIron();
        BLOCK_GRAPPLING_HOOK = new BlockGrapplingHook();
        BLOCK_GRAPPLING_HOOK_LINE = new BlockGrapplingHookRope();
    }

    public static void registerModels()
    {
        ModItems.registerModel(Item.getItemFromBlock(BLOCK_HOLY_IRON));
        ModItems.registerModel(Item.getItemFromBlock(BLOCK_GRAPPLING_HOOK));
        ModItems.registerModel(Item.getItemFromBlock(BLOCK_GRAPPLING_HOOK_LINE));
    }

    public static void registerBlocks(IForgeRegistry<Block> registry)
    {
        registry.register(BLOCK_HOLY_IRON);
        registry.register(BLOCK_GRAPPLING_HOOK);
        registry.register(BLOCK_GRAPPLING_HOOK_LINE);
        ForgeRegistries.ITEMS.register(new ItemBlock(BLOCK_HOLY_IRON).setRegistryName(BLOCK_HOLY_IRON.getRegistryName()));
        ForgeRegistries.ITEMS.register(new ItemBlock(BLOCK_GRAPPLING_HOOK).setRegistryName(BLOCK_GRAPPLING_HOOK.getRegistryName()));
        ForgeRegistries.ITEMS.register(new ItemBlock(BLOCK_GRAPPLING_HOOK_LINE).setRegistryName(BLOCK_GRAPPLING_HOOK_LINE.getRegistryName()));
    }
}
