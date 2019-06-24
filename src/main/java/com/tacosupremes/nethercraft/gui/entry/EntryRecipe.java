package com.tacosupremes.nethercraft.gui.entry;

import com.tacosupremes.nethercraft.common.blocks.ModBlocks;
import com.tacosupremes.nethercraft.common.items.IRecipeGiver;
import com.tacosupremes.nethercraft.common.items.ModItems;
import com.tacosupremes.nethercraft.common.items.RecipeType;
import com.tacosupremes.nethercraft.gui.GuiID;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
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
		this(is.getUnlocalizedName() +  ".rec");
		this.is = is;
		if(this.getRecipeCount() > 1)
		{	
			ItemStack is2 = is.copy();
			is2.setItemDamage(1);
			
			this.setNextEntry(new EntryRecipe(is2, this.getRecipeCount() - 1).setParent(this));
			
		}

	}
	
	public EntryRecipe(ItemStack is, int c)
	{
		this(is.getUnlocalizedName() + c +  ".rec");
		this.is = is;
		if(c > 1)
		{	
			ItemStack is2 = is.copy();
			is2.setItemDamage(1);
			
			this.setNextEntry(new EntryRecipe(is2, c - 1).setParent(this));
			
		}

	}
	
	public ItemStack getItemStack()
	{
		return is;
	}

	public boolean isBlock()
	{
		return Block.getBlockFromItem(is.getItem()) != Blocks.AIR;
	}

	public ItemStack[] getRecipe(int meta)
	{
		if(isBlock())
		{
			return ((IRecipeGiver)Block.getBlockFromItem(is.getItem())).getRecipe(meta);
		}
		
		return ((IRecipeGiver)is.getItem()).getRecipe(meta);
	}

	public RecipeType getRecipeType() 
	{	
		if(isBlock())
			return ((IRecipeGiver)Block.getBlockFromItem(is.getItem())).getType(is.getItemDamage());
		
		return ((IRecipeGiver)is.getItem()).getType(is.getItemDamage());
	}
	
	public int getRecipeCount() 
	{	
		if(isBlock())
			return ((IRecipeGiver)Block.getBlockFromItem(is.getItem())).getCountOfRecipes();
		
		return ((IRecipeGiver)is.getItem()).getCountOfRecipes();
	}
	
	
}
