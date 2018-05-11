package com.tacosupremes.nethercraft.proxy;

import com.tacosupremes.nethercraft.common.utils.ProxyRegistry;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy
{

	public void registerRenders()
	{

	}

	public boolean isShiftDown()
	{
		return false;
	}

	public void preInit(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(ProxyRegistry.class);
	}
	

	public void flameTornadoFX(double x, double y, double z, double r) 
	{
		
	}

	public boolean playerHoldingItem(Item wand) 
	{
		return false;
	}

	

}
