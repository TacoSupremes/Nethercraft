package com.tacosupremes.nethercraft.common.items;

import net.minecraft.item.ItemStack;

public interface IRecipeGiver 
{
	ItemStack[] getRecipe();
	
	RecipeType getType();
	
	
}
