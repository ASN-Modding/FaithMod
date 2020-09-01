package io.github.asnmodding.crusadermod.client;

import com.google.common.base.Predicates;
import io.github.asnmodding.crusadermod.common.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@Mod.EventBusSubscriber
public class ClientEventHandler
{
    private static EntityCreature lastPriestStaffTarget = null;

    @SubscribeEvent
    public static void onClientTick(final TickEvent.ClientTickEvent event)
    {
        final EntityPlayer player = Minecraft.getMinecraft().player;
        final World world = Minecraft.getMinecraft().world;
        if (player == null || world == null)
            return;

        if (player.getHeldItem(EnumHand.MAIN_HAND).getItem() == ModItems.PRIEST_STAFF)
        {
            final EntityCreature entityCreature = getRayTraceEntity(player, 20);
            if (lastPriestStaffTarget == entityCreature)
                return;

            if (lastPriestStaffTarget != null)
            {
                lastPriestStaffTarget.setGlowing(false);
                lastPriestStaffTarget = null;
            }

            if (entityCreature != null)
            {
                lastPriestStaffTarget = entityCreature;
                lastPriestStaffTarget.setGlowing(true);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private static EntityCreature getRayTraceEntity(final Entity sourceEntity, final double distance)
    {
        Vec3d eyesPosition = sourceEntity.getPositionEyes(0);
        Vec3d lookVector = sourceEntity.getLook(1.0F);
        Vec3d traceEnd = eyesPosition.add(lookVector.x * distance, lookVector.y * distance, lookVector.z * distance);
        Entity pointedEntity = null;
        RayTraceResult rayTraceResult = sourceEntity.rayTrace(distance, 0);
        List<Entity> list = sourceEntity.world.getEntitiesInAABBexcluding(sourceEntity, sourceEntity.getEntityBoundingBox().expand(lookVector.x * distance, lookVector.y * distance, lookVector.z * distance).grow(1.0D, 1.0D, 1.0D), Predicates.and(EntitySelectors.NOT_SPECTATING, p_apply_1_ -> p_apply_1_ != null && p_apply_1_.canBeCollidedWith()));

        double d1 = distance;

        if (rayTraceResult != null)
        {
            d1 = rayTraceResult.hitVec.distanceTo(eyesPosition);
        }
        double d2 = d1;

        for (int j = 0; j < list.size(); ++j)
        {
            Entity entity1 = list.get(j);
            AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(entity1.getCollisionBorderSize());
            RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(eyesPosition, traceEnd);

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
                double d3 = eyesPosition.distanceTo(raytraceresult.hitVec);

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
            if (pointedEntity instanceof EntityCreature)
            {
                return (EntityCreature) pointedEntity;
            }
        }
        return null;
    }
}
