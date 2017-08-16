package com.tacosupremes.nethercraft.common.formations;

import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IFormation
{	
	public Block[] getBlocks();
	
	public Vector3 getOffset();
	
}
