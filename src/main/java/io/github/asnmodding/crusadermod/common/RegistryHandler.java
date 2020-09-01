package io.github.asnmodding.crusadermod.common;

import io.github.asnmodding.crusadermod.CrusaderMod;
import io.github.asnmodding.crusadermod.common.entity.EntityPriest;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod.EventBusSubscriber
public class RegistryHandler
{
    private static int lastEntityId;

    public static int getNextEntityId()
    {
        return lastEntityId++;
    }

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

    @SubscribeEvent
    public static void onEntityRegister(final RegistryEvent.Register<EntityEntry> event)
    {
        EntityEntry priestEntityEntry = EntityEntryBuilder
                .create()
                .id(new ResourceLocation(CrusaderMod.MOD_ID, "priest"), getNextEntityId())
                .name("priest")
                .entity(EntityPriest.class)
                .tracker(64, 1, true)
                .egg(0xFFFFFF, 0xF5F5F5)
                .spawn(EnumCreatureType.CREATURE, 1, 1, 10, ForgeRegistries.BIOMES.getValues().toArray(new Biome[0]))
                .build();

        event.getRegistry().register(priestEntityEntry);
    }
}
