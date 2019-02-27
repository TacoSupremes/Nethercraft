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


public class GuiModBookText extends GuiScreen{
	
	
	public static final ResourceLocation texture = new ResourceLocation("nethercraft:textures/gui/book.png");

	
	int guiWidth = 146;
	int guiHeight = 180;
	int left, top, cp, pc; // cp = current page pC = page count
	
	
	String text;
	
	EntryText e;  
	
	public GuiModBookText(EntryText e)
	{
		text = e.getName();
		pc = e.getPageCount();
		cp = 1; 
		this.e = e;
	}
	
	@Override
	public final void initGui() 
	{
		left = width / 2 - guiWidth / 2;
		top = height / 2 - guiHeight / 2;	
		
		if(e.getParent() != null)
			this.buttonList.add(new GuiLabelButton(GuiHandler.getEntryFromName(e.getParent().getName()).getID(), left+guiWidth/2, top+guiHeight-20, 100, 20, I18n.format(LibMisc.MODID + "." + "back")));
	
		if(e.getNextEntry() != null)
		
	}

	public int getGuiHeight() {
		return guiHeight;
	}

	public void setGuiHeight(int guiHeight) {
		this.guiHeight = guiHeight;
	}

	public String getText() {
		return text;
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight);
		
		String s = I18n.format(LibMisc.MODID + "." + text);
		
		this.drawCenteredString(fontRenderer, s, left+guiWidth/2, top+10, Color.WHITE.getRGB());

		this.drawTextSplit(mc.fontRenderer, I18n.format(LibMisc.MODID + "." + text + cp), left+20, top+20,  guiWidth-40, Color.WHITE.getRGB());		   
		
		super.drawScreen(par1, par2, par3);

		
	}
	
	
	protected void actionPerformed(GuiButton gb) 
	{
		
		Minecraft.getMinecraft().player.openGui(Nethercraft.instance, gb.id, Minecraft.getMinecraft().player.world, 0, 0, 0);	
	
		System.out.println("opened" + gb.id);
	}
	
	
public static void drawTextSplit(FontRenderer f, String s,  int x, int y,  int trim,  int color){
		
		if(s.isEmpty() || s == "")
			return;
		
		if(f.getStringWidth(s) < trim){
			f.drawString(s, x, y, color);
			return;
		}
		
		List<String> toDraw = new ArrayList<String>();
		
		String cs = "";
		char[] ca = s.toCharArray();
		int space = -1;
	
		for(int i = 0; i<s.length(); i++){
			
			cs +=ca[i];
			
			if(ca[i] == ' ')
				space = i;
			
			if(f.getStringWidth(cs) >= trim){
				if(ca[i] != ' '){
					if(space != -1){
					for(int d = i; d> space; d--){
					StringBuilder sb = new StringBuilder(cs);
					sb.deleteCharAt(cs.length()-1);
					cs = sb.toString();
					}
					}
					
				}
					
				toDraw.add(cs);
				cs = "";
				i=space;
			}
		
		}
		
		toDraw.add(cs);
		
		for(int i = 0; i< toDraw.size();i++){
			f.drawString(toDraw.get(i), x, y+i*f.FONT_HEIGHT, color);
			
		}
		
	}

}
