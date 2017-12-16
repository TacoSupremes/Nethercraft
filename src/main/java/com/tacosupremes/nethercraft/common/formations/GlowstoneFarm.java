package com.tacosupremes.nethercraft.common.formations;

import com.tacosupremes.nethercraft.common.blocks.BlockGlowstoneCrop;
import com.tacosupremes.nethercraft.common.blocks.ModBlocks;
import com.tacosupremes.nethercraft.common.blocks.tiles.TileFormationBase;
import com.tacosupremes.nethercraft.common.utils.BlockUtils;
import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GlowstoneFarm implements IConsumerFormation 
{

	@Override
	public Block[] getBlocks() 
	{
		Block g = Blocks.GLOWSTONE;
		
		Block n = Blocks.NETHERRACK;
		
		Block a = Blocks.AIR;
		
		return new Block[]
		{
		n,n,g,n,n,
		n,n,n,n,n,
		g,n,a,n,g,
		n,n,n,n,n,
		n,n,g,n,n
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
		return 2000;
	}

	
	public static final int MINECRAFTDAY = 24000;
	
	BlockPos aPos[] = new BlockPos[]{new BlockPos(2, 0, 0), new BlockPos(-2, 0, 0), new BlockPos(0, 0, 2), new BlockPos(0, 0, -2)};

	@Override
	public void usePower(World w, BlockPos pos, NBTTagCompound nbt, TileFormationBase te) 
	{
		if(te.power < 200)
			return;
		
		if(w.getWorldTime() % (MINECRAFTDAY / 5) != 0)
			return;
	
			
		
			int rand = w.rand.nextInt(4);
			
			BlockPos np = pos.add(aPos[rand]);
			IBlockState ib = w.getBlockState(np);
			
			setGlowstone(w, np, pos);
		
			te.power -= 200;
		
		
		
	}
	
	
	public boolean canGrow(World w, BlockPos pos)
	{
		for(BlockPos tPos : aPos)
		{
			return true;
		}
		return true;
	}
	
	public int randGlowstone(World w, BlockPos pos)
	{
		
		int rand = w.rand.nextInt(4);
		
		while(w.getBlockState(pos.add(aPos[rand])).getBlock() != ModBlocks.glowstoneCrop)
			rand = w.rand.nextInt(4);
		
		return rand; 
	}

	public void setGlowstone(World w, BlockPos pos, BlockPos center)
	{
		
		if(w.getBlockState(pos).getBlock().isAir(w.getBlockState(pos), w, pos))
		{
			w.setBlockState(pos, ModBlocks.glowstoneCrop.getDefaultState());
			return;
		}
	
		if(w.getBlockState(pos).getBlock() == ModBlocks.glowstoneCrop)
		{
		
			if(((Integer)w.getBlockState(pos).getValue(BlockGlowstoneCrop.AGE)).intValue() < 5)
				w.setBlockState(pos, w.getBlockState(pos).cycleProperty(BlockGlowstoneCrop.AGE));
			else
				w.setBlockState(pos, Blocks.GLOWSTONE.getDefaultState(), 3);
			
			BlockUtils.drawLine(w, center, pos, EnumParticleTypes.FIREWORKS_SPARK);
			return;
			
		}
		
		if(w.getBlockState(pos).getBlock() == Blocks.GLOWSTONE)
			setGlowstone(w, pos.up(), center);
		
		
	}

	
}
