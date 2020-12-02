package io.github.asnmodding.faithmod.util;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public final class RaytraceUtil
{
    // Copied mostly from vanilla EntityRenderer

    /**
     * Gets entity that exists in look ray with a specified distance.
     * @param sourceEntity the source entity (usually a player)
     * @param distance the distance to check
     * @return the entity.
     */
    @OnlyIn(Dist.CLIENT)
    public static CreatureEntity getRayTraceEntity(final Entity sourceEntity, final double distance)
    {
        Vec3d eyesPosition = sourceEntity.getEyePosition(0);
        Vec3d lookVector = sourceEntity.getLook(1.0F);
        Vec3d traceEnd = eyesPosition.add(lookVector.x * distance, lookVector.y * distance, lookVector.z * distance);
        Entity pointedEntity = null;
        RayTraceResult rayTraceResult = ProjectileHelper.rayTrace(sourceEntity, sourceEntity.getBoundingBox(), entity -> true, RayTraceContext.BlockMode.COLLIDER, true);
//        RayTraceResult rayTraceResult = sourceEntity.getEntityWorld().rayTraceBlocks(eyesPosition, traceEnd, sourceEntity.getPosition(), VoxelShapes.empty() distance, 0);
        List<Entity> list = sourceEntity.world.getEntitiesInAABBexcluding(sourceEntity, sourceEntity.getBoundingBox().expand(lookVector.x * distance, lookVector.y * distance, lookVector.z * distance).grow(1.0D, 1.0D, 1.0D), ((Predicate<Entity>) Entity::isSpectator).and(p_apply_1_ -> p_apply_1_ != null && p_apply_1_.canBeCollidedWith()));

        double d1 = distance;

        if (rayTraceResult != null)
        {
            d1 = rayTraceResult.getHitVec().distanceTo(eyesPosition);
        }
        double d2 = d1;

        for (int j = 0; j < list.size(); ++j)
        {
            Entity entity1 = list.get(j);
            AxisAlignedBB axisalignedbb = entity1.getBoundingBox().grow(entity1.getCollisionBorderSize());
            RayTraceResult raytraceresult = rayTrace(Arrays.asList(axisalignedbb), eyesPosition, traceEnd, entity1.getPosition());

            if (axisalignedbb.contains(eyesPosition))
            {
                if (d2 >= 0.0D)
                {
                    pointedEntity = entity1;
                    d2 = 0.0D;
                }
            }
            else if (raytraceresult != null)
            {
                double d3 = eyesPosition.distanceTo(raytraceresult.getHitVec());

                if (d3 < d2 || d2 == 0.0D)
                {
                    if (entity1.getLowestRidingEntity() == sourceEntity.getLowestRidingEntity() && !entity1.canRiderInteract())
                    {
                        if (d2 == 0.0D)
                        {
                            pointedEntity = entity1;
                        }
                    }
                    else
                    {
                        pointedEntity = entity1;
                        d2 = d3;
                    }
                }
            }
        }

        if (pointedEntity != null && (d2 < d1 || rayTraceResult == null))
        {
            if (pointedEntity instanceof CreatureEntity)
            {
                return (CreatureEntity) pointedEntity;
            }
        }
        return null;
    }

    private static RayTraceResult rayTrace(Iterable<AxisAlignedBB> boxes, Vec3d start, Vec3d end, BlockPos pos)
    {
        double[] adouble = new double[]{1.0D};
        Direction direction = null;
        double d0 = end.x - start.x;
        double d1 = end.y - start.y;
        double d2 = end.z - start.z;

        for(AxisAlignedBB axisalignedbb : boxes) {
            direction = func_197741_a(axisalignedbb.offset(pos), start, adouble, direction, d0, d1, d2);
        }

        if (direction == null) {
            return null;
        } else {
            double d3 = adouble[0];
            return new BlockRayTraceResult(start.add(d3 * d0, d3 * d1, d3 * d2), direction, pos, false);
        }
    }

    @Nullable
    private static Direction func_197741_a(AxisAlignedBB aabb, Vec3d p_197741_1_, double[] p_197741_2_, @Nullable Direction facing, double p_197741_4_, double p_197741_6_, double p_197741_8_) {
        if (p_197741_4_ > 1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_4_, p_197741_6_, p_197741_8_, aabb.minX, aabb.minY, aabb.maxY, aabb.minZ, aabb.maxZ, Direction.WEST, p_197741_1_.x, p_197741_1_.y, p_197741_1_.z);
        } else if (p_197741_4_ < -1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_4_, p_197741_6_, p_197741_8_, aabb.maxX, aabb.minY, aabb.maxY, aabb.minZ, aabb.maxZ, Direction.EAST, p_197741_1_.x, p_197741_1_.y, p_197741_1_.z);
        }

        if (p_197741_6_ > 1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_6_, p_197741_8_, p_197741_4_, aabb.minY, aabb.minZ, aabb.maxZ, aabb.minX, aabb.maxX, Direction.DOWN, p_197741_1_.y, p_197741_1_.z, p_197741_1_.x);
        } else if (p_197741_6_ < -1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_6_, p_197741_8_, p_197741_4_, aabb.maxY, aabb.minZ, aabb.maxZ, aabb.minX, aabb.maxX, Direction.UP, p_197741_1_.y, p_197741_1_.z, p_197741_1_.x);
        }

        if (p_197741_8_ > 1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_8_, p_197741_4_, p_197741_6_, aabb.minZ, aabb.minX, aabb.maxX, aabb.minY, aabb.maxY, Direction.NORTH, p_197741_1_.z, p_197741_1_.x, p_197741_1_.y);
        } else if (p_197741_8_ < -1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_8_, p_197741_4_, p_197741_6_, aabb.maxZ, aabb.minX, aabb.maxX, aabb.minY, aabb.maxY, Direction.SOUTH, p_197741_1_.z, p_197741_1_.x, p_197741_1_.y);
        }

        return facing;
    }

    @Nullable
    private static Direction func_197740_a(double[] p_197740_0_, @Nullable Direction p_197740_1_, double p_197740_2_, double p_197740_4_, double p_197740_6_, double p_197740_8_, double p_197740_10_, double p_197740_12_, double p_197740_14_, double p_197740_16_, Direction p_197740_18_, double p_197740_19_, double p_197740_21_, double p_197740_23_) {
        double d0 = (p_197740_8_ - p_197740_19_) / p_197740_2_;
        double d1 = p_197740_21_ + d0 * p_197740_4_;
        double d2 = p_197740_23_ + d0 * p_197740_6_;
        if (0.0D < d0 && d0 < p_197740_0_[0] && p_197740_10_ - 1.0E-7D < d1 && d1 < p_197740_12_ + 1.0E-7D && p_197740_14_ - 1.0E-7D < d2 && d2 < p_197740_16_ + 1.0E-7D) {
            p_197740_0_[0] = d0;
            return p_197740_18_;
        } else {
            return p_197740_1_;
        }
    }

    private RaytraceUtil()
    {

    }
}
