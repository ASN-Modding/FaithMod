package io.github.asnmodding.faithmod.item;

import io.github.asnmodding.faithmod.FaithMod;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemHolyIron extends Item
{
    public ItemHolyIron()
    {
        super(new Properties().group(FaithMod.ITEM_GROUP));
        setRegistryName("holy_iron");
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        if (!Screen.hasShiftDown())
        {
            tooltip.add(new StringTextComponent(TextFormatting.GRAY + "Hold <Shift> for more info..."));
        }

        if (Screen.hasShiftDown())
        {
            tooltip.add(new StringTextComponent(TextFormatting.YELLOW + "Created by combining holy water with iron."));
            tooltip.add(new StringTextComponent(TextFormatting.YELLOW + "Can be used as a crafting ingredient"));
            tooltip.add(new StringTextComponent(TextFormatting.YELLOW + "for blocks, tools, weapons and armor."));
        }
    }

    //TODO: Use custom animated texture instead of Item#hasEffect method.
    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }
}
