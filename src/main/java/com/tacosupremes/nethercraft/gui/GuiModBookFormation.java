package com.tacosupremes.nethercraft.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.tacosupremes.nethercraft.common.formations.IFormation;
import com.tacosupremes.nethercraft.common.lib.LibMisc;
import com.tacosupremes.nethercraft.common.utils.Vector3;
import com.tacosupremes.nethercraft.gui.entry.EntryFormation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class GuiModBookFormation extends GuiModBook
{
	private IFormation f;
	private String name;
	private ItemStack[] rec;
	public GuiModBookFormation(EntryFormation e) 
	{
		super(e);
		this.f = e.getFormation();
		this.rec = f.getBlocks();
		this.name = e.getLocalisedName();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		GL11.glColor4f(1F, 1F, 1F, 1F);
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight);
		
		this.drawCenteredString(fontRenderer, name, left + guiWidth / 2, top + 10, Color.WHITE.getRGB());	
			
		for (int i = 0; i < this.buttonList.size(); ++i)
        {
            ((GuiButton)this.buttonList.get(i)).drawButton(this.mc, mouseX, mouseY, partialTicks);
        }

        for (int j = 0; j < this.labelList.size(); ++j)
        {
            ((GuiLabel)this.labelList.get(j)).drawLabel(this.mc, mouseX, mouseY);
        }
        
    	if(rec != null)
		{
    		// this should be placed n blocks below (offset) 
    		if(f.getOffset() != Vector3.zero)
    			this.drawTextSplit(mc.fontRenderer, "" + 
    		I18n.format(LibMisc.MODID + "." + "place") + 
    		(int)(-1 * f.getOffset().y) + " " + 
    		I18n.format(LibMisc.MODID + "." + 
    		((Math.abs(f.getOffset().y)  == 1.0) ? "block" : "blocks")) + 
    		" " + I18n.format(LibMisc.MODID + "." + "below"), 
    		left + 15, top + 60 + 16 * ((int)Math.sqrt(rec.length)-1),  guiWidth - 25, Color.WHITE.getRGB());		   	
    		else
    			this.drawTextSplit(mc.fontRenderer, "" + I18n.format(LibMisc.MODID + "." + "levelto"), (left + 15), top + 60 + 16 * ((int)Math.sqrt(rec.length)-1),  guiWidth - 25, Color.WHITE.getRGB());		   	
        			
			RenderHelper.enableGUIStandardItemLighting();
			
			int j = 0, k = 0;
					
			for(int i = 0; i < rec.length; i++)
			{	
				int m = left + guiWidth / 2 - 24 * ((int) (Math.sqrt(rec.length) - 1) / 2) + 16 * j + 8;
				int n = top + 40 + 16 * k;
			
				Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(rec[i], m, n);	
				
				j++;
				
				if(j == (int)Math.sqrt(rec.length))
				{
					j = 0;
					k++;
				}
				
			}
			
			RenderHelper.disableStandardItemLighting();
			
			 j = 0;
			 
			 k = 0;
			
			for(int i = 0; i < rec.length; i++)
			{
				
				int m = left + guiWidth / 2 - 24 * ((int) (Math.sqrt(rec.length) - 1) / 2) + 16 * j + 8;
				int n = top + 40 + 16 * k;
				
				if(mouseX > m && mouseX < m + 16 && mouseY > n && mouseY < n + 16)
					this.renderToolTip(rec[i], mouseX - 8, mouseY);
				
				j++;
				
				if(j == (int)Math.sqrt(rec.length))
				{
					j = 0;
					k++;
				}		
			}
		}
	}

}
