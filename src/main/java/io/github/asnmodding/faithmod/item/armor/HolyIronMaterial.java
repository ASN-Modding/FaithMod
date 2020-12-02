package io.github.asnmodding.faithmod.item.armor;

import io.github.asnmodding.faithmod.item.ModItems;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class HolyIronMaterial implements IArmorMaterial
{
    private static final HolyIronMaterial HOLY_IRON_MATERIAL = new HolyIronMaterial();

    private static final int[] MAX_DAMAGE_ARRAY = new int[] {13, 15, 16, 11};
    private static final int MAX_DAMAGE_FACTOR = 21;

    private final int[] damageReductionAmount = new int[] {2, 6, 7, 2};

    public static IArmorMaterial getMaterial()
    {
        return HOLY_IRON_MATERIAL;
    }

    private HolyIronMaterial()
    {

    }

    @Override
    public int getDurability(EquipmentSlotType slotIn)
    {
        return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * MAX_DAMAGE_FACTOR;
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slotIn)
    {
        return damageReductionAmount[slotIn.getIndex()];
    }

    @Override
    public int getEnchantability()
    {
        return 17;
    }

    @Override
    public SoundEvent getSoundEvent()
    {
        return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
    }

    @Override
    public Ingredient getRepairMaterial()
    {
        return Ingredient.fromItems(ModItems.HOLY_IRON);
    }

    @Override
    public String getName()
    {
        return "holy_iron";
    }

    @Override
    public float getToughness()
    {
        return 1;
    }
}
