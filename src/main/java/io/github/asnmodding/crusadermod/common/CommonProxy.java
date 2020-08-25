package io.github.asnmodding.crusadermod.common;

import io.github.asnmodding.crusadermod.CrusaderMod;
import io.github.asnmodding.crusadermod.Proxy;
import io.github.asnmodding.crusadermod.common.entity.PriestVillager;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import java.awt.*;

public class CommonProxy implements Proxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        ModBlocks.init();
        ModItems.init();
        ModTileEntities.init();

        // Register entities
        EntityRegistry.registerModEntity(new ResourceLocation("crusadermod", "priest"), PriestVillager.class, "priest", 0, CrusaderMod.INSTANCE, 10, 10, false, 0x000000, 0x5f5f5f);
        EntityRegistry.addSpawn(PriestVillager.class, 1, 1, 1, EnumCreatureType.CREATURE, ForgeRegistries.BIOMES.getValues().toArray(new Biome[0]));
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        ModTileEntities.registerTileEntities();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
