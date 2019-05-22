package com.tacosupremes.nethercraft.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.tacosupremes.nethercraft.Nethercraft;
import com.tacosupremes.nethercraft.common.lib.LibMisc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.resources.I18n;


public class GuiModBookText extends GuiModBook{

	int cp, pc; // cp = current page pC = page count
		
	String text;
	
	public GuiModBookText(EntryText e)
	{
		super(e);
		text = e.getName();
		pc = e.getPageCount();
		cp = 1;
		
	}
	

	@Override
	public void drawScreen(int par1, int par2, float par3) 
	{
		super.drawScreen(par1, par2, par3);	

		if(I18n.format(LibMisc.MODID + "." + text + cp).equals(LibMisc.MODID + "." + text + cp))
			return;
		
		this.drawTextSplit(mc.fontRenderer, "" + I18n.format(LibMisc.MODID + "." + text + cp), left + 15, top + 40,  guiWidth - 25, Color.WHITE.getRGB());		   	
	}


	@Override
	protected void actionPerformed(GuiButton gb) 
	{
		
		if(e.getNextEntry() != null && gb.id == e.getNextEntry().getID() && cp < pc)
		{	
			cp++;
			
			if(cp == pc && e.getNextEntry().getID() == EntryText.INCREASEPAGEID)
				gb.visible = false;
		}
		else if(gb.id == e.getParent().getID() && cp > 1)
		{
			cp--;
			
			if(cp < pc)
			{
				for(GuiButton gb2 : this.buttonList)
				{
					if(gb2.id == e.getNextEntry().getID() && !gb2.visible)
						gb2.visible = true;
				}
					
			}
			
		}else
			super.actionPerformed(gb);
	}
	
}
