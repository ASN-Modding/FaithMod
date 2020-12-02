package io.github.asnmodding.faithmod.item;

import io.github.asnmodding.faithmod.FaithMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemDivineIron extends Item
{
    public ItemDivineIron()
    {
        super(new Properties().group(FaithMod.ITEM_GROUP));
        setRegistryName(FaithMod.MOD_ID, "divine_iron");
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }
}
