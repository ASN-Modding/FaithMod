package io.github.asnmodding.crusadermod.common.entity.ai.priest;

import io.github.asnmodding.crusadermod.common.entity.EntityPriest;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class PriestAIHealOthers extends EntityAIBase
{
    private final EntityPriest priest;
    private int healInterval = 60;
    private int healTime;
    private EntityLivingBase healingTarget;

    public PriestAIHealOthers(EntityPriest priest)
    {
        this.priest = priest;
        setMutexBits(3);
    }

    @Override
    public boolean shouldExecute()
    {
        return this.priest.hasHealingTarget();
    }

    @Override
    public void startExecuting()
    {
        this.healingTarget = this.priest.getHealingTarget();
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        if (this.healingTarget.getHealth() < this.healingTarget.getMaxHealth() && this.healingTarget.isEntityAlive())
        {
            return true;
        }
        else
        {
            this.priest.setHealingTarget(null);
            return false;
        }
    }

    @Override
    public void resetTask()
    {
        this.healTime = 0;
        this.healingTarget = null;
    }

    @Override
    public void updateTask()
    {
        if (this.healingTarget == null)
            return;

        this.priest.getLookHelper().setLookPositionWithEntity(this.healingTarget, (float)(this.priest.getHorizontalFaceSpeed() + 20), (float)this.priest.getVerticalFaceSpeed());

        double distance = this.priest.getDistanceSq(this.healingTarget);

        if (distance < 10)
        {
            this.priest.getNavigator().clearPath();
        }
        else
        {
            this.priest.getNavigator().tryMoveToEntityLiving(this.healingTarget, 0.6);
        }

        if (distance <= 10D)
        {
            this.priest.startHealing();

            if (--this.healTime <= 0)
            {
                this.priest.healHealingTarget();
                this.healTime = this.healInterval;
            }
        }
    }
}