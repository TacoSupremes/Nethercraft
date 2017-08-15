package com.tacosupremes.nethercraft.common.enchantments;

import com.tacosupremes.nethercraft.common.lib.LibMisc;
import com.tacosupremes.nethercraft.common.utils.ProxyRegistry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ModEnchantment extends Enchantment
{

	protected ModEnchantment(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots, String name)
	{
		super(rarityIn, typeIn, slots);

		this.setName(LibMisc.MODID + ":" + name);
		this.setRegistryName(LibMisc.MODID, name);
		ProxyRegistry.register(this);
		ModEnchantments.enchants.add(this);

	}

}
