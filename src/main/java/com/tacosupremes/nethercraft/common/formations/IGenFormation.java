package com.tacosupremes.nethercraft.common.formations;

import com.tacosupremes.nethercraft.common.blocks.tiles.TileFormationBase;
import com.tacosupremes.nethercraft.common.items.ItemUpgradeRune.RuneType;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IGenFormation extends IFormation
{
	public void generatePower(World w, BlockPos pos, NBTTagCompound nbt, TileFormationBase te);	
	
}
