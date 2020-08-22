package io.github.asnmodding.crusadermod.common.item.armor;

import io.github.asnmodding.crusadermod.CrusaderMod;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class CrusaderBoots extends ItemArmor
{
    public CrusaderBoots()
    {
        super(ArmorMaterial.valueOf("holy_iron"), 1, EntityEquipmentSlot.FEET);
        setRegistryName("crusader_boots");
        setTranslationKey("crusader_boots");
        setCreativeTab(CrusaderMod.CREATIVE_TAB);
    }

    @Override
    public EntityEquipmentSlot getEquipmentSlot()
    {
        return EntityEquipmentSlot.FEET;
    }

    //TODO: Remove this when custom texture for Holy Iron Helmet will be added.
    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }
}
