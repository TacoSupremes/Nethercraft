package com.tacosupremes.nethercraft.common.items;

import com.tacosupremes.nethercraft.common.blocks.BlockMod;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public class ItemPortal extends ItemMod {

	public ItemPortal() 
	{
		super("nether_portal");
		
	}

	@Override
	public ItemStack[] getRecipe()
	{
		return null;
	}
	
    @Override
    public boolean addToTab()
	{
		return false;
	}

}
