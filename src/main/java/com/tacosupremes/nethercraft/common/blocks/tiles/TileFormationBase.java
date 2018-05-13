package com.tacosupremes.nethercraft.common.blocks.tiles;

import java.util.ArrayList;
import java.util.List;

import com.tacosupremes.nethercraft.Nethercraft;
import com.tacosupremes.nethercraft.common.blocks.tiles.power.IConsumer;
import com.tacosupremes.nethercraft.common.blocks.tiles.power.IGenerator;
import com.tacosupremes.nethercraft.common.blocks.tiles.power.INode;
import com.tacosupremes.nethercraft.common.formations.Formations;
import com.tacosupremes.nethercraft.common.formations.IConsumerFormation;
import com.tacosupremes.nethercraft.common.formations.IFormation;
import com.tacosupremes.nethercraft.common.formations.IGenFormation;
import com.tacosupremes.nethercraft.common.items.ModItems;
import com.tacosupremes.nethercraft.common.utils.BlockUtils;
import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
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

	public static int transferRate = 50;
	
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
		for(int i = 0; i< linkedTo.size(); i++)
		{
			if(this.getWorld().getTileEntity(linkedTo.get(i)) instanceof INode)
			{
				if(((INode)this.getWorld().getTileEntity(linkedTo.get(i))).isActiveNode() && Nethercraft.proxy.playerHoldingItem(ModItems.wand))
					BlockUtils.drawLine(getWorld(), this.getParticleOffset(), ((INode)this.getWorld().getTileEntity(linkedTo.get(i))).getParticleOffset(), EnumParticleTypes.REDSTONE);	
			}
			else
			{
				linkedTo.remove(i);
				break;
			}
				
		}
		
		if(!this.isActiveNode())
			return;
		
		if(formation == null)
			formation = Formations.getFormation(this.getWorld(), this.getPos());
		
		if(formation == null)
			return;
		
		if(!canRun(this.getWorld(), this.getPos(), nbt))
		{
			formation = null;
			return;
		}
		
		
		if(this.isGen()  && !linkedTo.isEmpty() && this.power >= this.transferRate)
		{
			IConsumer ii = null;
			
			List<BlockPos> l = getPathToConsumer(getWorld(), getPos(), linkedTo, false);
			//TODO: MAKE THIS GAY BS VECTOR3 to FIX GAY SHIT
			if(!l.isEmpty())
			{	
				if(this.getWorld().getTileEntity(l.get(l.size() - 1)) != null && this.getWorld().getTileEntity(l.get(l.size() - 1)) instanceof IConsumer && ((IConsumer)this.getWorld().getTileEntity(l.get(l.size() - 1))).isConsumer())
					ii = ((IConsumer)this.getWorld().getTileEntity(l.get(l.size() - 1)));
				
				Nethercraft.proxy.powerFX(this.getPos().getX()+0.5D, this.getPos().getY()+1.5D, this.getPos().getZ()+0.5D, l);
			}
			
			if(ii != null)
			{
				if(ii.fill(transferRate, false) > 0)		
						power -= ii.fill(transferRate, true);	
				
			}
			
		}
		
		if(this.getWorld().isRemote)
			return;
		
		if(isGen())
		{
		
		
			if(power < formation.getMaxPower())
				((IGenFormation)formation).generatePower(this.getWorld(), this.getPos(), nbt, this);
			else if(power > formation.getMaxPower())
				power = formation.getMaxPower();
			
			
		}
		else
		{
		
			if(power > 0)
				((IConsumerFormation)formation).usePower(this.getWorld(), this.getPos(), nbt, this);
			else 
				power = 0;
			
		}
	}
	
	
	

	public boolean canRun(World w, BlockPos pos, NBTTagCompound nbt) 
	{
		
		Block[] b = formation.getBlocks();
		
		Vector3 v3 = formation.getOffset();
		
		int r = (int) (Math.sqrt(b.length) - 1) / 2;
		
		int index = -1;
		
		for(int x = -r; x <= r; x++)
		{
			for(int z = -r; z <= r; z++)
			{
				index++;
				
				if(b[index] == Blocks.AIR)
					continue;
				
				if(w.getBlockState(pos.add(x, 0, z).add(v3.x, v3.y, v3.z)).getBlock() != b[index])
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
		
		if(formation == null)
			return 0;
		
		if(power == formation.getMaxPower())
			return 0;
		
		if(amount + power > formation.getMaxPower())
		{
			if(doit)
				power = formation.getMaxPower();
			
			return formation.getMaxPower() - power;
		}
	
		if(doit)
			power += amount;
		
		return amount;
	}

	@Override
	public boolean isConsumer()
	{
		if(formation == null)
			return false;
		
		return !isGen();
	}

	@Override
	public boolean isGen() 
	{
		
		
		return  formation == null ? false : formation instanceof IGenFormation;
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

	@Override
	public int getPower() 
	{

		return power;
	}
	

@SuppressWarnings("unused")
public static List<BlockPos> getPathToConsumer(World w, BlockPos posF, List<BlockPos> linked, boolean includeStart){
	
	List<BlockPos> toCheck = new ArrayList<BlockPos>();
	
	List<BlockPos> path = new ArrayList<BlockPos>();
	
	List<String> checked = new ArrayList<String>();
	
	List<Integer> choice = new ArrayList<Integer>();
	
	//int pig  = 4;
	//EnumParticleTypes e = EnumParticleTypes.values()[pig];
	
	if(linked.isEmpty())
		return toCheck;
	
	if(linked.size() > 1){
		
		choice.add(0);
		
		toCheck.add(linked.get(0));
	
	}else
		toCheck.addAll(linked);
	
	
	
	while(!toCheck.isEmpty()){

		BlockPos pos = toCheck.remove(0);
		
		path.add(pos);

		checked.add(pos.toString());
		
		INode ip = (INode)w.getTileEntity(pos);
		
		if(ip == null)
			continue;
		
		if(!ip.isActiveNode())
			continue;
		
		
		
		if(w.getTileEntity(pos) instanceof IConsumer)
		{
		
			IConsumer ipt = (IConsumer)w.getTileEntity(pos);
					
				if(ipt.fill(transferRate, false) > 0)
					break;

		}
		
		List<BlockPos> aba = new ArrayList<BlockPos>();
		
		if(ip == null)
			continue;
		
		for(int i = 0; i< ip.getNodeList().size(); i++){
			
			if(ip.getNodeList().get(i) == null)
				continue;
			
			if(!checked.contains(ip.getNodeList().get(i).toString()))	
				aba.add(ip.getNodeList().get(i));
		}
		
		
		if(aba.size() > 1)
			choice.add(path.size()-1);
		
		if(aba.size() > 0)
			toCheck.add(aba.get(0));
		else{

			if(choice.isEmpty())
				return new ArrayList<BlockPos>();
			else{
				
				int i = choice.remove(choice.size()-1);
				
				toCheck.add(path.get(i));
				
				
				
				for(int j = i+1; j< path.size();j++)
					path.remove(j);
				
				
				
			}
			
		}
		
	
			
	}
	
//	BlockPos pos2 = path.get(path.size()-1); 
		
//	w.spawnParticle(e.EXPLOSION_HUGE, pos2.getX(), pos2.getY()+2, pos2.getZ(), 0, 0, 0, null);
	ArrayList<BlockPos> pf = new ArrayList<BlockPos>();
	if(includeStart)
		pf.add(posF);
	pf.addAll(path);	
	return pf;
}

 public boolean isDone() {

	return nbt.getBoolean("DONE");
}

}
