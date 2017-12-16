package com.tacosupremes.nethercraft.common.formations;

import com.tacosupremes.nethercraft.common.blocks.tiles.TileFormationBase;
import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.block.Block;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NetherWartFarm implements IConsumerFormation {

	@Override
	public Block[] getBlocks() 
	{
	
		Block n = Blocks.SOUL_SAND;
		
		Block a = Blocks.OBSIDIAN;
				
		return new Block[]
				{
				n,n,n,n,n,						 
				n,n,n,n,n,				
				n,n,a,n,n,				 
				n,n,n,n,n,			
				n,n,n,n,n};
	}

	@Override
	public Vector3 getOffset()
	{
		return Vector3.down;
	}

	@Override
	public void usePower(World w, BlockPos pos, NBTTagCompound nbt, TileFormationBase te) 
	{
		if(te.getWorld().getWorldTime() % 100 != 0)
			return;	
		
		for(int xD = -2; xD <= 2; xD++)
		{
			for(int zD = -2; zD <= 2; zD++)
			{
				BlockPos pos_ = pos.add(xD, 0, zD);
				
				if(w.getBlockState(pos_).getBlock() == Blocks.NETHER_WART)
				{
					if(te.getPower() < 100)
						return;
					
					if(w.getBlockState(pos_).getValue(BlockNetherWart.AGE) == 3)
					{
						w.getBlockState(pos_).getBlock().dropBlockAsItem(w, pos_, w.getBlockState(pos_), 0);
						w.setBlockState(pos_, Blocks.NETHER_WART.getDefaultState());
						te.power -= 100;
					}
				
				}
			}
			
		}
		
	}

	@Override
	public int getMaxPower() 
	{	
		return 1000;
	}



}
