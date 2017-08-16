package com.tacosupremes.nethercraft.common.items;

import com.tacosupremes.nethercraft.common.blocks.tiles.power.INode;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWand extends ItemMod 
{
	public ItemWand()
	{
		super("wand");
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World w, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		if(w.getTileEntity(pos) != null && w.getTileEntity(pos) instanceof INode)
		{
			if(isBound(player.getHeldItem(hand)))
			{
				((INode)w.getTileEntity(pos)).getNodeList().add(BlockPos.fromLong(player.getHeldItem(hand).getTagCompound().getLong("BOUND")));
				((INode)w.getTileEntity(BlockPos.fromLong(player.getHeldItem(hand).getTagCompound().getLong("BOUND")))).getNodeList().add(pos);
				player.getHeldItem(hand).getTagCompound().removeTag("BOUND");			
			}
			else
			{
				if(!player.getHeldItem(hand).hasTagCompound())
					player.getHeldItem(hand).setTagCompound(new NBTTagCompound());
				player.getHeldItem(hand).getTagCompound().setLong("BOUND", pos.toLong());
			}
		}
		
		return super.onItemUse(player, w, pos, hand, facing, hitX, hitY, hitZ);
	}

	private boolean isBound(ItemStack is) 
	{
		return is.hasTagCompound() && is.getTagCompound().hasKey("BOUND");
	}
	

	
	

}
