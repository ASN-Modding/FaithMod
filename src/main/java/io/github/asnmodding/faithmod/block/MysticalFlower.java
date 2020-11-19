package io.github.asnmodding.faithmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.potion.Effects;

public class MysticalFlower extends FlowerBlock
{
//    public static final Block DANDELION = register("dandelion", new FlowerBlock(Effects.SATURATION, 7, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));

    public MysticalFlower()
    {
        super(Effects.GLOWING, 10, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0).sound(SoundType.PLANT));
        setRegistryName("mystical_flower");
    }
}
//    HAHA śmieszny komentarz looooooooooooooooooooool