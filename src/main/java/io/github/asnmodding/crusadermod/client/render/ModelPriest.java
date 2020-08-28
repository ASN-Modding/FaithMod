package io.github.asnmodding.crusadermod.client.render;

import io.github.asnmodding.crusadermod.common.entity.PriestVillager;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelPriest extends ModelPlayer
{
    public ModelPriest()
    {
        super(0.0f, true);
//        super(modelSize, 0.0F, 64, 64);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        PriestVillager priestVillager = (PriestVillager) entityIn;
        if (priestVillager.hasHealingTarget())
        {
            super.bipedRightArm.rotationPointZ = 0.0F;
            this.bipedRightArm.rotationPointX = -5.0F;
            this.bipedLeftArm.rotationPointZ = 0.0F;
            this.bipedLeftArm.rotationPointX = 5.0F;
            this.bipedRightArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
            this.bipedLeftArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
            this.bipedRightArm.rotateAngleZ = 2.3561945F;
            this.bipedLeftArm.rotateAngleZ = -2.3561945F;
            this.bipedRightArm.rotateAngleY = 0.0F;
            this.bipedLeftArm.rotateAngleY = 0.0F;
        }
    }
}
