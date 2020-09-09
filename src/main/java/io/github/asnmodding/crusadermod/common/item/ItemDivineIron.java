package io.github.asnmodding.crusadermod.common.item;

import io.github.asnmodding.crusadermod.CrusaderMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemDivineIron extends Item
{
    public ItemDivineIron()
    {
        setRegistryName(CrusaderMod.MOD_ID, "divine_iron");
        setTranslationKey("divine_iron");
        setCreativeTab(CrusaderMod.CREATIVE_TAB);
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }
}
