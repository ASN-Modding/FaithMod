package io.github.asnmodding.faithmod.entity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.IForgeRegistry;

public class ModEntityTypes
{
    public static final EntityType<EntityPriest> PRIEST_ENTITY_TYPE = EntityType.Builder
            .create(EntityPriest::new, EntityClassification.MISC)
            .size(0.6F, 1.95F)
            .build(EntityPriest.REGISTRY_NAME);

    public static void registerEntityTypes(final IForgeRegistry<EntityType<?>> registry)
    {
        registry.register(PRIEST_ENTITY_TYPE.setRegistryName(EntityPriest.REGISTRY_NAME));
    }
}
