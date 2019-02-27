package com.tacosupremes.nethercraft.gui;

public class EntryText extends Entry {

	private int pages;

	public EntryText(String s, int pages) {
		super(s, GuiID.Info);
		
		this.pages = pages;
	}
	
	public int getPageCount()
	{
		return pages;
	}

}
