package com.tacosupremes.nethercraft.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.tacosupremes.nethercraft.common.lib.LibMisc;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockUtils
{

	public static IBlockState fromItemStack(ItemStack is)
	{

		Block b = Block.getBlockFromItem(is.getItem());

		if (b == null)
			return null;

		return b.getStateFromMeta(is.getItemDamage());

	}

	public static ItemStack toItemStack(IBlockState s)
	{
		return new ItemStack(s.getBlock(), 1, s.getBlock().getMetaFromState(s));
	}

	public static int getMeta(World w, BlockPos pos)
	{
		return w.getBlockState(pos).getBlock().getMetaFromState(w.getBlockState(pos));
	}

	public static boolean equals(IBlockState i, IBlockState i2)
	{
		return i.getBlock() == i2.getBlock() && i.getBlock().getMetaFromState(i) == i2.getBlock().getMetaFromState(i2);
	}

	public static List<BlockPos> getConnectedBlocks(World w, BlockPos start, int limit)
	{
		List<BlockPos> l = new ArrayList<BlockPos>();

		List<String> checked = new ArrayList<String>();

		List<BlockPos> toCheck = new ArrayList<BlockPos>();

		IBlockState ib = w.getBlockState(start);

		l.add(start);

		toCheck.add(start);

		checked.add(start.toString());

		while (!toCheck.isEmpty())
		{

			BlockPos pos = toCheck.remove(0);

			for (EnumFacing f : EnumFacing.VALUES)
			{

				if (l.size() >= limit)
					return l;

				BlockPos pos_ = pos.add(f.getDirectionVec());

				IBlockState ib_ = w.getBlockState(pos_);

				if (BlockUtils.equals(ib, ib_) && !checked.contains(pos_.toString()))
				{

					l.add(pos_);

					checked.add(pos_.toString());

					toCheck.add(pos_);

				}

			}
		}

		return l;
	}

	public static List<BlockPos> getConnectedLogs(World w, BlockPos start, int limit)
	{

		List<BlockPos> l = new ArrayList<BlockPos>();

		List<String> checked = new ArrayList<String>();

		List<BlockPos> toCheck = new ArrayList<BlockPos>();

		IBlockState ib = w.getBlockState(start);

		l.add(start);

		toCheck.add(start);

		checked.add(start.toString());

		while (!toCheck.isEmpty())
		{

			BlockPos pos = toCheck.remove(0);

			for (int x = -2; x <= 2; x++)
			{

				for (int y = -2; y <= 2; y++)
				{

					for (int z = -2; z <= 2; z++)
					{

						if (l.size() >= limit)
							return l;

						BlockPos pos_ = pos.add(x, y, z);

						IBlockState ib_ = w.getBlockState(pos_);

						if (ib_.getBlock().isWood(w, pos_) && !checked.contains(pos_.toString()))
						{

							l.add(pos_);

							checked.add(pos_.toString());

							toCheck.add(pos_);

						}
					}
				}
			}
		}

		return l;
	}

	public static List<BlockPos> getConnectedOres(World w, BlockPos start, int limit)
	{

		List<BlockPos> l = new ArrayList<BlockPos>();

		List<String> checked = new ArrayList<String>();

		List<BlockPos> toCheck = new ArrayList<BlockPos>();

		IBlockState ib = w.getBlockState(start);

		l.add(start);

		toCheck.add(start);

		checked.add(start.toString());

		while (!toCheck.isEmpty())
		{

			BlockPos pos = toCheck.remove(0);

			for (int x = -2; x <= 2; x++)
			{

				for (int y = -2; y <= 2; y++)
				{

					for (int z = -2; z <= 2; z++)
					{

						if (l.size() >= limit)
							return l;

						BlockPos pos_ = pos.add(x, y, z);

						IBlockState ib_ = w.getBlockState(pos_);

						if (LibMisc.Ores.isOre(ib_) && !checked.contains(pos_.toString()))
						{

							l.add(pos_);

							checked.add(pos_.toString());

							toCheck.add(pos_);

						}
					}
				}
			}
		}

		return l;
	}
	
	public static void drawLine(World w, Vector3 start, Vector3 end, EnumParticleTypes type)
	{
		  
		 double i = 0;	  
		  
		 double vectorLength = Math.sqrt(Math.pow((start.getX()-end.getX()),2)+Math.pow((start.getY()-end.getY()),2)+Math.pow((start.getZ()-end.getZ()),2));
		 
		 while (i <= vectorLength)
		 {
			 
			 double xD = end.getX() - start.getX();
			 double yD = end.getY() - start.getY();
			 double zD = end.getZ() - start.getZ();
			 
			 double dL = i / vectorLength;
			 
			 xD *= dL;
			 yD *= dL;
			 zD *= dL;
			
			 w.spawnParticle(type, start.getX()+xD,start.getY()+yD,start.getZ()+zD, 0, 0, 0, 0);
				  
			 i++;
		 }  
		  
	  }

}
