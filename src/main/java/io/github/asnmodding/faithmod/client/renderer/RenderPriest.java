package io.github.asnmodding.faithmod.client.renderer;

import io.github.asnmodding.faithmod.FaithMod;
import io.github.asnmodding.faithmod.client.model.ModelPriest;
import io.github.asnmodding.faithmod.entity.EntityPriest;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderPriest extends BipedRenderer<EntityPriest, ModelPriest>
{
    public RenderPriest(EntityRendererManager renderManagerIn)
    {
        super(renderManagerIn, new ModelPriest(), 0.5F);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPriest entity)
    {
        return new ResourceLocation(FaithMod.MOD_ID, "textures/entities/priest.png");
    }
}
