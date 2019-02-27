package com.tacosupremes.nethercraft.gui;

public class EntryText extends Entry {

	private int pages;
	private int cp;
	
	public static int INCREASEPAGEID = -90876;
	public static int DECREASEPAGEID = -90877;

	public EntryText(String s, int pages) {
		super(s, GuiID.Info);
		
		this.pages = pages;
		
		cp = 1;
		
		if(pages > 1 && this.getNextEntry() == null)
		{
			this.setNextEntry(new Entry(INCREASEPAGEID));
		}
		
			
	}
	
	public EntryText(String s, int cp, EntryText et, int totalPages)
	{
		super(s + cp, GuiID.Info);
		this.setSubEntry();
		this.setParent(et);
		this.cp = cp;
		
		if(cp < totalPages)
		{
			Entry e = new EntryText(s, cp + 1, this, pages);
			this.setNextEntry(e);
		}
		
	}
	
	public int getPageCount()
	{
		return pages;
	}
	
	public int getCurrentPage()
	{
		return cp;
	}

	

}
