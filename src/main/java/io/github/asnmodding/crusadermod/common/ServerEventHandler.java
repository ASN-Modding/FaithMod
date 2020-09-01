package io.github.asnmodding.crusadermod.common;

import com.google.common.base.Predicates;
import io.github.asnmodding.crusadermod.common.entity.EntityPriest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@Mod.EventBusSubscriber
public class ServerEventHandler
{
    // For Priest Staff
    private static EntityCreature lastTargetEntity = null;

    @SubscribeEvent
    public static void onEntitySpawn(final EntityJoinWorldEvent event)
    {
        final Entity entity = event.getEntity();

        // Let's check if the entity comes from monser package.
        if (entity.getClass().getName().contains("net.minecraft.entity.monster"))
        {
            if (entity instanceof EntityCreature && !(entity instanceof EntityEnderman))
            {
                EntityCreature entityCreature = (EntityCreature) entity;
                entityCreature.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(entityCreature, EntityPriest.class, true));
            }
        }
    }
}
