package com.tacosupremes.nethercraft.common.items;

import net.minecraft.item.ItemStack;

public interface IRecipeGiver 
{
	ItemStack[] getRecipe(int meta);
	
	RecipeType getType(int meta);
	
	default int getCountOfRecipes() 
	{
		return 1;
	}
	
	
}
