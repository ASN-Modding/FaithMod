package io.github.asnmodding.crusadermod.common;

import io.github.asnmodding.crusadermod.CrusaderMod;
import io.github.asnmodding.crusadermod.common.item.ItemHolyIron;
import io.github.asnmodding.crusadermod.common.item.ItemHolyWater;
import io.github.asnmodding.crusadermod.common.item.armor.HolyIronHelmet;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems
{
    // Items
    public static ItemHolyWater HOLY_WATER;
    public static ItemHolyIron HOLY_IRON;
//    public static ItemHolyWaterSplash HOLY_WATER_SPLASH;

    // Armor
    public static HolyIronHelmet HOLY_IRON_HELMET;

    public static void init()
    {
        //Register holy iron armor material
        EnumHelper.addArmorMaterial("holy_iron", CrusaderMod.MOD_ID + ":holy_iron_armor", 15, new int[]{2, 5, 7, 2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F);

        // Items
        HOLY_WATER = new ItemHolyWater();
        HOLY_IRON = new ItemHolyIron();
//        HOLY_WATER_SPLASH = new ItemHolyWaterSplash();

        // Holy Armor
        HOLY_IRON_HELMET = new HolyIronHelmet();
    }

    public static void registerItems(IForgeRegistry<Item> registry)
    {
        registry.register(HOLY_WATER);
        registry.register(HOLY_IRON);
        registry.register(HOLY_IRON_HELMET);
//        registry.register(HOLY_WATER_SPLASH);
    }

    public static void registerModels()
    {
        registerModel(HOLY_WATER);
        registerModel(HOLY_IRON);
        registerModel(HOLY_IRON_HELMET);
//        registerModel(HOLY_WATER_SPLASH);
    }

    private static void registerModel(final Item item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
