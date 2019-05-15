package com.tacosupremes.nethercraft.gui;

import java.util.List;

public class EntryList extends Entry {

	private Entry[] e;

	public EntryList(String name, Entry[] en)
	{
		super(name, GuiID.List);
		//9	
		if(en.length > 9)
		{
			Entry[] e9 = new Entry[9];
			Entry[] extra = new Entry[en.length - 9];
			
			for(int i = 0; i < en.length; i++)
			{
				if(i < 9)
					e9[i] = en[i];
				else
					extra[i-9] = en[i];
			}
			
			this.e = e9;

			this.setNextEntry(new EntryList(name, extra, true).setParent(this));
			
		}
		else
			this.e = en;
			
		for(Entry e2 : e)
			e2.setParent(this);
	}

	public EntryList(String string, Entry[] en, boolean b) {
		this(string + " ", en);
		this.setSubEntry();
	}

	public Entry[] getList() 
	{
		return e;
	}

}
