package io.github.asnmodding.crusadermod.common.entity.ai.priest;

import io.github.asnmodding.crusadermod.common.entity.EntityPriest;
import net.minecraft.entity.ai.EntityAIAttackMelee;

public class PriestAIAttack extends EntityAIAttackMelee
{
    public PriestAIAttack(EntityPriest priest, double speedIn, boolean useLongMemory)
    {
        super(priest, speedIn, useLongMemory);
    }

    @Override
    public boolean shouldExecute()
    {
        if (super.attacker.getHealth() <= super.attacker.getMaxHealth() / 2)
            return false;

        return super.shouldExecute();
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        if (super.attacker.getHealth() <= super.attacker.getMaxHealth() / 2)
            return false;

        return super.shouldContinueExecuting();
    }
}
