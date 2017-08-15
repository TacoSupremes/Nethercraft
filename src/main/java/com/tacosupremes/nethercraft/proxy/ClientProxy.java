package com.tacosupremes.nethercraft.proxy;

import com.tacosupremes.nethercraft.common.blocks.ModBlocks;
import com.tacosupremes.nethercraft.common.items.ModItems;
import com.tacosupremes.nethercraft.common.utils.ClientStuff;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.Item;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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

}
