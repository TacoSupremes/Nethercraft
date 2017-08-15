package com.tacosupremes.nethercraft.common.blocks.tiles;

import java.util.ArrayList;
import java.util.List;

import com.tacosupremes.nethercraft.common.blocks.BlockNode;
import com.tacosupremes.nethercraft.common.blocks.tiles.power.INode;
import com.tacosupremes.nethercraft.common.utils.BlockUtils;
import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;

public class TileNode extends TileMod implements INode
{

	@Override
	public boolean isActiveNode() 
	{
		
		return !this.getWorld().isBlockPowered(this.getPos());
	}
	
	public List<BlockPos> linkedTo = new ArrayList<BlockPos>();

	@Override
	public void writeCustomNBT(NBTTagCompound cmp) 
	{
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
	public List<BlockPos> getNodeList() 
	{
		return this.linkedTo;
	}

	@Override
	public void update()
	{
	
		for(int i = 0; i< linkedTo.size(); i++)
		{
			if(this.getWorld().getTileEntity(linkedTo.get(i)) instanceof INode)
			{
				if(((INode)this.getWorld().getTileEntity(linkedTo.get(i))).isActiveNode())
					BlockUtils.drawLine(getWorld(), this.getParticleOffset(), ((INode)this.getWorld().getTileEntity(linkedTo.get(i))).getParticleOffset(), EnumParticleTypes.REDSTONE);	
			}
			else
			{
				linkedTo.remove(i);
				break;
			}
				
		}
	}

	@Override
	public Vector3 getParticleOffset()
	{
	
		EnumFacing enumfacing = this.getWorld().getBlockState(getPos()).getValue(BlockNode.FACING);
	
		double d0 = (double)pos.getX() + 0.5D;
  
		double d1 = (double)pos.getY() + 0.5D;
	    
		double d2 = (double)pos.getZ() + 0.5D;
	    
		double d3 = 0.22D;
	    
		double d4 = 0.27D;

	    
		if (enumfacing.getAxis().isHorizontal())
		{
			EnumFacing enumfacing1 = enumfacing.getOpposite();
	        double offset = 0.15D;
	        return new Vector3(d0 + offset * (double)enumfacing1.getFrontOffsetX(), d1, d2 + offset * (double)enumfacing1.getFrontOffsetZ()); 
		}
		else
		{
			if(enumfacing == EnumFacing.UP)
				return new Vector3(d0, d1-0.1D, d2);
			else
				return new Vector3(d0, pos.getY() + 0.55D, d2);
	    }	
	}

}
