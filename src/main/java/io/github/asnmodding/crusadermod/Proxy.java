package io.github.asnmodding.crusadermod;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface Proxy
{
    void preInit(final FMLPreInitializationEvent event);

    void init(final FMLInitializationEvent event);

    void postInit(final FMLPostInitializationEvent event);
}
