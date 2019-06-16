package com.tacosupremes.nethercraft.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tacosupremes.nethercraft.common.blocks.ModBlocks;
import com.tacosupremes.nethercraft.common.formations.Formations;
import com.tacosupremes.nethercraft.common.formations.IFormation;
import com.tacosupremes.nethercraft.common.formations.IGenFormation;
import com.tacosupremes.nethercraft.common.items.IRecipeGiver;
import com.tacosupremes.nethercraft.common.items.ItemMod;
import com.tacosupremes.nethercraft.common.items.ModItems;
import com.tacosupremes.nethercraft.gui.entry.Entry;
import com.tacosupremes.nethercraft.gui.entry.EntryFormation;
import com.tacosupremes.nethercraft.gui.entry.EntryItem;
import com.tacosupremes.nethercraft.gui.entry.EntryList;
import com.tacosupremes.nethercraft.gui.entry.EntryRecipe;
import com.tacosupremes.nethercraft.gui.entry.EntryText;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	static Map<String, Entry> m = new HashMap<String, Entry>();

	static int count = 0;
	
	public GuiHandler()
	{
		Entry e = new EntryList("Nether-Tome", new Entry[]{new Entry("Intro"), new Entry("Generating-Nether-Energy"), new Entry("Using-Nether-Energy"), new Entry("Nether-Items"), new Entry("n"), new Entry("n2"), new Entry("n3"), new Entry("n4"), new Entry("n5"), new Entry("n6"), new Entry("n7"), new Entry("n8"), new Entry("n9"), new Entry("n10"), new Entry("n11"), new Entry("n12")});		
		
		Entry e2 = new EntryList("Intro", new Entry[] {new EntryText("What-is-Nethercraft", 2), new EntryText("Nether-Energy", 1),  new EntryText("heat-altar" ,1),new EntryText("Overuse-of-Nether-Energy", 1)});
		
		Entry e3 = new EntryItem(new ItemStack(ModBlocks.formationBase));
		
		List<Entry> l = new ArrayList<Entry>();
		
		for(ItemMod i : ModItems.items)
		{
			ItemStack is = new ItemStack(i);
			Entry temp = new EntryItem(is);
			l.add(temp);
		}
		
		for(Item i : ModItems.nitems)
		{
			ItemStack is = new ItemStack(i);
			Entry temp = new EntryItem(is);
			l.add(temp);
		}
		
		
		Entry items = new EntryList("Nether-Items", l.toArray(new Entry[l.size()]));
		
		List<Entry> lGF = new ArrayList<Entry>();
		List<Entry> lCF = new ArrayList<Entry>();
		
		for(IFormation i : Formations.formations)
		{
			Entry temp = new EntryText(i.getName() + ".lore");
				
			Entry temp2 = new EntryFormation(i);
			
			temp.setNextEntry(temp2.setParent(temp));
			
			if(i instanceof IGenFormation)
				lGF.add(temp);
			else
				lCF.add(temp);
		}
		
		Entry gF = new EntryList("Generating-Nether-Energy", lGF.toArray(new Entry[lGF.size()]));
		Entry cF = new EntryList("Using-Nether-Energy", lCF.toArray(new Entry[lCF.size()]));	
		
		setNextEntry("heat-altar", new ItemStack(ModBlocks.formationBase));	
	}
	
	private void addFormation(IFormation i)
	{
	//	Entry e = new EntryText(i.getName() + , 1);
		
		
	}
	
	
	private void setNextEntry(String parent, ItemStack itemStack)
	{
		setNextEntry(parent, itemStack.getUnlocalizedName());	
	}
	
	public static boolean hasEntry(Entry e)
	{
		return m.containsKey(e.getName());
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
	
	public static Entry getEntryFromStack(ItemStack is)
	{
		
		
		return m.get(is.getUnlocalizedName());
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
		Entry e = getEntryFromID(ID); 
		
		
		// if entry not null return null since book doesn't have a container
		if(e != null)
			return null;
		
		return null;
	}

	//public String s[] = new String[]{"Intro", "Generating Nether Energy", "Using Nether Energy", "Nether Items"};
	
	//public GuiID ls[] = new GuiID[] {GuiID.List, GuiID.List, GuiID.List, GuiID.List};
	
	//public static int list = 0, gen = 0, consumer = 0, item = 0, info = 0;

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
		case Formation:
			return new GuiModBookFormation((EntryFormation)e);
		case Info:
			return new GuiModBookText((EntryText)e);
		case Item:
			return new GuiModBookItem((EntryItem)e);
		case List:
			return new GuiModBookList((EntryList)e);	
		case Recipe:
			return new GuiModBookRecipe((EntryRecipe)e);
		default:
			break;
		
		}
		
		return null;
	}

}
