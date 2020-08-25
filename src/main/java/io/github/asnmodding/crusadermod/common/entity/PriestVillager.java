package io.github.asnmodding.crusadermod.common.entity;

import com.google.common.base.Predicate;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntitySpellcasterIllager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.world.World;

import java.util.List;


public class PriestVillager extends EntityCreature implements IRangedAttackMob
{
    private static final Predicate<Entity> UNDEAD = entity -> entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD && ((EntityLivingBase)entity).attackable();
    private static final Predicate<Entity> FRIENDLIES = entity -> (entity instanceof EntityVillager || entity instanceof EntityPlayer) && ((EntityLivingBase)entity).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD;

    private Village village;
    private BlockPos home;

    private int randomTickDivider;

    public PriestVillager(World worldIn)
    {
        super(worldIn);
        setCustomNameTag("Priest");
        setAlwaysRenderNameTag(true);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

//        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.);
    }

    @Override
    protected void initEntityAI()
    {
//        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));

        this.tasks.addTask(1, new EntityAIHeal(this));
        this.tasks.addTask(2, new EntityAIHealOthers(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1, false));
//        this.tasks.addTask(2, new EntityAIAttackRanged(this, 0.5D, 60, 10.F));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F, 0.2F));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(3, new EntityAILookIdle(this));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityLiving.class, 2, true, true, UNDEAD));
    }

    @Override
    public boolean getCanSpawnHere()
    {
        final BlockPos blockPos = new BlockPos(this);
        final Village village = super.world.getVillageCollection().getNearestVillage(blockPos, 32);
        if (village == null)
            return false;
        else return true;
    }

    @Override
    protected void updateAITasks()
    {
        if (--this.randomTickDivider <= 0)
        {
            BlockPos blockpos = new BlockPos(this);
            this.world.getVillageCollection().addToVillagerPositionList(blockpos);
            this.randomTickDivider = 70 + this.rand.nextInt(50);
            this.village = this.world.getVillageCollection().getNearestVillage(blockpos, 32);
        }
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
//        EntityArrow entityarrow = this.createArrowEntity(distanceFactor);
//        if (this.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemBow)
//            entityarrow = ((net.minecraft.item.ItemBow) this.getHeldItemMainhand().getItem()).customizeArrow(entityarrow);
//        double d0 = target.posX - this.posX;
//        double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 3.0F) - entityarrow.posY;
//        double d2 = target.posZ - this.posZ;
//        double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
//        entityarrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (float)(14 - this.world.getDifficulty().getId() * 4));
//        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
//        this.world.spawnEntity(entityarrow);
    }

    @Override
    public void setSwingingArms(boolean swingingArms)
    {

    }

//    protected void initEntityAI()
//    {
//        this.tasks.addTask(0, new EntityAISwimming(this));
//        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
//        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityEvoker.class, 12.0F, 0.8D, 0.8D));
//        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityVindicator.class, 8.0F, 0.8D, 0.8D));
//        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityVex.class, 8.0F, 0.6D, 0.6D));
//        this.tasks.addTask(1, new EntityAITradePlayer(this));
//        this.tasks.addTask(1, new EntityAILookAtTradePlayer(this));
//        this.tasks.addTask(2, new EntityAIMoveIndoors(this));
//        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
//        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
//        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
//        this.tasks.addTask(6, new EntityAIVillagerMate(this));
//        this.tasks.addTask(7, new EntityAIFollowGolem(this));
//        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
//        this.tasks.addTask(9, new EntityAIVillagerInteract(this));
//        this.tasks.addTask(9, new EntityAIWanderAvoidWater(this, 0.6D));
//        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
//    }

    public void healEntity(EntityLivingBase entity)
    {
        setSwingingArms(true);

        // Spawn heal particles
        this.world.spawnParticle(EnumParticleTypes.HEART, entity.posX, entity.posY, entity.posZ, 0.5, 0.5, 0.5);

        // Apply heal potion effect
        entity.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 0));
    }

    private EntityLivingBase getNearestFriendlyEntity()
    {
        // Friendly entities = Villagers, Players, Crusaders.

        // Creating new variable here is unnecessary but we'll keep it here for now for brevity sake.
        int distance = 12;
        List<EntityLivingBase> entities = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(distance, 4.0D, distance), FRIENDLIES);

        if (entities.isEmpty())
            return null;

        //Sorts by distance.
        entities.sort(new EntityAINearestAttackableTarget.Sorter(this));
        return entities.get(0);
    }

    public static class EntityAIHeal extends EntityAIBase
    {
        private final PriestVillager priest;

        public EntityAIHeal(final PriestVillager priest)
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
            // Play heal sound
            this.priest.playSound(SoundEvents.ENTITY_ILLAGER_CAST_SPELL, 1.0F, 1.0F);
        }

        @Override
        public void updateTask()
        {
            this.priest.healEntity(this.priest);
        }
    }

    public static class EntityAIHealOthers extends EntityAIBase
    {
        private final PriestVillager priest;

        private EntityLivingBase nearestWoundedFriendlyEntity;

        public EntityAIHealOthers(PriestVillager priest)
        {
            this.priest = priest;
            setMutexBits(3);
        }

        @Override
        public boolean shouldExecute()
        {
            this.nearestWoundedFriendlyEntity = this.priest.getNearestFriendlyEntity();
            return this.nearestWoundedFriendlyEntity != null;
        }

        @Override
        public void startExecuting()
        {
        }

        @Override
        public boolean shouldContinueExecuting()
        {
            return this.nearestWoundedFriendlyEntity.getHealth() < this.nearestWoundedFriendlyEntity.getMaxHealth();
        }

        @Override
        public void updateTask()
        {
            this.priest.getLookHelper().setLookPositionWithEntity(this.priest, (float)(this.priest.getHorizontalFaceSpeed() + 20), (float)this.priest.getVerticalFaceSpeed());

            if (this.priest.getDistanceSq(this.priest) < 12.0d)
            {
                this.priest.getNavigator().clearPath();
            }
            else
            {
                this.priest.getNavigator().tryMoveToEntityLiving(this.nearestWoundedFriendlyEntity, 1.15);
            }

            if (this.nearestWoundedFriendlyEntity.getDistanceSq(this.priest) <= 4D)
            {
                this.priest.healEntity(this.nearestWoundedFriendlyEntity);
            }
        }
    }
}
