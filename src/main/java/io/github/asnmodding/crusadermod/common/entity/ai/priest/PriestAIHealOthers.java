package io.github.asnmodding.crusadermod.common.entity.ai.priest;

import io.github.asnmodding.crusadermod.common.entity.PriestVillager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class PriestAIHealOthers extends EntityAIBase
{
    private final PriestVillager priest;
    private int healInterval = 60;
    private int healTime;
    private EntityLivingBase healingTarget;

    public PriestAIHealOthers(PriestVillager priest)
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

        if (this.priest.getDistanceSq(this.healingTarget) < 12.0d)
        {
            this.priest.getNavigator().clearPath();
        }
        else
        {
            this.priest.getNavigator().tryMoveToEntityLiving(this.healingTarget, 0.6);
        }

        if (this.healingTarget.getDistanceSq(this.priest) <= 10D)
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