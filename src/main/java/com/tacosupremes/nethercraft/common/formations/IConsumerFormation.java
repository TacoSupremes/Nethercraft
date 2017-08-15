package com.tacosupremes.nethercraft.common.formations;

import com.tacosupremes.nethercraft.common.blocks.tiles.TileFormationBase;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IConsumerFormation extends IFormation {

	public void usePower(World w, BlockPos pos, NBTTagCompound nbt, TileFormationBase te);

}
