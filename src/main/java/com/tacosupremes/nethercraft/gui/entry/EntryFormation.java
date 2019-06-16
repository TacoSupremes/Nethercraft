package com.tacosupremes.nethercraft.gui.entry;

import com.tacosupremes.nethercraft.common.formations.IFormation;
import com.tacosupremes.nethercraft.common.lib.LibMisc;
import com.tacosupremes.nethercraft.gui.GuiID;

import net.minecraft.item.ItemStack;

public class EntryFormation extends Entry
{
	private IFormation formation;

	public EntryFormation(String s)
	{
		super(s, GuiID.Formation);
	}
	
	public EntryFormation(IFormation formation)
	{
		this(formation.getName());
		this.formation = formation;

	}
	
	public IFormation getFormation()
	{
		return formation;
	}

}
