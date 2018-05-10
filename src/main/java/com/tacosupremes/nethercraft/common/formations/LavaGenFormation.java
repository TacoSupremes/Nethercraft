package com.tacosupremes.nethercraft.common.formations;

import java.util.List;

import com.tacosupremes.nethercraft.common.blocks.tiles.TileFormationBase;
import com.tacosupremes.nethercraft.common.blocks.tiles.power.IGenerator;
import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LavaGenFormation implements IGenFormation 
{

	@Override
	public Block[] getBlocks() 
	{
		Block n = Blocks.NETHERRACK;
		Block o = Blocks.OBSIDIAN;
		Block b = Blocks.NETHER_BRICK;
		Block a = Blocks.AIR;
		
		return new Block[]
				{
				 b,o,n,o,b,
				 o,a,b,a,o,
				 n,b,a,b,n,
				 o,a,b,a,o,
				 b,o,n,o,b						
				};
	}
	
	

	@Override
	public Vector3 getOffset()
	{	
		return Vector3.down;
	}

	@Override
	public int getMaxPower() 
	{
		return 500;
	}

	@Override
	public void generatePower(World w, BlockPos pos, NBTTagCompound nbt, TileFormationBase te) 
	{
		
		if(w.getWorldTime() % 60 != 0)
				return;
			
		for(int xD = -1; xD <= 1; xD++)
		{
			for(int zD = -1; zD <= 1; zD++)
			{
				if(xD == 0 || zD == 0)
					continue;
				
				BlockPos pos_  = pos.add(xD, -1, zD);
				
				if(w.getBlockState(pos_).getBlock() == Blocks.LAVA)
				{
					w.setBlockToAir(pos_);
					te.power += 100;
					return;
				}
			}
		}
		
	}

}