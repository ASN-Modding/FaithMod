package io.github.asnmodding.faithmod.item.armor;

import io.github.asnmodding.faithmod.FaithMod;
import io.github.asnmodding.faithmod.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class CrusaderHelmet extends ArmorItem
{
    public CrusaderHelmet()
    {
        super(HolyIronMaterial.getMaterial(), EquipmentSlotType.HEAD, new Properties().group(FaithMod.ITEM_GROUP));
        setRegistryName("crusader_helmet");
    }

    //TODO: Remove this when custom texture for Holy Iron Helmet will be added.
    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player)
    {
        final ItemStack helmet = player.getItemStackFromSlot(EquipmentSlotType.HEAD);
        final ItemStack chestplate = player.getItemStackFromSlot(EquipmentSlotType.CHEST);
        final ItemStack leggings = player.getItemStackFromSlot(EquipmentSlotType.LEGS);
        final ItemStack boots = player.getItemStackFromSlot(EquipmentSlotType.FEET);

        if (helmet == ItemStack.EMPTY || helmet.getItem() == Items.AIR
                || chestplate == ItemStack.EMPTY || chestplate.getItem() == Items.AIR
                || leggings == ItemStack.EMPTY || leggings.getItem() == Items.AIR
                || boots == ItemStack.EMPTY || boots.getItem() == Items.AIR)
            return;

        else if (helmet.getItem() == this
                && chestplate.getItem() == ModItems.CRUSADER_CHESTPLATE
                && leggings.getItem() == ModItems.CRUSADER_LEGGINGS
                && boots.getItem() == ModItems.CRUSADER_BOOTS
        )
        {
            if(player.world.isRemote)
            {
                // Spawn holy armor particles

            }
            else
            {
                // Give swiftness and absorption buff every 20 seconds.
                if (player.getActivePotionEffect(Effects.ABSORPTION) == null || player.getActivePotionEffect(Effects.ABSORPTION).getDuration() <= 1)
                    player.addPotionEffect(new EffectInstance(Effects.ABSORPTION, 400, 1));
                if (player.getActivePotionEffect(Effects.SPEED) == null || player.getActivePotionEffect(Effects.SPEED).getDuration() <= 1)
                    player.addPotionEffect(new EffectInstance(Effects.SPEED, 400, 1));
            }
        }
    }
}
