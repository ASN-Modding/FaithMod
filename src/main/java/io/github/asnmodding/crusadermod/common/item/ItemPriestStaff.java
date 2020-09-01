package io.github.asnmodding.crusadermod.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
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
//        playerIn.rayTrace();
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        System.out.println("ItemPriestStaff Use!");
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
