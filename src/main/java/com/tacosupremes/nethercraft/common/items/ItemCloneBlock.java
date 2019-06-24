package com.tacosupremes.nethercraft.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemCloneBlock extends ItemMod {

	private Block block;

	public ItemCloneBlock(Block b) 
	{
		super(b.getUnlocalizedName().replace("tile.", "").replace(".name", ""));
		this.block = b;
	}
	
	public ItemCloneBlock(Block b, String s) 
	{
		super(s);
		this.block = b;
	}

	@Override
	public boolean addToTab() 
	{
		return false;
	}

	@Override
	public ItemStack[] getRecipe(int meta)
	{
		return null;
	}

	@Override
	public String getLocalizedName()
	{
		return block.getLocalizedName();
	}

	public Block getBlock()
	{
		return block;
	}
	
	
}
