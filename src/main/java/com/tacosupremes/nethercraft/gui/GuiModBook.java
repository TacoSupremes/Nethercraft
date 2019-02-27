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

public class GuiModBook extends GuiScreen {

	public static final ResourceLocation texture = new ResourceLocation("nethercraft:textures/gui/book.png");
	
	
	private Entry e;
	
	public GuiModBook(Entry e)
	{
		this.e = e;
	}
	
	int guiWidth = 146;
	int guiHeight = 180;
	int left, top;

	public boolean canInput = false;
	
	@Override
	public final void initGui()
	{
	
		left = width / 2 - guiWidth / 2;
		top = height / 2 - guiHeight / 2;
						
		if(e.getParent() != null)
			this.buttonList.add(new GuiLabelButton(GuiHandler.getEntryFromName(e.getParent().getName()).getID(), left+guiWidth/2, top+guiHeight-20, 100, 20, I18n.format(LibMisc.MODID + "." + "back")));
			
	}

	@Override
	public void updateScreen() {}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight);
		
		String s = I18n.format(LibMisc.MODID + "." + e.getName());
		
		this.drawCenteredString(fontRenderer, s, left+guiWidth/2, top+10, Color.WHITE.getRGB());


		super.drawScreen(par1, par2, par3);

		
	}

	protected void actionPerformed(GuiButton gb) 
	{	
		Minecraft.getMinecraft().player.openGui(Nethercraft.instance, gb.id, Minecraft.getMinecraft().player.world, 0, 0, 0);	
	
		System.out.println("opened" + gb.id);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	public void drawTooltip(ItemStack stack, int x, int y){
		this.renderToolTip(stack, x, y);
	}
	
	
	public static boolean hasEntry(ItemStack item)
	{		
		if(item == null)
			return false;
		
		String s = "runomancy."+item.getUnlocalizedName().substring(5)+".entry";
		
		return I18n.format(s) != s;	
	}
	
	@Override
	 protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	    {
	        if (mouseButton == 0)
	        {
	        	
	        	
	            for (int i = 0; i < this.buttonList.size(); ++i)
	            {
	                GuiButton guibutton = (GuiButton)this.buttonList.get(i);

	                if (guibutton.mousePressed(this.mc, mouseX, mouseY))
	                {
	                    net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre event = new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre(this, guibutton, this.buttonList);
	                    if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
	                        break;
	                    guibutton = event.getButton();
	                    this.selectedButton = guibutton;
	                    guibutton.playPressSound(this.mc.getSoundHandler());
	                    this.actionPerformed(guibutton);
	                    if (this.equals(this.mc.currentScreen))
	                        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Post(this, event.getButton(), this.buttonList));
	                
	                return;
	                }
	            }
	        }
	    }
		
}