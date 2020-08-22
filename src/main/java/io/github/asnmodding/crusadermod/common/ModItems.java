package io.github.asnmodding.crusadermod.common;

import io.github.asnmodding.crusadermod.CrusaderMod;
import io.github.asnmodding.crusadermod.common.item.ItemHolyIron;
import io.github.asnmodding.crusadermod.common.item.ItemHolyWater;
import io.github.asnmodding.crusadermod.common.item.armor.CrusaderBoots;
import io.github.asnmodding.crusadermod.common.item.armor.CrusaderChestplate;
import io.github.asnmodding.crusadermod.common.item.armor.CrusaderHelmet;
import io.github.asnmodding.crusadermod.common.item.armor.CrusaderLeggings;
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
    public static CrusaderHelmet CRUSADER_HELMET;
    public static CrusaderChestplate CRUSADER_CHESTPLATE;
    public static CrusaderLeggings CRUSADER_LEGGINS;
    public static CrusaderBoots CRUSADER_BOOTS;

    public static void init()
    {
        //Register holy iron armor material
        EnumHelper.addArmorMaterial("holy_iron", CrusaderMod.MOD_ID + ":crusader_armor", 15, new int[]{2, 5, 7, 2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F);

        // Items
        HOLY_WATER = new ItemHolyWater();
        HOLY_IRON = new ItemHolyIron();
//        HOLY_WATER_SPLASH = new ItemHolyWaterSplash();

        // Holy Armor
        CRUSADER_HELMET = new CrusaderHelmet();
        CRUSADER_CHESTPLATE = new CrusaderChestplate();
        CRUSADER_LEGGINS = new CrusaderLeggings();
        CRUSADER_BOOTS = new CrusaderBoots();
    }

    public static void registerItems(IForgeRegistry<Item> registry)
    {
        registry.register(HOLY_WATER);
        registry.register(HOLY_IRON);

        registry.register(CRUSADER_HELMET);
        registry.register(CRUSADER_CHESTPLATE);
        registry.register(CRUSADER_LEGGINS);
        registry.register(CRUSADER_BOOTS);
    }

    public static void registerModels()
    {
        registerModel(HOLY_WATER);
        registerModel(HOLY_IRON);

        registerModel(CRUSADER_HELMET);
        registerModel(CRUSADER_CHESTPLATE);
        registerModel(CRUSADER_LEGGINS);
        registerModel(CRUSADER_BOOTS);
    }

    private static void registerModel(final Item item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
