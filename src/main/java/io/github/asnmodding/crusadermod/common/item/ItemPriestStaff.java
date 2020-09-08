package io.github.asnmodding.crusadermod.common.item;

import io.github.asnmodding.crusadermod.common.util.RaytraceUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemPriestStaff extends ItemBase
{
    public ItemPriestStaff()
    {
        super("priest_staff");
        setMaxStackSize(1);
        setMaxDamage(200);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.YELLOW.toString() + TextFormatting.ITALIC.toString() + "Dropped by priest who enhanced it with healing power");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        final ItemStack itemStack = playerIn.getHeldItem(handIn);
        if (playerIn.isSneaking())
        {
            if (playerIn.world.isRemote)
            {
                spawnHealParticlesAndPlaySound(playerIn, playerIn);
            }
            // Heal yourself
            playerIn.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 0));
        }
        else
        {
            // Heal someone
            final EntityCreature entityCreature = RaytraceUtil.getRayTraceEntity(playerIn, 20);
            if (entityCreature != null)
            {
                if (playerIn.world.isRemote)
                {
                    spawnHealParticlesAndPlaySound(playerIn, entityCreature);
                }

                entityCreature.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 0));
            }
            return new ActionResult<>(EnumActionResult.FAIL, itemStack);
        }

        itemStack.damageItem(1, playerIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
    }

    @SideOnly(Side.CLIENT)
    public void spawnHealParticlesAndPlaySound(final EntityPlayer sourcePlayer, final EntityLivingBase targetEntity)
    {
        sourcePlayer.world.playSound(sourcePlayer, targetEntity.getPosition(), SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.NEUTRAL, 10, 10);
        spawnHealParticles(targetEntity);
    }

    @SideOnly(Side.CLIENT)
    public void spawnHealParticles(final Entity entity)
    {
        final double fullCircle = 2 * Math.PI;
        final double angleIncrement = 2.0 / 12.0 * Math.PI;
        final double radius = 0.2;
        double angle = 0.0;
        double particleY = entity.posY + 2.0;
        for (int i = 0; i < 5; i++)
        {
            for (int j = 1; j < 28; j++)
            {
                double x = entity.posX + (radius + entity.width) * Math.cos(angle);
                double z = entity.posZ + (radius + entity.width) * Math.sin(angle);

                entity.world.spawnParticle(EnumParticleTypes.END_ROD, x, particleY, z, 0, -0.01, 0);

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
