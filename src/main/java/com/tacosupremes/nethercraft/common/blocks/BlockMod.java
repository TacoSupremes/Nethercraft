package com.tacosupremes.nethercraft.common.blocks;

import com.tacosupremes.nethercraft.Nethercraft;
import com.tacosupremes.nethercraft.common.items.IRecipeGiver;
import com.tacosupremes.nethercraft.common.items.RecipeType;
import com.tacosupremes.nethercraft.common.lib.LibMisc;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public abstract class BlockMod extends Block implements IRecipeGiver
{
	
	public BlockMod(Material materialIn, String s)
	{
		super(materialIn);
		this.setUnlocalizedName(s);
		if(addToTab())
			this.setCreativeTab(Nethercraft.tab);
		ModBlocks.blocks.add(this);
	}

	@Override
	public Block setUnlocalizedName(String name)
	{
		super.setUnlocalizedName(name);
		setRegistryName(LibMisc.MODID + ":" + name);
		return this;
	}
	
	public boolean addToTab()
	{
		return true;
	}

	@Override
	public RecipeType getType(int meta) 
	{
		
		return RecipeType.Shaped;
	}

}