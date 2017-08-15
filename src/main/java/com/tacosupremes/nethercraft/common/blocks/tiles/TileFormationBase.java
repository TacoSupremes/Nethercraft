package com.tacosupremes.nethercraft.common.blocks.tiles;

import java.util.ArrayList;
import java.util.List;

import com.tacosupremes.nethercraft.common.blocks.tiles.power.IConsumer;
import com.tacosupremes.nethercraft.common.blocks.tiles.power.IGenerator;
import com.tacosupremes.nethercraft.common.formations.Formations;
import com.tacosupremes.nethercraft.common.formations.IConsumerFormation;
import com.tacosupremes.nethercraft.common.formations.IFormation;
import com.tacosupremes.nethercraft.common.formations.IGenFormation;
import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileFormationBase extends TileMod implements IGenerator, IConsumer
{
	@Override
	public int drain(int amount, boolean doit) 
	{
		if(amount > power)
			return -1;
		
		if(doit)
			power -= amount;
		
		return amount;
	}
	
	

	public int power = 0;
	
	public int maxPower = 5000;
	
	public IFormation formation = null;
	
	public List<BlockPos> linkedTo = new ArrayList<BlockPos>();
	
	public NBTTagCompound nbt = new NBTTagCompound();

	@Override
	public void writeCustomNBT(NBTTagCompound cmp) 
	{
		cmp.setInteger("POWER", power);
		cmp.setTag("NBT", nbt);
		
		if(!linkedTo.isEmpty())
		{
			cmp.setInteger("SIZE", linkedTo.size());
			
			for(int i = 0; i < linkedTo.size(); i++)
			{
				cmp.setLong("L" + i, linkedTo.get(i).toLong());
			}
		}
	}

	@Override
	public void readCustomNBT(NBTTagCompound cmp)
	{
		this.power = cmp.getInteger("POWER");
		this.nbt = cmp.getCompoundTag("NBT");
		
		if(cmp.hasKey("SIZE"))
		{
			for(int i = 0; i < cmp.getInteger("SIZE"); i++)
			{
				linkedTo.add(BlockPos.fromLong(cmp.getLong("L" + i)));
				cmp.removeTag("L" + i);
			}
			
			cmp.removeTag("SIZE");
		}
	}
	
	@Override
	public void update() 
	{
		if(this.getWorld().isRemote)
			return;
		
		if(!this.isActiveNode())
			return;
		
		if(formation == null)
			formation = Formations.getFormation(this.getWorld(), this.getPos());
		
		if(formation == null)
		{
			System.out.println("FORMATION NULL");
			return;
		}	
		if(!canRun(this.getWorld(), this.getPos(), nbt))
		{
			formation = null;
			return;
		}
		
		
		if(isGen())
		{
		
			if(nbt.getBoolean("DONE"))
				return;
		
			if(power < maxPower)
				((IGenFormation)formation).generatePower(this.getWorld(), this.getPos(), nbt, this);
			else if(power > maxPower)
				power = maxPower;
		}
		else
		{
		
			if(power > 0)
				((IConsumerFormation)formation).usePower(this.getWorld(), this.getPos(), nbt, this);
			else 
				power = 0;
			
		}
	}
	
	
	
	
	public boolean canRun(World w, BlockPos pos, NBTTagCompound nbt) {
		
		Block[] b = formation.getBlocks();
		
		int r = (int) (Math.sqrt(b.length) - 1) / 2;
		
		int index = -1;
		
		for(int x = -r; x <= r; x++)
		{
			for(int z = -r; z <= r; z++)
			{
				index++;
				
				if(b[index] == Blocks.AIR)
					continue;
				
				if(w.getBlockState(pos.add(x, 0, z)).getBlock() != b[index])
				{
					return false;
				}
			}
			
		}
		
		
		return true;
	}

	@Override
	public int fill(int amount, boolean doit)
	{	
		if(amount + power > maxPower)
			return -1;
		
		if(doit)
			power += amount;
		
		return 0;
	}

	@Override
	public boolean isConsumer()
	{
		return !isGen();
	}

	@Override
	public boolean isGen() 
	{
		return formation instanceof IGenFormation;
	}
	
	@Override
	public boolean isActiveNode()
	{
		return !this.getWorld().isBlockPowered(this.getPos());
	}

	@Override
	public List<BlockPos> getNodeList() 
	{
		return this.linkedTo;
	}

	@Override
	public Vector3 getParticleOffset()
	{
		return Vector3.fromBlockPos(getPos()).add(0.5D);
	}

}
