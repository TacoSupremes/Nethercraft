package com.tacosupremes.nethercraft.gui;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.tacosupremes.nethercraft.Nethercraft;
import com.tacosupremes.nethercraft.common.items.IRecipeGiver;
import com.tacosupremes.nethercraft.common.items.RecipeType;
import com.tacosupremes.nethercraft.common.lib.LibMisc;
import com.tacosupremes.nethercraft.gui.entry.EntryItem;
import com.tacosupremes.nethercraft.gui.entry.EntryRecipe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;

public class GuiModBookRecipe extends GuiModBook {
	
	private ItemStack is;
	private ItemStack[] rec;
	private RecipeType recType;
	
	public GuiModBookRecipe(EntryRecipe e) 
	{
		super(e);	
		this.is = e.getItemStack();
		this.rec = e.getRecipe(is.getItemDamage());
		this.recType = e.getRecipeType();
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
				
				if(rec[i] != ItemStack.EMPTY)
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
				
				if(mouseX > m && mouseX < m + 16 && mouseY > n && mouseY < n + 16  && rec[i] != ItemStack.EMPTY)
					this.renderToolTip(rec[i], mouseX - 8, mouseY+8);
				
				j++;
				
				if(j == 3)
				{
					j = 0;
					k++;
				}		
			}
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException 
	{
		
		int j = 0, k = 0;
		
		for(int i = 0; i < rec.length; i++)
		{	
			int m = left + guiWidth / 2 - 24 + 16 * j;
			int n = top + 40 + 16 * k;
			
			EntryItem dest = (EntryItem) GuiHandler.getEntryFromStack(rec[i]);
			
			if(mouseX > m && mouseX < m + 16 && mouseY > n && mouseY < n + 16 && rec[i] != ItemStack.EMPTY && dest != null)
			{
				
				dest.setTempParent(this.e);
				Minecraft.getMinecraft().player.openGui(Nethercraft.instance, dest.getID(), Minecraft.getMinecraft().player.world, 0, 0, 0);	
			}
		//	Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(rec[i], m, n);	
			
			
			j++;
			
			if(j == 3)
			{
				j = 0;
				k++;
			}
			
		}
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
	}
	
	protected void renderToolTip(ItemStack stack, int x, int y)
	{
		FontRenderer font = stack.getItem().getFontRenderer(stack);
		net.minecraftforge.fml.client.config.GuiUtils.preItemToolTip(stack);
		this.drawHoveringText(this.getItemToolTip(stack) + (GuiHandler.getEntryFromStack(stack) != null ? I18n.format(LibMisc.MODID + "." + "click") : ""), x, y);
		net.minecraftforge.fml.client.config.GuiUtils.postItemToolTip();	
	}
}
