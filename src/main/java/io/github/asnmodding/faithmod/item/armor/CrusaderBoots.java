package io.github.asnmodding.faithmod.item.armor;

import io.github.asnmodding.faithmod.FaithMod;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class CrusaderBoots extends ArmorItem
{
    public CrusaderBoots()
    {
        super(HolyIronMaterial.getMaterial(), EquipmentSlotType.FEET, new Properties().group(FaithMod.ITEM_GROUP));
        setRegistryName("crusader_boots");
    }

    //TODO: Remove this when custom texture for Holy Iron Helmet will be added.
    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }
}
