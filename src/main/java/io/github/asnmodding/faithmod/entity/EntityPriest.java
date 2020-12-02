package io.github.asnmodding.faithmod.entity;

import com.google.common.base.Predicate;
import io.github.asnmodding.faithmod.item.ModItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;


public class EntityPriest extends CreatureEntity implements IRangedAttackMob
{
    public static final String REGISTRY_NAME = "priest";

    private static final Predicate<LivingEntity> UNDEAD = entity -> entity != null && ((entity).getCreatureAttribute() == CreatureAttribute.UNDEAD || entity instanceof ZombieEntity || entity instanceof SpiderEntity) && entity.attackable();
    private static final Predicate<LivingEntity> WOUNDED_FRIENDLIES = entity ->
    {
        if (!(entity instanceof VillagerEntity) && !(entity instanceof PlayerEntity))
            return false;

        if (entity.getCreatureAttribute() == CreatureAttribute.UNDEAD)
            return false;

        return Float.compare(entity.getHealth(), entity.getMaxHealth()) < 0;
    };

    private static final DataParameter<Integer> HEALING_ENTITY_ID = EntityDataManager.createKey(EntityPriest.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> SWINGING_ARMS = EntityDataManager.createKey(EntityPriest.class, DataSerializers.BOOLEAN);

//    private Village village;

//    private BlockPos home;

    private LivingEntity healingTarget;

//    private boolean isHealing;

//    private int randomTickDivider;

    private double angle = 0;
    private final double angleIncrement = 2.0 / 28.0 * Math.PI;

    private final byte START_HEALING_STATE = 1;
    private final byte HEALING_STATE = 2;

    protected EntityPriest(EntityType<? extends CreatureEntity> type, World worldIn)
    {
        super(type, worldIn);
//        ((PathNavigateGround)this.getNavigator()).setBreakDoors(true);
        this.dataManager.register(HEALING_ENTITY_ID, 0);
        this.dataManager.register(SWINGING_ARMS, false);
    }

    @Override
    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new SwimGoal(this));

//        this.tasks.addTask(0, new EntityAISwimming(this));
//        this.tasks.addTask(1, new PriestAIAttack(this, 0.6, true));
//        this.tasks.addTask(2, new PriestAIHeal(this));
//        this.tasks.addTask(2, new PriestAIHealOthers(this));
//        this.tasks.addTask(3, new EntityAIMoveIndoors(this));
//        this.tasks.addTask(3, new EntityAIMoveTowardsTarget(this, 0.6D, 32.0F));
//        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F, 0.2F));
//        this.tasks.addTask(4, new EntityAIRestrictOpenDoor(this));
//        this.tasks.addTask(4, new EntityAIMoveThroughVillage(this, 0.5D, true));
//        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
//        this.tasks.addTask(5, new EntityAIOpenDoor(this, true));
//        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.6D));
//        this.tasks.addTask(6, new EntityAILookIdle(this));
//
//        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
//        this.targetTasks.addTask(2, new PriestAINearestHealableTarget(this));
//        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityLiving.class, 10, true, true, UNDEAD));
    }

    @Override
    public boolean canSpawn(IWorld worldIn, SpawnReason spawnReasonIn)
    {
        return super.canSpawn(worldIn, spawnReasonIn);
//        final BlockPos blockPos = new BlockPos(this);
//        final VillagePieces.Village village = super.world.getNearestVillage(blockPos, 32);
//        return village != null && super.canSpawn(worldIn, spawnReasonIn);
    }

//    @Override
//    public boolean getCanSpawnHere()
//    {
        //TODO: To verify... not sure if the code below actually works.


//    }


    @Override
    public boolean canDespawn(double distanceToClosestPlayer)
    {
        return false;
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag)
    {
        ILivingEntityData livingdata = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        setHeldItem(Hand.MAIN_HAND, new ItemStack(ModItems.PRIEST_STAFF));
        return livingdata;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_VINDICATOR_AMBIENT;
    }

    @Override
    protected void updateAITasks()
    {
//        if (--this.randomTickDivider <= 0)
//        {
//            BlockPos blockpos = new BlockPos(this);
//            this.world.getVillageCollection().addToVillagerPositionList(blockpos);
//            this.randomTickDivider = 70 + this.rand.nextInt(50);
//            this.village = this.world.getVillageCollection().getNearestVillage(blockpos, 32);
//        }
    }



