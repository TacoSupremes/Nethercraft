package com.tacosupremes.nethercraft.common.formations;

import java.util.ArrayList;
import java.util.List;

import com.tacosupremes.nethercraft.common.blocks.tiles.TileFormationBase;
import com.tacosupremes.nethercraft.common.utils.Vector3;
import com.tacosupremes.nethercraft.common.utils.WeightedObject;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandom.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class NetherSpawnerFormation implements IConsumerFormation {

	@Override
	public Block[] getBlocks() 
	{
		Block p = Blocks.PORTAL;
		
		Block o = Blocks.OBSIDIAN;
		
		Block a = Blocks.AIR;
		
		return new Block[]
		{
		 o,p,p,p,o,
		 p,a,a,a,p,
		 p,a,a,a,p,
		 p,a,a,a,p,
		 o,p,p,p,o				
		};
	}

	@Override
	public void usePower(World w, BlockPos pos, NBTTagCompound nbt, TileFormationBase te)
	{
		if(w.getWorldTime() %20 != 0)
			return;
		
		if(w.getDifficulty() == EnumDifficulty.PEACEFUL)
			return;
		
		System.out.println("NETHERSPAWNER" + te.power);
		
		if(te.power >= 300)
		{
			Entity e;
			
			e = randEntity(w, new Entity[]{new EntityBlaze(w), new EntityGhast(w), new EntityPigZombie(w), new EntityMagmaCube(w), new EntityWitherSkeleton(w)});
			
			e.setPosition(pos.getX(), pos.getY() + 3, pos.getZ());
			
			e.timeUntilPortal = 10000;
			
			if(!w.isRemote)
				w.spawnEntity(e);
			
			te.power -= 300;
		}
	}
	
	
	public Entity randEntity(World w, Entity[] l)
	{	
		List<WeightedObject> l2  = new ArrayList<WeightedObject>();
		l2.add(new WeightedObject(l[0], 70));
		l2.add(new WeightedObject(l[1], 45));
		l2.add(new WeightedObject(l[2], 160));
		l2.add(new WeightedObject(l[3], 60));
		l2.add(new WeightedObject(l[4], 35));
		
		return (Entity)WeightedRandom.getRandomItem(w.rand, l2).getObject();
		
	}

	@Override
	public Vector3 getOffset() {
		
		return Vector3.zero;
	}

}
