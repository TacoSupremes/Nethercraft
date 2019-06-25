package com.tacosupremes.nethercraft.gui.entry;

import com.tacosupremes.nethercraft.gui.GuiID;

import net.minecraft.item.ItemStack;

public class EntryItem extends Entry 
{
	private ItemStack is;

	public EntryItem(String s)
	{
		super(s, GuiID.Item);
	}
	
	public EntryItem(ItemStack is)
	{
		this(is.getUnlocalizedName());
		this.is = is;
		this.setNextEntry(new EntryRecipe(is).setParent(this));
	}
	
	public ItemStack getItemStack()
	{
		return is;
	}

	@Override
	public String getLocalisedName() 
	{
		return is.getDisplayName();
	}

}
