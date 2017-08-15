package com.tacosupremes.nethercraft.common.blocks;

import com.tacosupremes.nethercraft.common.blocks.tiles.TileFormationBase;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFormationBase extends BlockModContainer {

	public BlockFormationBase() 
	{
		super(Material.ROCK, "formationBase");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new TileFormationBase();
	}

	@Override
	protected Class<? extends TileEntity> tile() 
	{
		return TileFormationBase.class;
	}
	
}
