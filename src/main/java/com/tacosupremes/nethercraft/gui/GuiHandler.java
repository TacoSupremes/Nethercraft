package com.tacosupremes.nethercraft.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tacosupremes.nethercraft.common.blocks.ModBlocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	static Map<String, Entry> m = new HashMap<String, Entry>();

	static int count = 0;
	
	public GuiHandler()
	{
		Entry e = new EntryList("Nether-Tome", new Entry[]{new Entry("Intro"), new Entry("Generating-Nether-Energy"), new Entry("Using-Nether-Energy"), new Entry("Nether-Items")});		
		
		Entry e2 = new EntryList("Intro", new Entry[] {new EntryText("What-is-Nethercraft", 2), new EntryText("Nether-Energy", 1),  new EntryText("heat-altar" ,1),new EntryText("Overuse-of-Nether-Energy", 1)});
		
		Entry e3 = new EntryItem(new ItemStack(ModBlocks.formationBase));
		
		setNextEntry("heat-altar", new ItemStack(ModBlocks.formationBase));
	
	}
	
	
	private void setNextEntry(String parent, ItemStack itemStack)
	{
		setNextEntry(parent, itemStack.getUnlocalizedName());
		
	}


	public static void add(Entry e)
	{
		if(m.containsKey(e.getName())) 
		{		
			Entry n = m.get(e.getName());
			
			System.out.println("Replaced " + e.getName() + e.getID() + "==>" + n.getID());
			
			e.setID(n.getID());
			
			e.setParent(n.getParent());		
			
			m.replace(e.getName(), e);
		}
		else
			m.put(e.getName(), e);
		
	}
	
	public static void setNextEntry(String parent, String next)
	{
		Entry e = getEntryFromName(parent);
		
		Entry c = getEntryFromName(next);
		
		if(e != null && c != null)
		{
			e.setNextEntry(c);
			c.setParent(e);
		}
	}
	
	public static Entry getEntryFromName(String s)
	{
		return m.get(s);
	}
	
	public static Entry getEntryFromID(int ID)
	{
		for(Entry e : m.values())
		{
			if(e.getID() == ID)
				return e;
		}
		
		return null;
	}
	
	//public static final int BOOKID = 0;
	
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{	
		return null;
	}

	
	public String s[] = new String[]{"Intro", "Generating Nether Energy", "Using Nether Energy", "Nether Items"};
	
	public GuiID ls[] = new GuiID[] {GuiID.List, GuiID.List, GuiID.List, GuiID.List};
	
	public static int list = 0, gen = 0, consumer = 0, item = 0, info = 0;
	
	

	
	//public static int IDCOUNT = 1;
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		
		Entry e = getEntryFromID(ID); 
		
		if(e == null)
		{
			System.out.println("NO ID for" + ID);
			return null;
		}
		
		if(e.getType() == null)
		{
			System.out.println("NO Type for ID: " + ID);
			return null;
		}
		
		switch(e.getType())
		{
		case Consumer:
			break;
		case Gen:
			break;
		case Info:
			return new GuiModBookText((EntryText)e);
		case Item:
			break;
		case List:
			return new GuiModBookList((EntryList)e);
		default:
			break;
		
		}
	
		
		return null;
	}

}
