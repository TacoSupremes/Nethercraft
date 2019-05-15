package com.tacosupremes.nethercraft.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;

public class GuiModBookRecipe extends GuiModBook {
	
	private ItemStack is;
	private ItemStack[] rec;
	public GuiModBookRecipe(EntryRecipe e) 
	{
		super(e);	
		this.is = e.getItemStack();
		this.rec = e.getRecipe();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight);
		
		String s = is.getDisplayName();
		
		this.drawCenteredString(fontRenderer, s, left+guiWidth/2, top+10, Color.WHITE.getRGB());	
			
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
			RenderHelper.enableGUIStandardItemLighting();
			
			int j = 0, k = 0;
					
			for(int i = 0; i < rec.length; i++)
			{	
				int m = left + guiWidth / 2 - 24 + 16 * j;
				int n = top + 40 + 16 * k;
			
				Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(rec[i], m, n);	
				
				j++;
				
				if(j == 3)
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
				
				int m = left + guiWidth / 2 - 24 + 16 * j;
				int n = top + 40 + 16 * k;
				
				if(mouseX > m && mouseX < m + 16 && mouseY > n && mouseY < n + 16)
					this.renderToolTip(rec[i], mouseX-16, mouseY-16);
				
				j++;
				
				if(j == 3)
				{
					j = 0;
					k++;
				}		
			}
		}
	}
}
