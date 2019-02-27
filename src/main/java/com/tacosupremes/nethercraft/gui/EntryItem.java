package com.tacosupremes.nethercraft.gui;

import net.minecraft.item.ItemStack;

public class EntryItem extends Entry 
{
	private ItemStack itemStack;

	public EntryItem(String s)
	{
		super(s, GuiID.Item);
	}
	
	public EntryItem(ItemStack is)
	{
		this(is.getUnlocalizedName());
		this.itemStack = is;
	}

}
