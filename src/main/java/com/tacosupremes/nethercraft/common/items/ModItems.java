package com.tacosupremes.nethercraft.common.items;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

import com.tacosupremes.nethercraft.Nethercraft;
import com.tacosupremes.nethercraft.common.lib.LibMisc;
import com.tacosupremes.nethercraft.common.utils.ProxyRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class ModItems
{

	public static List<ItemMod> items = new ArrayList<ItemMod>();

	public static List<Item> nitems = new ArrayList<Item>();

	public static Item wand;
	
	public static Item firePickaxe;
	
	public static Item fireSword;
	
	public static Item netherWalker;
	
	public static Item netherScythe;
	
	public static Item lavaWalker;
	
	public static Item book;
	
	public static Item netherGlasses;
	
	public static Item blazeChestPlate;
	
	public static Item ghastCannon;
	
	public static Item portal;
	
	public static Item fire;
	
	public static Item blazeIngot;
	
	
	//hellcats because why not
	
	//nether glasses to see range of formations 
	// to allow for discovery of dungeons,???
		
	public static void preInit()
	{
		wand = new ItemWand();	
		
		blazeIngot = new ItemBlazeIngot();
		
		firePickaxe = new ItemFirePickaxe();
		
		fireSword = new ItemFireSword();
		
		netherWalker = new ItemNetherWalker();
		
		netherScythe = new ItemNetherScythe();
		
		netherGlasses = new ItemNetherGlasses();
		
		blazeChestPlate = new ItemBlazeChestPlate();
		
		ghastCannon = new ItemGhastCannon();
		
		book = new ItemModBook();
		
		portal = new ItemCloneBlock(Blocks.PORTAL);
		
		fire = new ItemCloneBlock(Blocks.FIRE);
	}
	
	public static void register()
	{
		for(ItemMod i : items)
		{		
			if(Nethercraft.config.isItemEnabled(i))
				ProxyRegistry.register(i);
		}
		
		for(Item i : nitems)
		{		
			if(Nethercraft.config.isItemEnabled(i))
				ProxyRegistry.register(i);
		}
	}

	public static void registerRenders()
	{

		for (ItemMod i : items)
		{
			
			if (i.meta != 0)
			{
				if (i.hasOneModel())
				{
					for (int i2 = 0; i2 < i.meta + 1; i2++)
						registerItemRenderSameModel(i, i2);
					
					if (i.getColor() != null) 
						Minecraft.getMinecraft().getItemColors().registerItemColorHandler(i.getColor(), i);
					
					continue;
				}

				ResourceLocation[] s = new ResourceLocation[i.meta + 1];

				for (int i2 = 0; i2 < i.meta + 1; i2++)
					s[i2] = new ResourceLocation(i.getRegistryName().toString() + (i2 == 0 ? "" : i2));

				ModelBakery.registerItemVariants(i, s);

				if (!i.skipVariants())
				{
					for (int i2 = 0; i2 <= i.meta; i2++)
						ModItems.registerItemRender(i, i2);
				}
			}

			if (i.meta == 0 || i.skipVariants())
				registerItemRender(i, 0);
			
			if (i.getColor() != null) 	
				Minecraft.getMinecraft().getItemColors().registerItemColorHandler(i.getColor(), i);
		}

		for (Item i : nitems)
			registerItemRender(i, 0);
	}

	public static void registerItemRender(Item i, int meta)
	{
		if (i == null)
			return;

		ModelLoader.setCustomModelResourceLocation(i, meta, new ModelResourceLocation(i.getRegistryName() + (meta == 0 ? "" : String.valueOf(meta)), "inventory"));
	}

	public static void registerItemRenderSameModel(Item i, int meta)
	{
		if (i == null)
			return;

		ModelLoader.setCustomModelResourceLocation(i, meta, new ModelResourceLocation(i.getRegistryName(), "inventory"));
	}

	public static void registerItemAllMeta(Item item, int range)
	{
		registerItemMetas(item, range, i -> item.getRegistryName().getResourcePath());
	}

	public static void registerItemAppendMeta(Item item, int maxExclusive, String loc)
	{
		registerItemMetas(item, maxExclusive, i -> loc + i);
	}

	public static void registerItemMetas(Item item, int maxExclusive, IntFunction<String> metaToName)
	{
		for (int i = 0; i < maxExclusive; i++)
		{
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(LibMisc.MODID + ":" + metaToName.apply(i), "inventory"));
		}
	}
	
}
