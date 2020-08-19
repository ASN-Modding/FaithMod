package io.github.asnmodding.crusadermod;

import io.github.asnmodding.crusadermod.common.ModItems;
import io.github.asnmodding.crusadermod.common.RegistryHandler;
import io.github.asnmodding.crusadermod.common.item.ItemHolyWater;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = CrusaderMod.MOD_ID, name = CrusaderMod.MOD_NAME, version = CrusaderMod.VERSION, acceptedMinecraftVersions = "[1.12.2]")
public class CrusaderMod
{
    public static final String MOD_ID = "crusadermod";
    public static final String MOD_NAME = "Crusader Mod";
    public static final String VERSION = "1.0-SNAPSHOT";

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static CrusaderMod INSTANCE;

    @SidedProxy(clientSide = "io.github.asnmodding.crusadermod.client.ClientProxy", serverSide = "io.github.asnmodding.crusadermod.common.CommonProxy", modId = MOD_ID)
    public static Proxy PROXY;

    public static final CreativeTabs CREATIVE_TAB = new CrusaderModCreativeTab();

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(RegistryHandler.class);

        PROXY.preInit(event);
    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        PROXY.init(event);
    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event)
    {
        PROXY.postInit(event);
    }

    private static final class CrusaderModCreativeTab extends CreativeTabs
    {
        public CrusaderModCreativeTab()
        {
            super("crusader_mod");
        }

        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(ModItems.HOLY_WATER);
        }
    }
}
