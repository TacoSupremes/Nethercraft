package com.tacosupremes.nethercraft.gui;

import java.util.List;

public class EntryList extends Entry {

	private Entry[] e;

	public EntryList(String name, Entry[] en) {
		super(name, GuiID.List);
		this.e = en;
		
		for(Entry entry : this.e)
		{
			entry.setParent(this);
		}
		
	}

	public Entry[] getList() 
	{
		return e;
	}

}
