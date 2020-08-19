package io.github.asnmodding.crusadermod.common;

import io.github.asnmodding.crusadermod.Proxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy implements Proxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        ModBlocks.init();
        ModItems.init();
        ModTileEntities.init();
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
