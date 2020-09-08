package io.github.asnmodding.crusadermod.common.entity.hook;

import io.github.asnmodding.crusadermod.common.ModBlocks;
import io.github.asnmodding.crusadermod.common.ModItems;
import net.minecraft.block.BlockLadder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGrapplingHook extends EntityThrowable
{
    public EntityGrapplingHook(World worldIn)
    {
        super(worldIn);
    }

    public EntityGrapplingHook(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    @SideOnly(Side.CLIENT)
    public EntityGrapplingHook(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
        if (!this.world.isRemote)
        {
            setDead();

            if (result.typeOfHit != RayTraceResult.Type.BLOCK)
            {
                dropGrapplingHookItem();
                return;
            }

            final BlockPos hitBlockPos = result.getBlockPos();
            BlockPos grapplingHookBlockPos = hitBlockPos.up();
            final EnumFacing hitSide = result.sideHit;
            final EnumFacing preferredRopeFacing = this.thrower.getHorizontalFacing().getOpposite();

            if (hitSide == EnumFacing.DOWN)
            {
                dropGrapplingHookItem();
            }
            else if (hitSide == EnumFacing.UP)
            {
                // Spawn hook block at hook impact position
                world.setBlockState(grapplingHookBlockPos, ModBlocks.BLOCK_GRAPPLING_HOOK.getDefaultState());

                final EnumFacing ropeFacing = getRopeFacing(world, grapplingHookBlockPos, preferredRopeFacing);
                if (ropeFacing == null)
                    return; //No rope

                final BlockPos ropeStartBlockPos = grapplingHookBlockPos.offset(ropeFacing).down();
                spawnRope(world, ropeStartBlockPos, ropeFacing);
            }
            else
            {
                BlockPos blockUnderGrapplingHookBlock = hitBlockPos.offset(hitSide).down();
                if (world.isAirBlock(blockUnderGrapplingHookBlock))
                {
                    dropGrapplingHookItem();
                }
                else
                {
                    grapplingHookBlockPos = blockUnderGrapplingHookBlock.up();
                    world.setBlockState(grapplingHookBlockPos, ModBlocks.BLOCK_GRAPPLING_HOOK.getDefaultState());

                    final EnumFacing ropeFacing = getRopeFacing(world, grapplingHookBlockPos, preferredRopeFacing);
                    if (ropeFacing == null)
                    {
                        this.setDead();
                        return; //No rope
                    }

                    final BlockPos ropeStartBlockPos = grapplingHookBlockPos.offset(ropeFacing).down();
                    spawnRope(world, ropeStartBlockPos, ropeFacing);
                }
            }
        }
    }

    private EnumFacing getRopeFacing(final World world, final BlockPos grapplingHookBlockPos, final EnumFacing preferredRopeFacing)
    {
        BlockPos blockPos = grapplingHookBlockPos.offset(preferredRopeFacing).down();
        if (world.isAirBlock(blockPos))
            return preferredRopeFacing;

        for (final EnumFacing facing : EnumFacing.Plane.HORIZONTAL)
        {
            blockPos = grapplingHookBlockPos.offset(facing).down();
            if (world.isAirBlock(blockPos))
                return facing;
        }
        return null;
    }

    private void spawnRope(final World world, final BlockPos startRopePos, EnumFacing facing)
    {
        final int ropeLength = 12;
        BlockPos ropePos = startRopePos;

        for (int i = 0; i < ropeLength; i++)
        {
            if (!world.isAirBlock(ropePos))
                break;

            world.setBlockState(ropePos, ModBlocks.BLOCK_GRAPPLING_HOOK_LINE.getDefaultState().withProperty(BlockLadder.FACING, facing));
            ropePos = ropePos.down();
        }
    }

    private void dropGrapplingHookItem()
    {
        final EntityItem entityItem = new EntityItem(world, this.posX, this.posY, this.posZ, new ItemStack(ModItems.GRAPPLING_HOOK));
        entityItem.setDefaultPickupDelay();
        world.spawnEntity(entityItem);
    }
}
