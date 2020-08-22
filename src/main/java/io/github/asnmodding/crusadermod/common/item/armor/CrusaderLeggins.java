package io.github.asnmodding.crusadermod.common.item.armor;

import io.github.asnmodding.crusadermod.CrusaderMod;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class CrusaderLeggins extends ItemArmor
{
    public CrusaderLeggins()
    {
        super(ArmorMaterial.valueOf("holy_iron"), 1, EntityEquipmentSlot.LEGS);
        setRegistryName("crusader_leggins");
        setTranslationKey("crusader_leggins");
        setCreativeTab(CrusaderMod.CREATIVE_TAB);
    }

    @Override
    public EntityEquipmentSlot getEquipmentSlot()
    {
        return EntityEquipmentSlot.LEGS;
    }

    //TODO: Remove this when custom texture for Holy Iron Helmet will be added.
    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }
}
