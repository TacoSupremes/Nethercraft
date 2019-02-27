package com.tacosupremes.nethercraft.gui;

public class Entry 
{
	private String name;
	private GuiID type;

	private int id;
	private Entry parent = null; // parent/prev entry
	private Entry next = null; // next entry
	private boolean sub = false;
	
	public Entry(int id)
	{
		this.id = id;
		this.name = "FAKEENTRY";
	}
	
	
	
	public Entry(String name, GuiID type)
	{
		this(name);
		
		this.type = type;		
	}
	
	public Entry(String name)
	{
		this.name = name;
		GuiHandler.add(this);
		
		this.id = GuiHandler.count;
		GuiHandler.count++;
	}

	public String getName() 
	{
		return name;
	}
	
	public GuiID getType() 
	{
		return type;
	}

	public int getID()
	{
		
		return id;
	}

	public Entry setID(int id2) 
	{
		this.id = id2;
		return this;
	}

	public Entry setParent(Entry e)
	{
		this.parent = e;
		return this;
	}
	
	public Entry getParent()
	{
		return this.parent;
	}
	
	public Entry setNextEntry(Entry e) // linked entry on next page
	{
		this.next = e;
		return this;
		
	}
	
	public Entry getNextEntry()
	{
		return this.next;
	}
	
	public boolean isSubEntry()
	{
		return sub;
	}
	
	public Entry setSubEntry()
	{
		sub = true;
		return this;
	}
}
