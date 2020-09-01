package io.github.asnmodding.crusadermod.common.item;

import io.github.asnmodding.crusadermod.common.util.RaytraceUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
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

//    @Override
//    protected RayTraceResult rayTrace(World worldIn, EntityPlayer playerIn, boolean useLiquids)
//    {
//        System.out.println("Raytracing... ");
//        return super.rayTrace(worldIn, playerIn, useLiquids);
//    }



    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        if (playerIn.isSneaking())
        {
            if (playerIn.world.isRemote)
            {
                worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.PLAYERS, 10, 10);

                double angle = 0.0;
                double radius = 0.6;
                double angleIncrement = 2.0 / 12.0 * Math.PI;
                double particleY = playerIn.posY + 2.0;
                for (int i = 0; i < 5; i++)
                {
                    for (int j = 1; j < 28; j++)
                    {
                        double x = playerIn.posX + radius * Math.cos(angle);
                        double z = playerIn.posZ + radius * Math.sin(angle);

                        playerIn.world.spawnParticle(EnumParticleTypes.END_ROD, x, particleY, z, 0, -0.01, 0);

                        if (angle + angleIncrement > 2 * Math.PI)
                        {
                            angle = (angle + angleIncrement) - (2 * Math.PI);
                        }
                        else
                        {
                            angle += angleIncrement;
                        }
                    }
                    particleY -= 0.4;
                }
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
                //TODO: Spawn healing particles.
                if (playerIn.world.isRemote)
                {
                    worldIn.playSound(playerIn, entityCreature.getPosition(), SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.PLAYERS, 10, 10);

                    double angle = 0.0;
                    double radius = 0.6;
                    double angleIncrement = 2.0 / 12.0 * Math.PI;
                    double particleY = entityCreature.posY + 2.0;
                    for (int i = 0; i < 5; i++)
                    {
                        for (int j = 1; j < 28; j++)
                        {
                            double x = entityCreature.posX + radius * Math.cos(angle);
                            double z = entityCreature.posZ + radius * Math.sin(angle);

                            playerIn.world.spawnParticle(EnumParticleTypes.END_ROD, x, particleY, z, 0, -0.01, 0);

                            if (angle + angleIncrement > 2 * Math.PI)
                            {
                                angle = (angle + angleIncrement) - (2 * Math.PI);
                            }
                            else
                            {
                                angle += angleIncrement;
                            }
                        }
                        particleY -= 0.4;
                    }
                }

                entityCreature.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 0));
            }
        }

        final ItemStack itemStack = playerIn.getHeldItem(handIn);
        itemStack.damageItem(1, playerIn);

        return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
    }

//    @Override
//    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
//    {
//        System.out.println("ItemPriestStaff Use!");
//        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
//    }
}
