package io.github.asnmodding.crusadermod.common.entity.ai.priest;

import io.github.asnmodding.crusadermod.common.entity.PriestVillager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class PriestAINearestHealableTarget extends EntityAIBase
{
    private final PriestVillager priest;
    private EntityLivingBase healableTarget;

    public PriestAINearestHealableTarget(PriestVillager priest)
    {
        this.priest = priest;
    }

    @Override
    public boolean shouldExecute()
    {
        this.healableTarget = this.priest.getNearestFriendlyEntity();
        return this.healableTarget != null;
    }

    @Override
    public void startExecuting()
    {
        this.priest.setHealingTarget(this.healableTarget);
        super.startExecuting();
    }

    @Override
    public void resetTask()
    {
        this.healableTarget = null;
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        EntityLivingBase healingTarget = this.priest.getHealingTarget();
        return healingTarget != null && healingTarget.isEntityAlive();
    }
}