package io.github.asnmodding.crusadermod.client;

import io.github.asnmodding.crusadermod.common.ModItems;
import io.github.asnmodding.crusadermod.common.util.RaytraceUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

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
            final EntityCreature entityCreature = RaytraceUtil.getRayTraceEntity(player, 20);
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
