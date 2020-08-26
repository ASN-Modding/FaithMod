package io.github.asnmodding.crusadermod.common;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
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
    public static void onModelRegister(final ModelRegistryEvent event)
    {
        ModItems.registerModels();
        ModBlocks.registerModels();
    }
}
