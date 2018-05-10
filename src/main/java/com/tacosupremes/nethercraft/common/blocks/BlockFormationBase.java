package com.tacosupremes.nethercraft.common.blocks;

import java.util.Random;

import com.tacosupremes.nethercraft.common.blocks.tiles.TileFormationBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
		if(!((TileFormationBase)worldIn.getTileEntity(pos)).isDone())
			return;
		
        for (int i = 0; i < 3; ++i)
        {
           
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX()+0.5D, pos.getY()+1.5D, pos.getZ()+0.5D, 0, 1, 0);
        }
    }
	
	
	
	
}
