package io.github.asnmodding.crusadermod.common.item.tool;

import io.github.asnmodding.crusadermod.CrusaderMod;
import net.minecraft.item.ItemSword;

public class ItemDivineSword extends ItemSword
{
    public ItemDivineSword()
    {
        super(ToolMaterial.DIAMOND);
        setRegistryName(CrusaderMod.MOD_ID, "divine_sword");
        setTranslationKey("divine_sword");
        setCreativeTab(CrusaderMod.CREATIVE_TAB);
        setMaxDamage(500);
    }
}
