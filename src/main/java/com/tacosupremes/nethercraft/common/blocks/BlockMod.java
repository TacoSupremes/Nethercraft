package com.tacosupremes.nethercraft.common.blocks;

import com.tacosupremes.nethercraft.Nethercraft;
import com.tacosupremes.nethercraft.common.lib.LibMisc;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockMod extends Block
{

	public BlockMod(Material materialIn, String s)
	{
		super(materialIn);
		this.setUnlocalizedName(s);
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

}