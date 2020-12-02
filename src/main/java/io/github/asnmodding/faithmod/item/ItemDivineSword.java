package io.github.asnmodding.faithmod.item;


import io.github.asnmodding.faithmod.FaithMod;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;

public class ItemDivineSword extends SwordItem
{
    public ItemDivineSword()
    {
        super(ItemTier.IRON, 3, -2.4f, new Properties().maxDamage(500).group(FaithMod.ITEM_GROUP));
        setRegistryName(FaithMod.MOD_ID, "divine_sword");
    }
}
