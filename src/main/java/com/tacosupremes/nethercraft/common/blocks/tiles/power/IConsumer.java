package com.tacosupremes.nethercraft.common.blocks.tiles.power;

public interface IConsumer extends INode
{
	public int fill(int amount, boolean doit);
	
	public boolean isConsumer();
}
