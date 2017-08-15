package com.tacosupremes.nethercraft.common.utils;

import com.tacosupremes.nethercraft.Nethercraft;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientStuff
{

	@SubscribeEvent
	public static void onRegister(ModelRegistryEvent event)
	{

		Nethercraft.proxy.registerRenders();
	}

}
