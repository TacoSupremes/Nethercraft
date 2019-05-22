package com.tacosupremes.nethercraft.common.formations;

import java.util.List;

import com.tacosupremes.nethercraft.common.blocks.tiles.TileFormationBase;
import com.tacosupremes.nethercraft.common.blocks.tiles.power.IConsumer;
import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LavaPitFormation implements IConsumerFormation {

	@Override
	public ItemStack[] getBlocks()
	{
		ItemStack a = ItemStack.EMPTY;
			
		ItemStack n = new ItemStack(Blocks.NETHER_BRICK);
		
		ItemStack o = new ItemStack(Blocks.OBSIDIAN);
		
		return new ItemStack[]
		{
				n,n,n,n,n,
				n,o,a,o,n,
				n,a,o,a,n,
				n,o,a,o,n,
				n,n,n,n,n		
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
			return 0;
	}

	@Override
	public int getRange()
	{
		return -1;
	}

	@Override
	public void usePower(World w, BlockPos pos, NBTTagCompound nbt, TileFormationBase te) 
	{
		//TODO:
	}

	@Override
	public String getName()
	{
		return "lava_pit";
	}

}