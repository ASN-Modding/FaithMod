package io.github.asnmodding.crusadermod.common.item.armor;

import io.github.asnmodding.crusadermod.CrusaderMod;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HolyIronHelmet extends ItemArmor
{
    public HolyIronHelmet()
    {
        super(ArmorMaterial.valueOf("holy_iron"), 1, EntityEquipmentSlot.HEAD);
        setRegistryName("holy_iron_helmet");
        setTranslationKey("holy_iron_helmet");
        setCreativeTab(CrusaderMod.CREATIVE_TAB);
    }

    @Override
    public EntityEquipmentSlot getEquipmentSlot()
    {
        return EntityEquipmentSlot.HEAD;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getDefaultInstance()
    {
        return super.getDefaultInstance();
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }

}
