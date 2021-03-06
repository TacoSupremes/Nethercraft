package com.tacosupremes.nethercraft.common.blocks;

import java.util.ArrayList;
import java.util.List;

import com.tacosupremes.nethercraft.Nethercraft;
import com.tacosupremes.nethercraft.common.items.IRecipeGiver;
import com.tacosupremes.nethercraft.common.items.ModItems;
import com.tacosupremes.nethercraft.common.utils.ProxyRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ModBlocks
{

	public static List<Block> blocks = new ArrayList<Block>();

	public static Block formationBase;
	
	public static Block node;
	
	public static Block glowstoneCrop;
	
	public static Block fire;

	public static void preInit()
	{
		formationBase = new BlockFormationBase();

		node = new BlockNode();

		glowstoneCrop = new BlockGlowstoneCrop();
		
		fire = new BlockFire();
	}
	
	public static void register()
	{
		for (Block i : blocks)
		{
			if(Nethercraft.config.isBlockEnabled(i))
			{		
				ProxyRegistry.register(i);
				ProxyRegistry.register(new ItemBlock(i).setRegistryName(i.getRegistryName()));	
			}
		}
		
	}

	public static void registerRenders()
	{

		for (Block i : blocks)
		{
			ModItems.registerItemRender(Item.getItemFromBlock(i), 0);
		}

	}
	
}
