package com.tacosupremes.nethercraft.common.formations;

import java.util.List;

import com.tacosupremes.nethercraft.common.blocks.tiles.TileFormationBase;
import com.tacosupremes.nethercraft.common.blocks.tiles.power.IConsumer;
import com.tacosupremes.nethercraft.common.items.ItemUpgradeRune;
import com.tacosupremes.nethercraft.common.items.ItemUpgradeRune.RuneType;
import com.tacosupremes.nethercraft.common.lib.LibMisc;
import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class LavaPitFormation implements IConsumerFormation {

	@Override
	public ItemStack[] getBlocks()
	{			
		ItemStack n = new ItemStack(Blocks.NETHER_BRICK);
		
		ItemStack a = new ItemStack(Blocks.MAGMA);
		
		return new ItemStack[]
		{
				n,n,n,n,n,
				n,a,a,a,n,
				n,a,a,a,n,
				n,a,a,a,n,
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
			return 2500;
	}

	@Override
	public int getRange()
	{
		return 2;
	}

	//private final String tag = "SMELT";
	
	@Override
	public void usePower(World w, BlockPos pos, NBTTagCompound nbt, TileFormationBase te) 
	{
		int ra = getRange();
		List<EntityItem> ei = w.getEntitiesWithinAABB(EntityItem.class, (new AxisAlignedBB(pos.add(ra, ra, ra), pos.add(-ra, -ra, -ra))));
		
		for(EntityItem e : ei)
		{		
			String tag = e.getName() + e.posX + e.posY + e.posZ;
			
			ItemStack is = e.getItem();
			
			if(FurnaceRecipes.instance().getSmeltingResult(is) != ItemStack.EMPTY)
			{	
				e.setNoDespawn();
				
				if(te.power < ItemUpgradeRune.getCost(te.getUpgradeRune(), 25))
				{
					if(e.cannotPickup())
						e.setNoPickupDelay();
					continue;
				}
				
				e.setPickupDelay(20);
				
				if(nbt.getInteger(tag) >= ItemUpgradeRune.getSpeed(te.getUpgradeRune(), 100))
				{
					ItemStack r = FurnaceRecipes.instance().getSmeltingResult(is);
					if(LibMisc.Ores.isOre(is) && te.getUpgradeRune() == RuneType.Chaos && w.rand.nextBoolean())
					{
						r.setCount(2);
					}
					else
						r.setCount(1);
					
					
					
							
					nbt.removeTag(tag);
					
					EntityItem i = new EntityItem(w, e.posX, e.posY + 0.25D, e.posZ, r);
					i.setItem(r);
					is.setCount(is.getCount() - 1);
					
					if(is == ItemStack.EMPTY)
						e.setDead();
					
					if(!w.isRemote)
						w.spawnEntity(i);
				}
				else
				{
					nbt.setInteger(tag, nbt.getInteger(tag) + 1);
					
					if(nbt.getInteger(tag) % 3 == 0)
						te.power -= 1;

					for(int i = 0; i< 5;i++) 
					{
						double o1 = (w.rand.nextDouble() - w.rand.nextDouble()) / 1.6D;
						
						double o2 = (w.rand.nextDouble() - w.rand.nextDouble()) / 1.6D;
						
						w.spawnParticle(EnumParticleTypes.FLAME, e.posX + o1, e.posY , e.posZ + o2, 0, 0, 0);
						w.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, e.posX + o1, e.posY+0.1D, e.posZ + o2, 0, 0, 0);
					}
				}
			}
		}
	}

	@Override
	public String getName()
	{
		return "lava_pit";
	}

}