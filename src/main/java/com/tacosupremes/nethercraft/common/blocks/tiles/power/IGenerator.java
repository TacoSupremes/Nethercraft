package com.tacosupremes.nethercraft.common.blocks.tiles.power;

public interface IGenerator extends INode
{
	public int drain(int amount, boolean doit);
	
	public boolean isGen();
}
