package io.github.asnmodding.crusadermod.common.block.hook;

import io.github.asnmodding.crusadermod.common.ModBlocks;
import io.github.asnmodding.crusadermod.common.ModItems;
import io.github.asnmodding.crusadermod.common.block.BlockBase;
import net.minecraft.block.BlockLever;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGrapplingHook extends BlockBase
{
    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);

    public BlockGrapplingHook()
    {
        super(Material.WOOD, MapColor.WOOD, "block_grappling_hook");
        setLightOpacity(255);
        setCreativeTab(null);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB;
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        for (final EnumFacing facing : EnumFacing.HORIZONTALS)
        {
            BlockPos linePosition = pos.offset(facing).down();
            if(worldIn.getBlockState(linePosition).getBlock() == ModBlocks.BLOCK_GRAPPLING_HOOK_LINE)
            {
                // Destroy entire line
                while (worldIn.getBlockState(linePosition).getBlock() == ModBlocks.BLOCK_GRAPPLING_HOOK_LINE)
                {
                    worldIn.setBlockToAir(linePosition);
                    linePosition = linePosition.down();
                }
                break;
            }
        }
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        drops.add(new ItemStack(ModItems.GRAPPLING_HOOK));
    }
}
