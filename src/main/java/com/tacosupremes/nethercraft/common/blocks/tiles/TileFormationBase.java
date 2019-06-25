package com.tacosupremes.nethercraft.common.blocks.tiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tacosupremes.nethercraft.Nethercraft;
import com.tacosupremes.nethercraft.common.blocks.tiles.power.IConsumer;
import com.tacosupremes.nethercraft.common.blocks.tiles.power.IGenerator;
import com.tacosupremes.nethercraft.common.blocks.tiles.power.INode;
import com.tacosupremes.nethercraft.common.formations.Formations;
import com.tacosupremes.nethercraft.common.formations.IConsumerFormation;
import com.tacosupremes.nethercraft.common.formations.IFormation;
import com.tacosupremes.nethercraft.common.formations.IGenFormation;
import com.tacosupremes.nethercraft.common.items.IRecipeGiver;
import com.tacosupremes.nethercraft.common.items.ItemCloneBlock;
import com.tacosupremes.nethercraft.common.items.ModItems;
import com.tacosupremes.nethercraft.common.items.RecipeType;
import com.tacosupremes.nethercraft.common.utils.BlockUtils;
import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

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
	
	public static Map<String, List<BlockPos>> protectedBlocks = new HashMap<String, List<BlockPos>>();
	
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
				if(this.isActiveNode() && ((INode)this.getWorld().getTileEntity(linkedTo.get(i))).isActiveNode() && (Nethercraft.proxy.playerHoldingItem(ModItems.wand) || Nethercraft.proxy.playerWearingItem(ModItems.netherGlasses, EntityEquipmentSlot.HEAD)))
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
		
		
		if(!protectedBlocks.containsKey(this.getPos().toString()))
		{
			protectedBlocks.put(this.getPos().toString(), this.getBlocks());
		}
		
		if(formation.getRange() != -1 && Nethercraft.proxy.playerWearingItem(ModItems.netherGlasses, EntityEquipmentSlot.HEAD))
		{	
			int r = formation.getRange();
			BlockPos pos = this.getPos();
		
			BlockUtils.spawnParticle(EnumParticleTypes.SPELL_WITCH, this.getWorld(), Vector3.fromBlockPos(this.getWorld().getTopSolidOrLiquidBlock(pos.add(-r, 0, -r))).add(0.5D, 0, 0.5D), new Vector3(0, 1.0D,0));
			BlockUtils.spawnParticle(EnumParticleTypes.SPELL_WITCH, this.getWorld(), Vector3.fromBlockPos(this.getWorld().getTopSolidOrLiquidBlock(pos.add(+r, 0, -r))).add(0.5D, 0, 0.5D), new Vector3(0, 1.0D,0));
			BlockUtils.spawnParticle(EnumParticleTypes.SPELL_WITCH, this.getWorld(), Vector3.fromBlockPos(this.getWorld().getTopSolidOrLiquidBlock(pos.add(-r, 0, +r))).add(0.5D, 0, 0.5D), new Vector3(0, 1.0D,0));
			BlockUtils.spawnParticle(EnumParticleTypes.SPELL_WITCH, this.getWorld(), Vector3.fromBlockPos(this.getWorld().getTopSolidOrLiquidBlock(pos.add(+r, 0, +r))).add(0.5D, 0, 0.5D), new Vector3(0, 1.0D,0));
			
			
		}
		
		if(!canRun(this.getWorld(), this.getPos(), nbt))
		{
			formation = null;
			
			protectedBlocks.remove(this.getPos().toString());
			
			return;
		}
			
		if(this.isGen() && !linkedTo.isEmpty() && this.power >= this.transferRate)
		{
			IConsumer ii = null;
			
			//List<BlockPos> l = getPathToConsumer(getWorld(), getPos(), linkedTo, false);
			List<BlockPos> l = new ArrayList<BlockPos>();
			
			getPathToConsumer3(world, pos, this.linkedTo, l, new ArrayList<String>(), false);
			
			if(!l.isEmpty())
			{	
				if(this.getWorld().getTileEntity(l.get(l.size() - 1)) != null && this.getWorld().getTileEntity(l.get(l.size() - 1)) instanceof IConsumer && ((IConsumer)this.getWorld().getTileEntity(l.get(l.size() - 1))).isConsumer())
					ii = ((IConsumer)this.getWorld().getTileEntity(l.get(l.size() - 1)));
				else
				{
					System.out.println("THE LIST DOES NOT HAVE CONSUMER OOF");
				}
			}
			else
			{
				System.out.println("BIG OOF The path is empty");
			}
			
			if(ii != null)
			{
				if(ii.fill(transferRate, false) > 0)		
				{	
					
					List<Vector3> lv = getOffset(world, l);
					
					lv.add(ii.getParticleOffset());
					
					Nethercraft.proxy.powerFX(this.getPos().getX()+0.5D, this.getPos().getY()+1.5D, this.getPos().getZ()+0.5D,  lv);
				
					int delay = 0;
					
					long ct = this.getWorld().getWorldTime();
					
					while(this.getWorld().getWorldTime() < ct + 100L)
						{
						System.out.print("\n Time Let Delay : " + (this.getWorld().getWorldTime() + 100L - ct));
					
						};
					
					power -= ii.fill(transferRate, true);	
					
				}
			}
			
		}
		
		
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
	
	private List<BlockPos> getBlocks() 
	{
		ItemStack[] b = formation.getBlocks();
		
		Vector3 v3 = formation.getOffset();
		
		List<BlockPos> bp = new ArrayList<BlockPos>();
		
		int r = (int) (Math.sqrt(b.length) - 1) / 2;
		
		int index = -1;
		
		for(int x = -r; x <= r; x++)
		{
			for(int z = -r; z <= r; z++)
			{
				index++;
				
				if(b[index] == ItemStack.EMPTY)
					continue;
				
				bp.add(pos.add(x, 0, z).add(v3.x, v3.y, v3.z));
			}
			
		}
		
		return bp;
	}
	
	public static boolean isProtected(World w, BlockPos pos)
	{
		for(EnumFacing e : EnumFacing.VALUES)
		{
			if(w.getTileEntity(pos.add(e.getDirectionVec())) != null && w.getTileEntity(pos.add(e.getDirectionVec())) instanceof INode) 
				return true;
		}
		
		
		for(List<BlockPos> lbp : protectedBlocks.values())
		{
			for(BlockPos bp : lbp)
			{
				if(bp.equals(pos))
					return true;
			}
		}
		
		return false;
	}

	public boolean canRun(World w, BlockPos pos, NBTTagCompound nbt) 
	{
		
		ItemStack[] b = formation.getBlocks();
		
		Vector3 v3 = formation.getOffset();
		
		int r = (int) (Math.sqrt(b.length) - 1) / 2;
		
		int index = -1;
		
		for(int x = -r; x <= r; x++)
		{
			for(int z = -r; z <= r; z++)
			{
				index++;
				
				if(b[index] == ItemStack.EMPTY)
					continue;
				
				if(!(BlockUtils.toItemStack(w.getBlockState(pos.add(x, 0, z).add(v3.x, v3.y, v3.z))).isItemEqual(b[index]) || (b[index].getItem() instanceof ItemCloneBlock && w.getBlockState(pos.add(x, 0, z).add(v3.x, v3.y, v3.z)).getBlock() == ((ItemCloneBlock)b[index].getItem()).getBlock())))
					return false;
				
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
	

public static List<Vector3> getOffset(World w, List<BlockPos> bp)
{	
	List<Vector3> v3 = new ArrayList<Vector3>();
	
	for(BlockPos pos : bp)
	{
		if(w.getTileEntity(pos) == null)
			return new ArrayList<Vector3>();
		
		Vector3 off = ((INode)w.getTileEntity(pos)).getParticleOffset();
		
		v3.add(off);
	}
	
	return v3;	
}

public static boolean getPathToConsumer3(World w, BlockPos posF,
		List<BlockPos> linked, List<BlockPos> path, List<String> used, boolean includeStart)
{
	
	if(includeStart)
	{
		path.add(posF);
	}
	
	used.add(posF.toString());
	//*G-N - N - N
	// |		 |
	// N -N		 N
	//    |		 |
	//	  N		 C
	
	for(BlockPos bp : linked)
	{
		if(!used.contains(bp.toString()))
		{
			INode i = (INode)w.getTileEntity(bp);
			
			used.add(bp.toString());
			
			if(!i.isActiveNode())
				continue;
			
			path.add(bp);
						
			if(i instanceof IConsumer && ((IConsumer)i).isConsumer() && ((IConsumer)i).canFill())
			{
				return true;
			}
			
			List<BlockPos> l2 = new ArrayList<BlockPos>();
					
			if(getPathToConsumer3(w, posF, i.getNodeList(), l2, used, false))
			{
				path.addAll(l2);
				return true;
			}
			
			if(!path.isEmpty())
				path.remove(path.size() - 1);
		}
		
	}
	return false;
	
}
 
public boolean isDone() 
{
	return nbt.getBoolean("DONE");
}


 @Override
public boolean canFill() 
{
	return formation != null ? power < formation.getMaxPower() : false;
}



}
