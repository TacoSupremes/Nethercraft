package com.tacosupremes.nethercraft.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.tacosupremes.nethercraft.common.lib.LibMisc;
import com.tacosupremes.nethercraft.gui.entry.EntryItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class GuiModBookItem extends GuiModBook
{

	private ItemStack is; // TODO CREATE RECIPE PAGES / ADD NEXTBUTTON TO GUIMODBOOK

	public GuiModBookItem(EntryItem e) 
	{
		super(e);
		this.is = e.getItemStack();
	}

	@Override
	public void initGuiFeatures()
	{

	}

	@Override
	public void updateScreen() 
	{
		
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight);
		
		String s = is.getDisplayName();
		
		this.drawCenteredString(fontRenderer, s, left + guiWidth / 2, top + 10, Color.WHITE.getRGB());
		
		RenderHelper.enableGUIStandardItemLighting();
		
		Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(is, left + guiWidth / 2 - 8, top + 20);
		
		RenderHelper.disableStandardItemLighting();
		
		this.drawTextSplit(mc.fontRenderer, I18n.format(is.getUnlocalizedName().replaceAll(".name", "") + ".lore"), left + 15, top + 40,  guiWidth - 25, Color.WHITE.getRGB());		   	
		
		for (int i = 0; i < this.buttonList.size(); ++i)
        {
            ((GuiButton)this.buttonList.get(i)).drawButton(this.mc, mouseX, mouseY, partialTicks);
        }

        for (int j = 0; j < this.labelList.size(); ++j)
        {
            ((GuiLabel)this.labelList.get(j)).drawLabel(this.mc, mouseX, mouseY);
        }
	}
	
	

}
