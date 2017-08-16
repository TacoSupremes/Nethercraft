package com.tacosupremes.nethercraft.common.blocks.tiles.power;

import java.util.List;

import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.util.math.BlockPos;

public interface INode 
{
	
	public boolean isActiveNode();
	
	public List<BlockPos> getNodeList();
	
	public Vector3 getParticleOffset();

}
