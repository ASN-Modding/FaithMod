package io.github.asnmodding.faithmod.registry;

import io.github.asnmodding.faithmod.FaithMod;
import io.github.asnmodding.faithmod.block.ModBlocks;
import io.github.asnmodding.faithmod.entity.ModEntityTypes;
import io.github.asnmodding.faithmod.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FaithMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryHandler
{
    @SubscribeEvent
    public static void onBlockRegister(final RegistryEvent.Register<Block> event)
    {
        ModBlocks.registerBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void onItemRegister(final RegistryEvent.Register<Item> event)
    {
        ModItems.registerItems(event.getRegistry());
    }

    @SubscribeEvent
    public static void onEntityTypeRegister(final RegistryEvent.Register<EntityType<?>> event)
    {
        ModEntityTypes.registerEntityTypes(event.getRegistry());
    }
}
