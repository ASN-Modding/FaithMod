package io.github.asnmodding.crusadermod.common.entity.ai.priest;

import io.github.asnmodding.crusadermod.common.entity.PriestVillager;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.EnumParticleTypes;

public class PriestAIHeal extends EntityAIBase
{
    private final PriestVillager priest;

    private int healInterval = 60;
    private int healTime;

    public PriestAIHeal(final PriestVillager priest)
    {
        this.priest = priest;
        setMutexBits(3);
    }

    @Override
    public boolean shouldExecute()
    {
        return this.priest.getHealth() < this.priest.getMaxHealth();
    }

    @Override
    public void startExecuting()
    {
        this.priest.setHealingTarget(this.priest);

        // Play heal sound
//            this.priest.playSound(SoundEvents.ENTITY_ILLAGER_CAST_SPELL, 1.0F, 1.0F);
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        if (this.priest.getHealth() < this.priest.getMaxHealth())
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
    public void updateTask()
    {
        this.priest.startHealing();

        if (--this.healTime <= 0)
        {
            this.priest.healHealingTarget();
            this.healTime = this.healInterval;
        }
    }
}
