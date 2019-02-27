package com.tacosupremes.nethercraft.gui;

import net.minecraft.item.ItemStack;

public class GuiModBookItem extends GuiModBook
{

	private ItemStack is; // TODO CREATE RECIPE PAGES / ADD NEXTBUTTON TO GUIMODBOOK

	public GuiModBookItem(EntryItem e) 
	{
		super(e);
		this.is = e.getItemStack();
	}

	@Override
	public void initGuiFeatures()
	{

	}

	@Override
	public void updateScreen() 
	{
		
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) 
	{
		super.drawScreen(par1, par2, par3);
	}
	
	

}
