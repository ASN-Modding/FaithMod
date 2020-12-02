package io.github.asnmodding.faithmod;

import io.github.asnmodding.faithmod.block.ModBlocks;
import io.github.asnmodding.faithmod.client.renderer.RenderPriest;
import io.github.asnmodding.faithmod.entity.ModEntityTypes;
import io.github.asnmodding.faithmod.item.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FaithMod.MOD_ID)
public class FaithMod
{
    public static final String MOD_ID = "faithmod";

    public static final ItemGroup ITEM_GROUP = new ItemGroup(FaithMod.MOD_ID)
    {
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(ModItems.HOLY_WATER);
        }
    };

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public FaithMod() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setupCommon(final FMLCommonSetupEvent event)
    {
        // Add common setup code here...

    }

    private void setupClient(final FMLClientSetupEvent event)
    {
        registerRenderers();
    }

    private void registerRenderers()
    {
        final EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        entityRendererManager.register(ModEntityTypes.PRIEST_ENTITY_TYPE, new RenderPriest(entityRendererManager));
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
}
