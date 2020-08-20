package io.github.asnmodding.crusadermod.common.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemHolyWater extends ItemBase
{
    public ItemHolyWater()
    {
        super("holy_water");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        EntityPlayer entityplayer = entityLiving instanceof EntityPlayer ? (EntityPlayer)entityLiving : null;

        if (entityplayer == null || !entityplayer.capabilities.isCreativeMode)
        {
            stack.shrink(1);
        }

        if (entityplayer instanceof EntityPlayerMP)
        {
            CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)entityplayer, stack);
        }

        if (!worldIn.isRemote)
        {
            // Cure negative effects
            curePlayerNegativePotionEffects(entityLiving);

            // Add Holy Water potion effects
            for (final PotionEffect potionEffect : getHolyWaterPotionEffects())
            {
                entityLiving.addPotionEffect(potionEffect);
            }
        }

        if (entityplayer != null)
        {
            entityplayer.addStat(StatList.getObjectUseStats(this));
        }

        if (entityplayer == null || !entityplayer.capabilities.isCreativeMode)
        {
            if (stack.isEmpty())
            {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            if (entityplayer != null)
            {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        return stack;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 32;
    }

//    public String getItemStackDisplayName(ItemStack stack)
//    {
//        return I18n.translateToLocal(PotionUtils.getPotionFromItem(stack).getNamePrefixed("potion.effect."));
//    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.YELLOW.toString() + TextFormatting.ITALIC.toString() + "Clears all negatives effects.");
        tooltip.add(TextFormatting.YELLOW.toString() + TextFormatting.ITALIC.toString() + "Gives temporary absorption and health boost.");
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }

    @Override
    public boolean getHasSubtypes()
    {
        return false;
    }

//    @SideOnly(Side.CLIENT)
//    public ItemStack getDefaultInstance()
//    {
//        return PotionUtils.addPotionToItemStack(new ItemStack(this), PotionTypes.INVISIBILITY);
//    }

    /**
     * Cures negative/bad potion effects from the given Entity.
     *
     * @param entityLiving the entity that potion effects should be removed from.
     */
    public static void curePlayerNegativePotionEffects(EntityLivingBase entityLiving)
    {
        if (entityLiving.getEntityWorld().isRemote)
            return;

        final List<PotionEffect> potionEffects = new ArrayList<>(entityLiving.getActivePotionEffects());
        for (final PotionEffect potionEffect : potionEffects)
        {
            if (potionEffect.getPotion().isBadEffect())
            {
                entityLiving.removePotionEffect(potionEffect.getPotion());
            }
        }
    }

    public static List<PotionEffect> getHolyWaterPotionEffects()
    {
        // TODO: We could potentially create our own potion with all below effects.
        final List<PotionEffect> potionEffects = new ArrayList<>(3);
        potionEffects.add(new PotionEffect(MobEffects.ABSORPTION, 2400, 1, false, true));
        potionEffects.add(new PotionEffect(MobEffects.HEALTH_BOOST, 2400, 1));
        potionEffects.add(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 4, false, true));
        return potionEffects;
    }
}