//    @Override
//    public void onUpdate()
//    {
//        super.onUpdate();
//    }

    public void setHealingTarget(LivingEntity healingTarget)
    {
        this.healingTarget = healingTarget;
        if (healingTarget == null)
            setHealingEntityId(0);
        else
        {
            setHealingEntityId(healingTarget.getEntityId());
        }
    }

    private void setHealingEntityId(int entityId)
    {
        this.dataManager.set(HEALING_ENTITY_ID, entityId);
    }

    public boolean hasHealingTarget()
    {
        return this.dataManager.get(HEALING_ENTITY_ID) != 0;
    }

    @Nullable
    public LivingEntity getHealingTarget()
    {
        if (!this.hasHealingTarget())
        {
            return null;
        }
        else if (this.world.isRemote)
        {
            if (this.healingTarget != null)
            {
                return this.healingTarget;
            }
            else
            {
                Entity entity = this.world.getEntityByID(this.dataManager.get(HEALING_ENTITY_ID));

                if (entity instanceof LivingEntity)
                {
                    this.healingTarget = (LivingEntity) entity;
                    return this.healingTarget;
                }
                else
                {
                    return null;
                }
            }
        }
        else
        {
            return this.healingTarget;
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == START_HEALING_STATE)
        {
            spawnHealingProcessParticles();
        }
//        else if (id == HEALING_STATE)
//        {
//            spawnHealParticles();
//        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn)
    {
        super.attackEntityAsMob(entityIn);
        float damage = (float)this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue();
        int knockback = 0;

        if (entityIn instanceof LivingEntity)
        {
            damage += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((LivingEntity) entityIn).getCreatureAttribute());
            knockback += EnchantmentHelper.getKnockbackModifier(this);
        }

        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), damage);

        if (flag)
        {
            if (knockback > 0 && entityIn instanceof LivingEntity)
            {
                ((LivingEntity)entityIn).knockBack(this, (float)knockback * 0.5F, MathHelper.sin(this.rotationYaw * 0.017453292F), -MathHelper.cos(this.rotationYaw * 0.017453292F));
                this.setMotion(getMotion().mul(0.6D, 1, 0.6D));
            }

            if (entityIn instanceof PlayerEntity)
            {
                PlayerEntity entityplayer = (PlayerEntity)entityIn;
                ItemStack itemstack = this.getHeldItemMainhand();
                ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;

                if (!itemstack.isEmpty() && !itemstack1.isEmpty() && itemstack.getItem().canDisableShield(itemstack, itemstack1, entityplayer, this) && itemstack1.getItem().isShield(itemstack1, entityplayer))
                {
                    float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;

                    if (this.rand.nextFloat() < f1)
                    {
                        entityplayer.getCooldownTracker().setCooldown(itemstack1.getItem(), 100);
                        this.world.setEntityState(entityplayer, (byte)30);
                    }
                }
            }

            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void swingArm(Hand hand)
    {
        super.swingArm(hand);
    }

//    @Override
//    public void setSwingingArms(boolean swingingArms)
//    {
//        this.dataManager.set(SWINGING_ARMS, swingingArms);
//    }

    @OnlyIn(Dist.CLIENT)
    public boolean isSwingingArms()
    {
        return this.dataManager.get(SWINGING_ARMS);
    }

    protected ArrowEntity createArrowEntity(float p_193097_1_)
    {
        ArrowEntity entitytippedarrow = new ArrowEntity(this.world, this);
        entitytippedarrow.setEnchantmentEffectsFromEntity(this, p_193097_1_);
        return entitytippedarrow;
    }

    public void startHealing()
    {
        //TODO: Move hands up?
        this.world.setEntityState(this, START_HEALING_STATE);
    }

    public void healHealingTarget()
    {
        swingArm(Hand.MAIN_HAND);

        this.world.setEntityState(this, HEALING_STATE);

        // Apply heal potion effect
        this.healingTarget.addPotionEffect(new EffectInstance(Effects.INSTANT_HEALTH, 1, 0));

//        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(new TextComponentString("HealingTarget HP: " + this.healingTarget.getHealth()));
    }

    @OnlyIn(Dist.CLIENT)
    private void spawnHealingProcessParticles()
    {
        final LivingEntity healingTarget = getHealingTarget();
        if (healingTarget == null)
            return;

        double x = healingTarget.getPosX() + 0.6 * Math.cos(angle);
        double z = healingTarget.getPosZ() + 0.6 * Math.sin(angle);

        this.world.addParticle(ParticleTypes.END_ROD, x, healingTarget.getPosY() + 2, z, 0, -0.2, 0);

        if (angle + angleIncrement > 360)
        {
            angle = (angle + angleIncrement) - 360;
        }
        else
        {
            angle += angleIncrement;
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void spawnHealParticles()
    {
        final LivingEntity healingTarget = getHealingTarget();
        if (healingTarget == null)
            return;

        double angle = 0.0;
        double radius = 0.6;
        for (int i = 1; i < 500; ++i)
        {
            double x = healingTarget.getPosX() + radius * Math.cos(angle);
            double z = healingTarget.getPosZ() + radius * Math.sin(angle);

            this.world.addParticle(ParticleTypes.END_ROD, x, healingTarget.getPosY() + 0.5, z, Math.cos(angle) / 8, 0.00, Math.sin(angle) / 8);

            if (angle + angleIncrement > 10)
            {
                angle = (angle + angleIncrement) - 10;
                radius += 0.1;
            }
            else
            {
                angle += angleIncrement;
            }
        }
    }

    public LivingEntity getNearestWoundedFriendlyEntity()
    {
        // Friendly entities = Villagers, Players, Crusaders.

        // Creating new variable here is unnecessary but we'll keep it here for now for brevity sake.
        int distance = 12;
        LivingEntity livingEntity = this.world.func_225318_b(LivingEntity.class, (new EntityPredicate()).setDistance(10).setCustomPredicate(WOUNDED_FRIENDLIES), this, this.getPosX(), this.getEyeHeight(), this.getPosZ(), this.getBoundingBox().grow(10, 4.0D, 10));
//        Entity entity = this.world.<LivingEntity>func_225318_b(LivingEntity.class, (new EntityPredicate()).setDistance(10).setCustomPredicate(WOUNDED_FRIENDLIES), this.goalOwner, this.goalOwner.getPosX(), this.goalOwner.getPosYEye(), this.goalOwner.getPosZ(), this.getTargetableArea(this.getTargetDistance()));

        List<LivingEntity> entities = this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(distance, 4.0D, distance), WOUNDED_FRIENDLIES);

        if (entities.isEmpty())
            return null;

        //Sorts by distance.
        return livingEntity;
    }

    @Override
    protected void dropLoot(DamageSource damageSourceIn, boolean p_213354_2_)
    {
        if (recentlyHit != 0 && super.attackingPlayer != null)
        {
            final float chanceToDropStaff = 0.8f;

            final float randomNumber = super.rand.nextFloat();
            final float playerLuck = super.attackingPlayer.getLuck();
            if (randomNumber + playerLuck > chanceToDropStaff)
            {
                this.entityDropItem(new ItemStack(ModItems.PRIEST_STAFF, 1), 0.0f);
            }
        }
    }

    @Override
    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor)
    {
        ArrowEntity entityarrow = this.createArrowEntity(distanceFactor);;
        double d0 = target.getPosX() - this.getPosX();
        double d1 = target.getBoundingBox().minY + (double)(target.getHeight() / 3.0F) - entityarrow.getPosY();
        double d2 = target.getPosZ() - this.getPosZ();
        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
        entityarrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (float)(14 - this.world.getDifficulty().getId() * 4));
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.addEntity(entityarrow);
    }
}
