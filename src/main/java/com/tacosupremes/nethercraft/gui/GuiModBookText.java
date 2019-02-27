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

		this.drawTextSplit(mc.fontRenderer, I18n.format(LibMisc.MODID + "." + text + cp), left+20, top+20,  guiWidth-40, Color.WHITE.getRGB());		   	
	}
}
