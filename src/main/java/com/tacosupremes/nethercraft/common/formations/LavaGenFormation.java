package com.tacosupremes.nethercraft.common.formations;

import java.util.List;

import com.tacosupremes.nethercraft.common.blocks.tiles.TileFormationBase;
import com.tacosupremes.nethercraft.common.blocks.tiles.power.IGenerator;
import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LavaGenFormation implements IGenFormation 
{

	@Override
	public ItemStack[] getBlocks() 
	{
		ItemStack n = new ItemStack(Blocks.NETHERRACK);
		ItemStack o = new ItemStack(Blocks.OBSIDIAN);
		ItemStack b = new ItemStack(Blocks.NETHER_BRICK);
		ItemStack i = new ItemStack(Blocks.IRON_BLOCK);
		ItemStack a = ItemStack.EMPTY;
		
		return new ItemStack[]
				{
				 b,o,n,o,b,
				 o,a,b,a,o,
				 n,b,i,b,n,
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



	@Override
	public int getRange() {
	
		return -1;
	}



	@Override
	public String getName() 
	{
		return "lava_gen";
	}

}