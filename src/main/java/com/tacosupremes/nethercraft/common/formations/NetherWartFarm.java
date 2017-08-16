package com.tacosupremes.nethercraft.common.formations;

import com.tacosupremes.nethercraft.common.blocks.tiles.TileFormationBase;
import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NetherWartFarm implements IConsumerFormation {

	@Override
	public Block[] getBlocks() 
	{
	
		Block n = Blocks.SOUL_SAND;
		
		Block a = Blocks.AIR;
				
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
		
		//TODO FINISH
		
	}



}
