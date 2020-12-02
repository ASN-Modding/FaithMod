package io.github.asnmodding.faithmod.item;

import io.github.asnmodding.faithmod.FaithMod;
import io.github.asnmodding.faithmod.util.RaytraceUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPriestStaff extends Item
{
    public ItemPriestStaff()
    {
        super(new Properties().maxStackSize(1).maxDamage(100).group(FaithMod.ITEM_GROUP));
        setRegistryName("priest_staff");
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(new StringTextComponent(TextFormatting.YELLOW.toString() + TextFormatting.ITALIC.toString() + "Dropped by priest who enhanced it with healing power"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        final ItemStack itemStack = playerIn.getHeldItem(handIn);
        if (playerIn.isSneaking())
        {
            if (playerIn.world.isRemote)
            {
                spawnHealParticlesAndPlaySound(playerIn, playerIn);
            }
            // Heal yourself
            playerIn.addPotionEffect(new EffectInstance(Effects.INSTANT_HEALTH, 1, 0));
        }
        else
        {
            // Heal someone
            final CreatureEntity entityCreature = RaytraceUtil.getRayTraceEntity(playerIn, 20);
            if (entityCreature != null)
            {
                if (playerIn.world.isRemote)
                {
                    spawnHealParticlesAndPlaySound(playerIn, entityCreature);
                }

                entityCreature.addPotionEffect(new EffectInstance(Effects.INSTANT_HEALTH, 1, 0));
            }
            return new ActionResult<>(ActionResultType.FAIL, itemStack);
        }

        itemStack.damageItem(1, playerIn, playerEntity -> {});
        return new ActionResult<>(ActionResultType.SUCCESS, itemStack);
    }

    @OnlyIn(Dist.CLIENT)
    public void spawnHealParticlesAndPlaySound(final PlayerEntity sourcePlayer, final LivingEntity targetEntity)
    {
        sourcePlayer.world.playSound(sourcePlayer, targetEntity.getPosition(), SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.NEUTRAL, 10, 10);
        spawnHealParticles(targetEntity);
    }

    @OnlyIn(Dist.CLIENT)
    public void spawnHealParticles(final Entity entity)
    {
        final double fullCircle = 2 * Math.PI;
        final double angleIncrement = 2.0 / 12.0 * Math.PI;
        final double radius = 0.2;
        double angle = 0.0;
        double particleY = entity.getPosY() + 2.0;
        for (int i = 0; i < 5; i++)
        {
            for (int j = 1; j < 28; j++)
            {
                double x = entity.getPosX() + (radius + entity.getWidth()) * Math.cos(angle);
                double z = entity.getPosZ() + (radius + entity.getWidth()) * Math.sin(angle);

                entity.world.addParticle(ParticleTypes.END_ROD, x, particleY, z, 0, -0.01, 0);

                if (angle + angleIncrement > fullCircle)
                {
                    angle = (angle + angleIncrement) - fullCircle;
                }
                else
                {
                    angle += angleIncrement;
                }
            }
            particleY -= 0.4;
        }
    }
}
