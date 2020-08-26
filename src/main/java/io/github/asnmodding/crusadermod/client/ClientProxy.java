package io.github.asnmodding.crusadermod.client;

import io.github.asnmodding.crusadermod.client.render.PriestVillagerRender;
import io.github.asnmodding.crusadermod.common.CommonProxy;
import io.github.asnmodding.crusadermod.common.ModBlocks;
import io.github.asnmodding.crusadermod.common.ModTileEntities;
import io.github.asnmodding.crusadermod.common.entity.PriestVillager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
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

        ModBlocks.init();
//        ModItems.init();
        ModTileEntities.init();

        // Register Render
        RenderingRegistry.registerEntityRenderingHandler(PriestVillager.class, PriestVillagerRender::new);
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
