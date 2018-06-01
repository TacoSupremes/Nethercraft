package com.tacosupremes.nethercraft.common.enchantments;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;

public class LavaWalkerEnchantment extends ModEnchantment {

	public LavaWalkerEnchantment() {
		super(Rarity.RARE, EnumEnchantmentType.ARMOR_FEET, new EntityEquipmentSlot[] {EntityEquipmentSlot.FEET}, "lava_walker");
	}

}
