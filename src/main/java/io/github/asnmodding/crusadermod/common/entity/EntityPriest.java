package io.github.asnmodding.crusadermod.common.entity;

import com.google.common.base.Predicate;
import io.github.asnmodding.crusadermod.common.ModItems;
import io.github.asnmodding.crusadermod.common.entity.ai.priest.PriestAIAttack;
import io.github.asnmodding.crusadermod.common.entity.ai.priest.PriestAIHeal;
import io.github.asnmodding.crusadermod.common.entity.ai.priest.PriestAIHealOthers;
import io.github.asnmodding.crusadermod.common.entity.ai.priest.PriestAINearestHealableTarget;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;


public class EntityPriest extends EntityCreature implements IRangedAttackMob
{
    private static final Predicate<Entity> UNDEAD = entity -> entity instanceof EntityLivingBase
            && (((EntityLivingBase)entity).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD || entity instanceof EntityZombie || entity instanceof EntitySpider) && ((EntityLivingBase)entity).attackable();
    private static final Predicate<Entity> WOUNDED_FRIENDLIES = entity ->
    {
        if (!(entity instanceof EntityVillager) && !(entity instanceof EntityPlayer))
            return false;

        final EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
        if (entityLivingBase.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
            return false;

        return Float.compare(entityLivingBase.getHealth(), entityLivingBase.getMaxHealth()) < 0;
    };

    private static final DataParameter<Integer> HEALING_ENTITY_ID = EntityDataManager.createKey(EntityGuardian.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> SWINGING_ARMS = EntityDataManager.<Boolean>createKey(AbstractSkeleton.class, DataSerializers.BOOLEAN);

//    private Village village;

//    private BlockPos home;

    private EntityLivingBase healingTarget;

//    private boolean isHealing;

//    private int randomTickDivider;

    private double angle = 0;
    private final double angleIncrement = 2.0 / 28.0 * Math.PI;

    private final byte START_HEALING_STATE = 1;
    private final byte HEALING_STATE = 2;

    public EntityPriest(World worldIn)
    {
        super(worldIn);
//        enablePersistence();
        ((PathNavigateGround)this.getNavigator()).setBreakDoors(true);
        this.dataManager.register(HEALING_ENTITY_ID, 0);
        this.dataManager.register(SWINGING_ARMS, false);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new PriestAIAttack(this, 0.6, true));
        this.tasks.addTask(2, new PriestAIHeal(this));
        this.tasks.addTask(2, new PriestAIHealOthers(this));
        this.tasks.addTask(3, new EntityAIMoveIndoors(this));
        this.tasks.addTask(3, new EntityAIMoveTowardsTarget(this, 0.6D, 32.0F));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F, 0.2F));
        this.tasks.addTask(4, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(4, new EntityAIMoveThroughVillage(this, 0.5D, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
        this.tasks.addTask(5, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(6, new EntityAILookIdle(this));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new PriestAINearestHealableTarget(this));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityLiving.class, 10, true, true, UNDEAD));
    }

    @Override
    public boolean getCanSpawnHere()
    {
        //TODO: To verify... not sure if the code below actually works.

        final BlockPos blockPos = new BlockPos(this);
        final Village village = super.world.getVillageCollection().getNearestVillage(blockPos, 32);
        if (village == null)
            return false;
        else return true;
    }

    @Override
    protected boolean canDespawn()
    {
        return false;
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        setHeldItem(EnumHand.MAIN_HAND, new ItemStack(ModItems.PRIEST_STAFF));
        return livingdata;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.VINDICATION_ILLAGER_AMBIENT;
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

    @Override
    public void onUpdate()
    {
        super.onUpdate();
    }

    public void setHealingTarget(EntityLivingBase healingTarget)
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
    public EntityLivingBase getHealingTarget()
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

                if (entity instanceof EntityLivingBase)
                {
                    this.healingTarget = (EntityLivingBase)entity;
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
    @SideOnly(Side.CLIENT)
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
        float damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        int knockback = 0;

        if (entityIn instanceof EntityLivingBase)
        {
            damage += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase) entityIn).getCreatureAttribute());
            knockback += EnchantmentHelper.getKnockbackModifier(this);
        }

        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), damage);

        if (flag)
        {
            if (knockback > 0 && entityIn instanceof EntityLivingBase)
            {
                ((EntityLivingBase)entityIn).knockBack(this, (float)knockback * 0.5F, MathHelper.sin(this.rotationYaw * 0.017453292F), -MathHelper.cos(this.rotationYaw * 0.017453292F));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            if (entityIn instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)entityIn;
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
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
        EntityArrow entityarrow = this.createArrowEntity(distanceFactor);
        if (this.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemBow)
            entityarrow = ((net.minecraft.item.ItemBow) this.getHeldItemMainhand().getItem()).customizeArrow(entityarrow);
        double d0 = target.posX - this.posX;
        double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 3.0F) - entityarrow.posY;
        double d2 = target.posZ - this.posZ;
        double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
        entityarrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (float)(14 - this.world.getDifficulty().getId() * 4));
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(entityarrow);
    }

    @Override
    public void setSwingingArms(boolean swingingArms)
    {
        this.dataManager.set(SWINGING_ARMS, swingingArms);
    }

    @SideOnly(Side.CLIENT)
    public boolean isSwingingArms()
    {
        return this.dataManager.get(SWINGING_ARMS).booleanValue();
    }

    protected EntityArrow createArrowEntity(float p_193097_1_)
    {
        EntityTippedArrow entitytippedarrow = new EntityTippedArrow(this.world, this);
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
        setSwingingArms(true);

        this.world.setEntityState(this, HEALING_STATE);

        // Apply heal potion effect
        this.healingTarget.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 0));

//        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(new TextComponentString("HealingTarget HP: " + this.healingTarget.getHealth()));
    }

    @SideOnly(Side.CLIENT)
    private void spawnHealingProcessParticles()
    {
        final EntityLivingBase healingTarget = getHealingTarget();
        if (healingTarget == null)
            return;

        double x = healingTarget.posX + 0.6 * Math.cos(angle);
        double z = healingTarget.posZ + 0.6 * Math.sin(angle);

        this.world.spawnParticle(EnumParticleTypes.END_ROD, x, healingTarget.posY + 2, z, 0, -0.2, 0);

        if (angle + angleIncrement > 360)
        {
            angle = (angle + angleIncrement) - 360;
        }
        else
        {
            angle += angleIncrement;
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnHealParticles()
    {
        final EntityLivingBase healingTarget = getHealingTarget();
        if (healingTarget == null)
            return;

        double angle = 0.0;
        double radius = 0.6;
        for (int i = 1; i < 500; ++i)
        {
            double x = healingTarget.posX + radius * Math.cos(angle);
            double z = healingTarget.posZ + radius * Math.sin(angle);

            this.world.spawnParticle(EnumParticleTypes.END_ROD, x, healingTarget.posY + 0.5, z, Math.cos(angle) / 8, 0.00, Math.sin(angle) / 8);

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

    public EntityLivingBase getNearestWoundedFriendlyEntity()
    {
        // Friendly entities = Villagers, Players, Crusaders.

        // Creating new variable here is unnecessary but we'll keep it here for now for brevity sake.
        int distance = 12;
        List<EntityLivingBase> entities = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(distance, 4.0D, distance), WOUNDED_FRIENDLIES);

        if (entities.isEmpty())
            return null;

        //Sorts by distance.
        entities.sort(new EntityAINearestAttackableTarget.Sorter(this));
        return entities.get(0);
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)
    {
        if (wasRecentlyHit && super.attackingPlayer != null)
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
}
