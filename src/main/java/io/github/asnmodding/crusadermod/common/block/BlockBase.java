package io.github.asnmodding.crusadermod.common.block;

import io.github.asnmodding.crusadermod.CrusaderMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockBase extends Block
{
    public BlockBase(Material blockMaterial, MapColor blockMapColor, String registryName)
    {
        super(blockMaterial, blockMapColor);
        setRegistryName(registryName);
        setTranslationKey(registryName);
        setCreativeTab(CrusaderMod.CREATIVE_TAB);
    }
}