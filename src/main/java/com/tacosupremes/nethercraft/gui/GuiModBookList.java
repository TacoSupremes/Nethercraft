package com.tacosupremes.nethercraft.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.tacosupremes.nethercraft.Nethercraft;
import com.tacosupremes.nethercraft.common.lib.LibMisc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.resources.I18n;

public class GuiModBookList extends GuiModBook {
	
	private Entry[] l;

	public GuiModBookList(EntryList l)
	{
		super(l);
		this.l = l.getList();
	}

	@Override
	public void initGuiFeatures()
	{
		for(int i = 0; i<l.length; i++)
				this.buttonList.add(new GuiLabelButton(GuiHandler.getEntryFromName(l[i].getName()).getID(), left+guiWidth/2, top+25+15*i, I18n.format(LibMisc.MODID + "." + l[i].getName())));	
	}

		
}