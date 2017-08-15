package com.tacosupremes.nethercraft.common.utils;

import net.minecraft.util.WeightedRandom.Item;

public class WeightedObject extends Item 
{

	private Object o;

	public WeightedObject(Object o, int itemWeightIn)
	{
		super(itemWeightIn);
		this.o = o;
		
	}
	
	public Object getObject()
	{
		return o;
	}

}
