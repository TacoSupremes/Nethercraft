package com.tacosupremes.nethercraft.common.items;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class ItemNetherGlasses extends ItemArmor {

	public ItemNetherGlasses()
	{
		super(ArmorMaterial.IRON, 0, EntityEquipmentSlot.HEAD);
		ItemMod.registerNonItemMod(this, "nether_glasses");
	}
}