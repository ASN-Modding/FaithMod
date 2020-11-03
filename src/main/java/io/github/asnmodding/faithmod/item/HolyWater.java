package io.github.asnmodding.faithmod.item;

import io.github.asnmodding.faithmod.FaithMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class HolyWater extends Item
{
    public HolyWater()
    {
        super(new Properties().group(FaithMod.ITEM_GROUP).rarity(Rarity.UNCOMMON).maxStackSize(16).setNoRepair());
        setRegistryName(FaithMod.MODID, "holy_water");
    }

    public int getUseDuration(ItemStack stack) {
        return 54000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.BOW;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        int ret = ForgeEventFactory.onItemUseStart(playerIn, itemstack, getUseDuration(itemstack));
        if (ret == -1)
            return ActionResult.resultFail(itemstack);

        playerIn.setActiveHand(handIn);
        return ActionResult.resultConsume(itemstack);
    }
}
