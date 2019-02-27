package com.tacosupremes.nethercraft.gui;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class GuiLabelButton extends GuiButton {

	private int trim;



	public GuiLabelButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, int trim) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.trim = trim;
		
	}
	
	public GuiLabelButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		trim = 0;
		//Minecraft.getMinecraft().fontRenderer.getStringWidth(displayString + 1);
		
	}
	
	
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.mouseDragged(mc, mouseX, mouseY);
            int j = Color.WHITE.getRGB();

            if (packedFGColour != 0)
            {
                j = packedFGColour;
            }
            else
            if (!this.enabled)
            {
                j = 10526880;
            }
            else if (this.hovered)
            {
                j = 16777120;
            }

           if(trim == 0)
        	   this.drawCenteredString(fontrenderer, this.displayString, this.x, this.y, j);
           else 
        	   GuiModBookText.drawTextSplit(fontrenderer, this.displayString, this.x, this.y, trim, j);
           // this.drawCenteredString(fontrenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, j);
        }
    }

}
