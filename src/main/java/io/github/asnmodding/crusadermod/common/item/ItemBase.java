package io.github.asnmodding.crusadermod.common.item;

import io.github.asnmodding.crusadermod.CrusaderMod;
import net.minecraft.item.Item;

public class ItemBase extends Item
{
    public ItemBase(final String registryName)
    {
        setRegistryName(CrusaderMod.MOD_ID, registryName);
        setTranslationKey(registryName);
        setCreativeTab(CrusaderMod.CREATIVE_TAB);
    }
}
