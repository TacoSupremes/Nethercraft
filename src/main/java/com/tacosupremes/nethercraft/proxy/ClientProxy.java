package com.tacosupremes.nethercraft.proxy;

import java.util.List;

import com.tacosupremes.nethercraft.common.blocks.ModBlocks;
import com.tacosupremes.nethercraft.common.items.ModItems;
import com.tacosupremes.nethercraft.common.utils.ClientStuff;
import com.tacosupremes.nethercraft.common.utils.ParticleFlameTornado;
import com.tacosupremes.nethercraft.common.utils.PowerParticle;
import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
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
	public void powerFX(double x, double y, double z, List<Vector3> list)  
	{
		PowerParticle wisp = new PowerParticle(Minecraft.getMinecraft().world, x, y, z, 0, 1.0F, 0, list);
		
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
	
	@Override
	public boolean playerWearingItem(Item wand, EntityEquipmentSlot slot) 
	{
		
		if(Minecraft.getMinecraft().player == null)
			return false;
		
		ItemStack is = Minecraft.getMinecraft().player.getItemStackFromSlot(slot);
		
		return is != null && is.getItem() == wand;
	}

}
