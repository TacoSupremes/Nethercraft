package com.tacosupremes.nethercraft;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.tacosupremes.nethercraft.common.blocks.ModBlocks;
import com.tacosupremes.nethercraft.common.enchantments.ModEnchantments;
import com.tacosupremes.nethercraft.common.formations.Formations;
import com.tacosupremes.nethercraft.common.items.ModItems;
import com.tacosupremes.nethercraft.common.lib.LibMisc;
import com.tacosupremes.nethercraft.common.utils.EnchantUtils;
import com.tacosupremes.nethercraft.common.utils.OfflinePlayerUtils;
import com.tacosupremes.nethercraft.gui.GuiHandler;
import com.tacosupremes.nethercraft.proxy.CommonProxy;
import com.tacosupremes.nethercraft.recipes.ModRecipes;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = LibMisc.MODID, version = LibMisc.VERSION)
public class Nethercraft
{
	public static CreativeTabs tab;

	public static NCEventHandler events;

	@SidedProxy(clientSide = LibMisc.CLIENTPROXY, serverSide = LibMisc.COMMONPROXY)
	public static CommonProxy proxy;

	@Instance(LibMisc.MODID)
	public static Nethercraft instance;

	public static final Logger logger = LogManager.getLogManager().getLogger(LibMisc.MODID);

	//public static final ResourceLocation oldPagesLoot = register("old_pages_loot");
	
	public static ModConfig config;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ModEnchantments.preInit();

		tab = new WSTab();

		proxy.preInit(event);

		ModItems.preInit();

		ModBlocks.preInit(); 	
		
		config = new ModConfig();
		
		config.preInit(event);
			
		ModBlocks.register();
		
		ModItems.register();

		ModRecipes.preInit();

		LibMisc.Ores.preInit();
		
		Formations.preInit();

	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		events = new NCEventHandler();

		LibMisc.Ores.init();

		MinecraftForge.EVENT_BUS.register(events);

		MinecraftForge.EVENT_BUS.register(OfflinePlayerUtils.class);

		NetworkRegistry.INSTANCE.registerGuiHandler(Nethercraft.instance, new GuiHandler());
	}

	public class WSTab extends CreativeTabs
	{

		public WSTab()
		{
			super(CreativeTabs.getNextID(), LibMisc.MODID);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem()
		{
			return new ItemStack(Blocks.RED_NETHER_BRICK);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void displayAllRelevantItems(NonNullList<ItemStack> l)
		{
			super.displayAllRelevantItems(l);

			for (Enchantment e : ModEnchantments.enchants)
			{

				if(!config.isEnchantEnabled(e))
					continue;
				
				ItemStack is = new ItemStack(Items.ENCHANTED_BOOK, 1, 0);

				for (int i = 1; i <= e.getMaxLevel(); i++)
				{

					ItemStack is2 = is.copy();

					EnchantUtils.enchantItem(is2, e, i);

					l.add(is2);

				}
			}
		}

	}

	private static ResourceLocation register(String id)
	{
		return LootTableList.register(new ResourceLocation(LibMisc.MODID, id));
	}

}
