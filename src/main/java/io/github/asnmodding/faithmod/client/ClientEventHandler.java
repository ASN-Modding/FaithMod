package io.github.asnmodding.faithmod.client;

import io.github.asnmodding.faithmod.item.ModItems;
import io.github.asnmodding.faithmod.util.RaytraceUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ClientEventHandler
{
    private static CreatureEntity lastPriestStaffTarget = null;

    @SubscribeEvent
    public static void onClientTick(final TickEvent.ClientTickEvent event)
    {
        final PlayerEntity player = Minecraft.getInstance().player;
        final World world = Minecraft.getInstance().world;
        if (player == null || world == null)
            return;

        if (player.getHeldItem(Hand.MAIN_HAND).getItem() == ModItems.PRIEST_STAFF)
        {
            final CreatureEntity entityCreature = RaytraceUtil.getRayTraceEntity(player, 20);
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
}
