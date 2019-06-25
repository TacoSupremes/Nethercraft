package com.tacosupremes.nethercraft.common.formations;

import com.tacosupremes.nethercraft.common.blocks.tiles.TileFormationBase;
import com.tacosupremes.nethercraft.common.utils.BlockUtils;
import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.block.Block;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NetherWartFarm implements IConsumerFormation {

	@Override
	public ItemStack[] getBlocks() 
	{
	
		ItemStack n = new ItemStack(Blocks.SOUL_SAND);
		
		ItemStack a = new ItemStack(Blocks.OBSIDIAN);
		
		ItemStack b = new ItemStack(Blocks.NETHER_WART_BLOCK);
				
		return new ItemStack[]
		{
				b,n,n,n,b,						 
				n,b,n,b,n,				
				n,n,a,n,n,				 
				n,b,n,b,n,			
				b,n,n,n,b
		};
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
		
		for(int xD = -getRange(); xD <= getRange(); xD++)
		{
			for(int zD = -getRange(); zD <= getRange(); zD++)
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
						BlockUtils.spawnParticle(EnumParticleTypes.FLAME, w, pos_);
						System.out.println("Wart Farmed");
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

	@Override
	public int getRange() 
	{	
		return 2;
	}

	@Override
	public String getName() 
	{
		return "nether_wart";
	}



}
