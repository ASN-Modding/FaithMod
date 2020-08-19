package io.github.asnmodding.crusadermod.common.item;

import io.github.asnmodding.crusadermod.CrusaderMod;
import net.minecraft.item.Item;

public class ItemBase extends Item
{
    public ItemBase(final String registryName)
    {
        setRegistryName(registryName);
        setTranslationKey(registryName);
        setCreativeTab(CrusaderMod.CREATIVE_TAB);
    }
}
