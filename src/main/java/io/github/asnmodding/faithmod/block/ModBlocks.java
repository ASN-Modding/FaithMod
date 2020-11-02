package io.github.asnmodding.faithmod.block;

import net.minecraft.block.Block;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks
{
    public static final MysticalFlower MYSTICAL_FLOWER = new MysticalFlower();

    public static void registerBlocks(final IForgeRegistry<Block> registry)
    {
        registry.register(MYSTICAL_FLOWER);
    }
}
