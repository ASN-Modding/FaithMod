package io.github.asnmodding.crusadermod.common;

import io.github.asnmodding.crusadermod.common.item.ItemHolyIron;
import io.github.asnmodding.crusadermod.common.item.ItemHolyWater;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems
{
    public static ItemHolyWater HOLY_WATER;
    public static ItemHolyIron HOLY_IRON;

    public static void init()
    {
        HOLY_WATER = new ItemHolyWater();
        HOLY_IRON = new ItemHolyIron();
    }

    public static void registerItems(IForgeRegistry<Item> registry)
    {
        registry.register(HOLY_WATER);
        registry.register(HOLY_IRON);
    }

    public static void registerModels()
    {
        registerModel(HOLY_WATER);
        registerModel(HOLY_IRON);
    }

    private static void registerModel(final Item item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
