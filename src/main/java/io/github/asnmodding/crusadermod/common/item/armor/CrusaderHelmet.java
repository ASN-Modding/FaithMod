package io.github.asnmodding.crusadermod.common.item.armor;

import io.github.asnmodding.crusadermod.CrusaderMod;
import io.github.asnmodding.crusadermod.common.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class CrusaderHelmet extends ItemArmor
{
    public CrusaderHelmet()
    {
        super(ArmorMaterial.valueOf("holy_iron"), 1, EntityEquipmentSlot.HEAD);
        setRegistryName("crusader_helmet");
        setTranslationKey("crusader_helmet");
        setCreativeTab(CrusaderMod.CREATIVE_TAB);
    }

    @Override
    public EntityEquipmentSlot getEquipmentSlot()
    {
        return EntityEquipmentSlot.HEAD;
    }

    //TODO: Remove this when custom texture for Holy Iron Helmet will be added.
    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
        final ItemStack helmet = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        final ItemStack chestplate = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        final ItemStack leggins = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
        final ItemStack boots = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);

        if (helmet == ItemStack.EMPTY || helmet.getItem() == Items.AIR
                || chestplate == ItemStack.EMPTY || chestplate.getItem() == Items.AIR
                || leggins == ItemStack.EMPTY || leggins.getItem() == Items.AIR
                || boots == ItemStack.EMPTY || boots.getItem() == Items.AIR)
            return;

        else if (helmet.getItem() == this
                && chestplate.getItem() == ModItems.CRUSADER_CHESTPLATE
                && leggins.getItem() == ModItems.CRUSADER_LEGGINS
                && boots.getItem() == ModItems.CRUSADER_BOOTS
                )
        {
//             Give swiftness and absorption buff every 20 seconds.
            if (player.getActivePotionEffect(MobEffects.ABSORPTION) == null || player.getActivePotionEffect(MobEffects.ABSORPTION).getDuration() <= 1)
                player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 400, 1));
            if (player.getActivePotionEffect(MobEffects.SPEED) == null || player.getActivePotionEffect(MobEffects.SPEED).getDuration() <= 1)
                player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 400, 1));
        }
    }
}
