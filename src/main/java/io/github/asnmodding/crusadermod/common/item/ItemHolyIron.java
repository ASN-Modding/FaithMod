package io.github.asnmodding.crusadermod.common.item;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemHolyIron extends ItemBase
{
    public ItemHolyIron()
    {
        super("holy_iron");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if (!GuiScreen.isShiftKeyDown())
        {
            tooltip.add(TextFormatting.GRAY + "Hold <Shift> for more info...");
        }

        if (GuiScreen.isShiftKeyDown())
        {
            tooltip.add(TextFormatting.YELLOW + "Created by combining holy water with iron.");
            tooltip.add(TextFormatting.YELLOW + "Can be used as a crafting ingredient");
            tooltip.add(TextFormatting.YELLOW + "for blocks, tools, weapons and armor.");
        }
    }

    //TODO: Use custom animated texture instead of Item#hasEffect method.
    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }
}
