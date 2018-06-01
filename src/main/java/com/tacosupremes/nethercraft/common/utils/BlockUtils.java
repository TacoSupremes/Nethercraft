package com.tacosupremes.nethercraft.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.tacosupremes.nethercraft.common.lib.LibMisc;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlockUtils
{

	public static ItemStack toItemStack(IBlockState s)
	{
		return toItemStack(s, 1);
	}
	
	public static ItemStack toItemStack(IBlockState s, int count)
	{
		return new ItemStack(s.getBlock(), count, s.getBlock().getMetaFromState(s));
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
	
	public static void drawLine(World w, BlockPos start, BlockPos end, EnumParticleTypes type)
	{
		 drawLine(w, Vector3.fromBlockPos(start).add(0.5D), Vector3.fromBlockPos(end).add(0.5D), type);
	}
	
	public static void drawLine(World w, Vector3 start, Vector3 end, EnumParticleTypes type)
	{
		  
		 double i = 0;	  
		  
		 double vectorLength = Math.sqrt(Math.pow((start.getX()-end.getX()),2)+Math.pow((start.getY()-end.getY()),2)+Math.pow((start.getZ()-end.getZ()),2));
		 
		 w.spawnParticle(type, end.getX(), end.getY(), end.getZ(), 0, 0, 0, 0);
		 
		 while (i <= vectorLength)
		 {
			 
			 double xD = end.getX() - start.getX();
			 double yD = end.getY() - start.getY();
			 double zD = end.getZ() - start.getZ();
			 
			 double dL = i / vectorLength;
			 
			 xD *= dL;
			 yD *= dL;
			 zD *= dL;			
	
			 w.spawnParticle(type, start.getX()+xD, start.getY()+yD, start.getZ()+zD, 0, 0, 0, 0);
				  
			 i++;
		 }  
		 
		 
			
	
		  
	  }
	
	public static void spawnParticle(World w, BlockPos pos, EnumParticleTypes type)
	{
		w.spawnParticle(type, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);
	}
	
	public static void spawnParticle(World w, Vector3 start, Vector3 end, EnumParticleTypes type, double speed)
	{
		Vector3 v = end.subtract(start).normalize().multiply(speed);
		
		w.spawnParticle(type, start.getX(), start.getY(), start.getZ(), v.getX(), v.getY(), v.getZ());
	}
	
	 public static RayTraceResult rayTrace(World worldIn, EntityPlayer playerIn, boolean useLiquids, double r)
	  {
	      float f = playerIn.rotationPitch;
	      float f1 = playerIn.rotationYaw;
	      double d0 = playerIn.posX;
	      double d1 = playerIn.posY + (double)playerIn.getEyeHeight();
	      double d2 = playerIn.posZ;
	      Vec3d vec3d = new Vec3d(d0, d1, d2);
	      float f2 = MathHelper.cos(-f1 * 0.017453292F - (float)Math.PI);
	      float f3 = MathHelper.sin(-f1 * 0.017453292F - (float)Math.PI);
	      float f4 = -MathHelper.cos(-f * 0.017453292F);
	      float f5 = MathHelper.sin(-f * 0.017453292F);
	      float f6 = f3 * f4;
	      float f7 = f2 * f4;
	      double d3 = r;
	      Vec3d vec3d1 = vec3d.addVector((double)f6 * d3, (double)f5 * d3, (double)f7 * d3);
	      return worldIn.rayTraceBlocks(vec3d, vec3d1, useLiquids, !useLiquids, false);
	  }


	public static RayTraceResult rayTrace2(World worldIn, EntityPlayer playerIn, double r)
	  {
	      float f = playerIn.rotationPitch;
	      float f1 = playerIn.rotationYaw;
	      double d0 = playerIn.posX;
	      double d1 = playerIn.posY + (double)playerIn.getEyeHeight();
	      double d2 = playerIn.posZ;
	      Vec3d vec3d = new Vec3d(d0, d1, d2);
	      float f2 = MathHelper.cos(-f1 * 0.017453292F - (float)Math.PI);
	      float f3 = MathHelper.sin(-f1 * 0.017453292F - (float)Math.PI);
	      float f4 = -MathHelper.cos(-f * 0.017453292F);
	      float f5 = MathHelper.sin(-f * 0.017453292F);
	      float f6 = f3 * f4;
	      float f7 = f2 * f4;
	      double d3 = r;
	      Vec3d vec3d1 = vec3d.addVector((double)f6 * d3, (double)f5 * d3, (double)f7 * d3);
	      return worldIn.rayTraceBlocks(vec3d, vec3d1, false, true, false);
	  }


	public static Entity rayTraceEntity(World w, EntityPlayer player, int r2){
		 
		 RayTraceResult r = rayTrace2(w, player, r2);
		 
		 if(r == null)
			 return null;
		 
		 List<Entity> e2 =	w.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(player.getPosition(), r.getBlockPos()).expand(1, 1, 1));
		 
		 if(e2.isEmpty())
			 return null;
		 
		 float d = -1;
		 int in = -1;
		 
		 for(int i = 0; i<e2.size(); i++){
			 
			 if(e2.get(i).getDistanceToEntity(player) < d || d == -1){
				 in = i;
				 d = e2.get(i).getDistanceToEntity(player);
			 }
		 }
		 
		 return e2.get(in);
	}
	
	public static List<BlockPos> diamond(World w, BlockPos pos, int r)
	{
		
		List<BlockPos> l = new ArrayList<BlockPos>(); 
		
		l.add(pos);
		
		if(r > 1)
		{
			for(int x = -1 * (r-1), z = -1 * (r-1); x < r-1; x++, z++)
			{
				if(x == 0 && z == 0)
					continue;
				
				l.add(pos.add(x, 0, z));
			}
		}
		
		return null;
	}
	

}
