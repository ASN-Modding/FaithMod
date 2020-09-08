package io.github.asnmodding.crusadermod.client;

import io.github.asnmodding.crusadermod.client.renderer.RenderGrapplingHook;
import io.github.asnmodding.crusadermod.client.renderer.RenderPriest;
import io.github.asnmodding.crusadermod.common.CommonProxy;
import io.github.asnmodding.crusadermod.common.ModBlocks;
import io.github.asnmodding.crusadermod.common.ModItems;
import io.github.asnmodding.crusadermod.common.ModTileEntities;
import io.github.asnmodding.crusadermod.common.entity.EntityPriest;
import io.github.asnmodding.crusadermod.common.entity.hook.EntityGrapplingHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);

//        ModBlocks.init();
//        ModItems.init();
//        ModTileEntities.init();

        // Register Render
        RenderingRegistry.registerEntityRenderingHandler(EntityPriest.class, RenderPriest::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGrapplingHook.class, RenderGrapplingHook::new);
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);

//        ModItems.registerModels();
//        ModBlocks.registerModels();
        ModTileEntities.registerModels();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);

    }
}
