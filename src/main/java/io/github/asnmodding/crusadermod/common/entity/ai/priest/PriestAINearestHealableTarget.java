package io.github.asnmodding.crusadermod.common.entity.ai.priest;

import io.github.asnmodding.crusadermod.common.entity.EntityPriest;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class PriestAINearestHealableTarget extends EntityAIBase
{
    private final EntityPriest priest;
    private EntityLivingBase healableTarget;

    public PriestAINearestHealableTarget(EntityPriest priest)
    {
        this.priest = priest;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute()
    {
        this.healableTarget = this.priest.getNearestWoundedFriendlyEntity();
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
//        this.priest.setHealingTarget(null);
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        EntityLivingBase healingTarget = this.priest.getHealingTarget();
        return healingTarget != null && healingTarget.isEntityAlive();
    }
}