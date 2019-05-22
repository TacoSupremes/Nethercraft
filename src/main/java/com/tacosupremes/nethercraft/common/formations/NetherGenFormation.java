package com.tacosupremes.nethercraft.common.formations;

import com.tacosupremes.nethercraft.common.blocks.tiles.TileFormationBase;
import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NetherGenFormation implements IGenFormation
{

	@Override
	public void generatePower(World w, BlockPos pos, NBTTagCompound nbt, TileFormationBase te) 
	{			
		if(w.getWorldTime() %20 != 0)
			return;

		for(int y = 1; y < pos.getY(); y++)
		{
			for(int x = pos.getX() - getRange(); x <= pos.getX() + getRange(); x++)
			{
				for(int z = pos.getZ() - getRange(); z <= pos.getZ() + getRange(); z++)
				{				
					BlockPos pos_ = new BlockPos(x, y, z);
					
					if(w.getBlockState(pos_).getBlockHardness(w, pos_) > 0)
					{
						if(w.getTileEntity(pos_) == null && w.getBlockState(pos_).getBlock() != Blocks.NETHERRACK)
						{			
							w.setBlockState(pos_, Blocks.NETHERRACK.getDefaultState(), 3);	
												
							System.out.println("GENERATOR" + te.power);
							
							return;
						}
							
					}
				}
			}
		}
		nbt.setBoolean("DONE", true);	
	}

	

	@Override
	public ItemStack[] getBlocks() 
	{
		ItemStack f = new ItemStack(Blocks.FIRE);
		
		ItemStack a = ItemStack.EMPTY;
		
		return new ItemStack[]
		{
		 f,a,f,a,f,
		 a,a,a,a,a,
		 f,a,a,a,f,
		 a,a,a,a,a,
		 f,a,f,a,f				
		};
	}



	@Override
	public Vector3 getOffset() 
	{
		return Vector3.zero;
	}



	@Override
	public int getMaxPower() 
	{
		return 1000;
	}



	@Override
	public int getRange() 
	{
		return 16;
	}



	@Override
	public String getName() 
	{
		return "nether_gen";
	}

}
