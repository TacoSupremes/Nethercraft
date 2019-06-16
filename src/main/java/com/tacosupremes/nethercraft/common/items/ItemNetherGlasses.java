package com.tacosupremes.nethercraft.common.items;

import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemNetherGlasses extends ItemArmor implements IRecipeGiver{

	public ItemNetherGlasses()
	{
		super(ArmorMaterial.IRON, 0, EntityEquipmentSlot.HEAD);
		ItemMod.registerNonItemMod(this, "nether_glasses");
	}
	
	@Override
	public ItemStack[] getRecipe(int meta) 
	{
		ItemStack b = new ItemStack(ModItems.blazeIngot, 1, 1);
			
		ItemStack s = new ItemStack(Items.STICK);
			
		ItemStack n = ItemStack.EMPTY;
			
		return new ItemStack[] {
					b,s,b,
					s,n,s,
					n,n,n
				};
		}

	@Override
	public RecipeType getType(int meta)
	{
		return RecipeType.Shaped;
	}
}