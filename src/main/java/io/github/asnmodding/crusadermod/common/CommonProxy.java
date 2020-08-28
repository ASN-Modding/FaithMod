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
import net.minecraftforge.fml.common.registry.*;

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

//        EntityEntryBuilder.create()
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
