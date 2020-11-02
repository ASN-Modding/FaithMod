package io.github.asnmodding.faithmod.registry;

import io.github.asnmodding.faithmod.FaithMod;
import io.github.asnmodding.faithmod.world.gen.MysticalFlowerFeature;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = FaithMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WorldGenRegistry
{
    public static MysticalFlowerFeature MYSTICAL_FLOWER_FEATURE = new MysticalFlowerFeature();

    //          biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(DEFAULT_FLOWER_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(2))));

    @SubscribeEvent
    public static void onFeatureRegister(RegistryEvent.Register<Feature<?>> registryEvent)
    {
        if (registryEvent.getName().equals(ForgeRegistries.FEATURES.getRegistryName()))
        {
            MYSTICAL_FLOWER_FEATURE.setRegistryName("mystical_flower_feature");
            registryEvent.getRegistry().register(MYSTICAL_FLOWER_FEATURE);
            Biomes.PLAINS.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, MYSTICAL_FLOWER_FEATURE.withConfiguration(MysticalFlowerFeature.DEFAULT_MYSTICAL_FLOWER_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(2))));
        }
    }
}
