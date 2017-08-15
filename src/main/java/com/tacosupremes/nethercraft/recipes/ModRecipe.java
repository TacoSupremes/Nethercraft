package com.tacosupremes.nethercraft.recipes;


import com.tacosupremes.nethercraft.common.lib.LibMisc;
import com.tacosupremes.nethercraft.common.utils.RecipeHandler;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class ModRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe
{

	public ModRecipe(ResourceLocation res)
	{
		RecipeHandler.addRecipe(res, this);
	}

	public ModRecipe(String name)
	{
		this(new ResourceLocation(LibMisc.MODID, name));
	}

	@Override
	public boolean isHidden()
	{
		return true;
	}


}