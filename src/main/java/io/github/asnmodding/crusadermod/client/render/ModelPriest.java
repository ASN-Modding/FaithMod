package io.github.asnmodding.crusadermod.client.render;

import io.github.asnmodding.crusadermod.common.entity.EntityPriest;
import net.minecraft.client.model.ModelIllager;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.AbstractIllager;
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

        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

        this.bipedLeftLegwear.render(scale);
        this.bipedRightLegwear.render(scale);
        this.bipedLeftArmwear.render(scale);
        this.bipedRightArmwear.render(scale);
        this.bipedBodyWear.render(scale);
        this.bipedHead.render(scale);
        this.bipedBody.render(scale);
        this.bipedRightArm.render(scale);
        this.bipedLeftArm.render(scale);
        this.bipedRightLeg.render(scale);
        this.bipedLeftLeg.render(scale);
        this.bipedHeadwear.render(scale);

        EntityPriest entityPriest = (EntityPriest) entityIn;

        GlStateManager.pushMatrix();
        super.bipedHead.rotateAngleY = netHeadYaw * 0.017453292F;
        super.bipedHead.rotateAngleX = headPitch * 0.017453292F;
        super.bipedLeftArm.rotationPointY = 3.0F;
        super.bipedRightArm.rotationPointY = 3.0F;
        this.bipedLeftArm.rotationPointZ = -1.0F;
        this.bipedRightArm.rotationPointZ = -1.0F;
        this.bipedLeftArm.rotateAngleX = -0.75F;
        this.bipedRightArm.rotateAngleX = -0.75F;
        this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.bipedLeftLeg.rotateAngleY = 0.0F;
        this.bipedRightLeg.rotateAngleY = 0.0F;

        if (entityPriest.hasHealingTarget())
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

        GlStateManager.popMatrix();
    }
}
