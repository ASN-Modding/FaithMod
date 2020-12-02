package io.github.asnmodding.faithmod.item;

import io.github.asnmodding.faithmod.FaithMod;
import io.github.asnmodding.faithmod.block.ModBlocks;
import io.github.asnmodding.faithmod.item.armor.CrusaderBoots;
import io.github.asnmodding.faithmod.item.armor.CrusaderChestplate;
import io.github.asnmodding.faithmod.item.armor.CrusaderHelmet;
import io.github.asnmodding.faithmod.item.armor.CrusaderLeggings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems
{
    public static final HolyWater HOLY_WATER = new HolyWater();
    public static final ItemPriestStaff PRIEST_STAFF = new ItemPriestStaff();
    public static final ItemDivineSword DIVINE_SWORD = new ItemDivineSword();
    public static final ItemHolyIron HOLY_IRON = new ItemHolyIron();
    public static final ItemDivineIron DIVINE_IRON = new ItemDivineIron();

    // Armor
    public static final CrusaderHelmet CRUSADER_HELMET = new CrusaderHelmet();
    public static final CrusaderChestplate CRUSADER_CHESTPLATE = new CrusaderChestplate();
    public static final CrusaderLeggings CRUSADER_LEGGINGS = new CrusaderLeggings();
    public static final CrusaderBoots CRUSADER_BOOTS = new CrusaderBoots();

    public static void registerItems(final IForgeRegistry<Item> registry)
    {
        registry.register(HOLY_WATER);
        registry.register(PRIEST_STAFF);
        registry.register(DIVINE_IRON);
        registry.register(DIVINE_SWORD);
        registry.register(HOLY_IRON);

        registry.register(CRUSADER_HELMET);
        registry.register(CRUSADER_CHESTPLATE);
        registry.register(CRUSADER_LEGGINGS);
        registry.register(CRUSADER_BOOTS);

        BlockItem flowerBlock = new BlockItem(ModBlocks.MYSTICAL_FLOWER, new Item.Properties().maxStackSize(64).group(FaithMod.ITEM_GROUP).setNoRepair());
        flowerBlock.setRegistryName(ModBlocks.MYSTICAL_FLOWER.getRegistryName());
        registry.register(flowerBlock);
    }
}
