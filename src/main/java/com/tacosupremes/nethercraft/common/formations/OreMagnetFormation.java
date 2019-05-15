package com.tacosupremes.nethercraft.common.formations;

import java.util.List;
import java.util.ArrayList;

import com.tacosupremes.nethercraft.common.blocks.tiles.TileFormationBase;
import com.tacosupremes.nethercraft.common.lib.LibMisc;
import com.tacosupremes.nethercraft.common.utils.BlockUtils;
import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OreMagnetFormation implements IConsumerFormation {

	@Override
	public Block[] getBlocks() 
	{
		Block a = Blocks.AIR;
		
		Block d = Blocks.DIAMOND_BLOCK; 
		
		Block g = Blocks.GLOWSTONE;
		
		Block o = Blocks.OBSIDIAN;
		
		return new Block[]
		{
				a,a,o,a,a,
				a,o,g,o,a,
				o,g,d,g,o,
				a,o,g,o,a,
				a,a,o,a,a		
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

	@Override
	public void usePower(World w, BlockPos pos, NBTTagCompound nbt, TileFormationBase te) 
	{
		
		if(w.getWorldTime() %100 != 0 || te.power < 100)
			return;
		
		for(int y = pos.getY()-1; y > 1; y--)
		{
			for(int x = pos.getX() - getRange(); x <= pos.getX() + getRange(); x++)
			{
				for(int z = pos.getZ() - getRange(); z <= pos.getZ() + getRange(); z++)
				{				
					BlockPos pos_ = new BlockPos(x, y, z);
					
					IBlockState state = w.getBlockState(pos_);
					
					if(LibMisc.Ores.isOre(state))
					{					
						NonNullList<ItemStack> drops = NonNullList.create();
						
						w.getBlockState(pos_).getBlock().getDrops(drops, w, pos_, state, 0);
						
						for(ItemStack is : drops)
						{
							EntityItem i = new EntityItem(w, pos.getX(), pos.getY()+1, pos.getZ(), is);
					
							if(!w.isRemote)
								w.spawnEntity(i);
						}
						
						if(!w.isRemote)
							w.setBlockState(pos_, Blocks.NETHERRACK.getDefaultState(), 3);
					
						te.power -= 100;
						
						return;
					}
		
				}
			}
		}
	}

	@Override
	public int getRange()
	{
		return 8;
	}

	@Override
	public String getName() 
	{
		return "ore_magnet";
	}
		

}
