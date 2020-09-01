package io.github.asnmodding.crusadermod.client.renderer;

import io.github.asnmodding.crusadermod.CrusaderMod;
import io.github.asnmodding.crusadermod.client.model.ModelPriest;
import io.github.asnmodding.crusadermod.common.entity.EntityPriest;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class PriestVillagerRender extends RenderLiving<EntityPriest>
{
    public PriestVillagerRender(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelPriest(), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityPriest entity)
    {
        return new ResourceLocation(CrusaderMod.MOD_ID, "textures/entities/priest.png");
    }
}
