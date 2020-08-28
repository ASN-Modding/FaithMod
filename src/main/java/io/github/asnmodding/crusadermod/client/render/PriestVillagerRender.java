package io.github.asnmodding.crusadermod.client.render;

import io.github.asnmodding.crusadermod.common.entity.PriestVillager;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class PriestVillagerRender extends RenderLiving<PriestVillager>
{
    public PriestVillagerRender(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelPlayer(0.0F, true), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(PriestVillager entity)
    {
        return new ResourceLocation("textures/entity/alex.png");
    }
}
