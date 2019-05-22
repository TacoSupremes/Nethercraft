package com.tacosupremes.nethercraft.gui;

import com.tacosupremes.nethercraft.common.blocks.ModBlocks;
import com.tacosupremes.nethercraft.common.items.IRecipeGiver;
import com.tacosupremes.nethercraft.common.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class EntryRecipe extends Entry 
{
	private ItemStack is;

	public EntryRecipe(String s)
	{
		super(s, GuiID.Recipe);
	}
	
	public EntryRecipe(ItemStack is)
	{
		this(is.getUnlocalizedName() + ".rec");
		this.is = is;

	}
	
	public ItemStack getItemStack()
	{
		return is;
	}

	
	public boolean isBlock()
	{
		return Block.getBlockFromItem(is.getItem()) != null;
	}

	public ItemStack[] getRecipe()
	{
		
		if(isBlock())
		{
			return ((IRecipeGiver)Block.getBlockFromItem(is.getItem())).getRecipe();
		}
		
		return ((IRecipeGiver)is.getItem()).getRecipe();
	}
	
	
}
