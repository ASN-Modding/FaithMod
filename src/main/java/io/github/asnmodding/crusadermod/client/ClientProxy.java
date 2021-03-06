package io.github.asnmodding.crusadermod.client;

import io.github.asnmodding.crusadermod.common.CommonProxy;
import io.github.asnmodding.crusadermod.common.ModBlocks;
import io.github.asnmodding.crusadermod.common.ModTileEntities;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);

        ModBlocks.init();
//        ModItems.init();
        ModTileEntities.init();
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);

//        ModItems.registerModels();
        ModBlocks.registerModels();
        ModTileEntities.registerModels();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);

    }
}
