package com.tacosupremes.nethercraft.proxy;

import com.tacosupremes.nethercraft.common.blocks.ModBlocks;
import com.tacosupremes.nethercraft.common.items.ModItems;
import com.tacosupremes.nethercraft.common.utils.ClientStuff;
import com.tacosupremes.nethercraft.common.utils.ParticleFlameTornado;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy extends CommonProxy
{

	@Override
	public void registerRenders()
	{
		ModItems.registerRenders();
		ModBlocks.registerRenders();
	}

	@Override
	public boolean isShiftDown()
	{
		return Minecraft.getMinecraft().currentScreen != null ? GuiScreen.isShiftKeyDown() : false;
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);

	//	ClientRegistry.bindTileEntitySpecialRenderer(TileSharedEnderChest.class, new TileSharedEnderChestRenderer());

	//	ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(ModBlocks.sharedEnderChest), 0, TileSharedEnderChest.class);

		MinecraftForge.EVENT_BUS.register(ClientStuff.class);

	}
	
	
	@Override
	public void flameTornadoFX(double x, double y, double z, double r) {

		ParticleFlameTornado wisp = new ParticleFlameTornado(Minecraft.getMinecraft().world, x, y, z, r);
		
		Minecraft.getMinecraft().effectRenderer.addEffect(wisp);
	}
	@Override
	public boolean playerHoldingItem(Item wand) 
	{
		
		if(Minecraft.getMinecraft().player == null)
			return false;
		
		ItemStack m = Minecraft.getMinecraft().player.getHeldItemMainhand();
		
		ItemStack o = Minecraft.getMinecraft().player.getHeldItemOffhand();

		
		if(m != null && m.getItem() == wand)
			return true;
		
		if(o != null && o.getItem() == wand)
			return true;
		
		return false;
	}

}
