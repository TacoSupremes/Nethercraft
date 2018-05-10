package com.tacosupremes.nethercraft.common.items;

import com.tacosupremes.nethercraft.Nethercraft;
import com.tacosupremes.nethercraft.common.blocks.ModBlocks;
import com.tacosupremes.nethercraft.common.lib.LibMisc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFirePickaxe extends ItemPickaxe {

	public ItemFirePickaxe() {
		super(ToolMaterial.DIAMOND);
		ItemMod.registerNonItemMod(this, "fire_pickaxe");
	}
	
	
//TODO: implement ore magnet
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World w, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
			
		BlockPos pos_ = pos.add(facing.getDirectionVec());
			
		if(w.getBlockState(pos_).getBlock().isAir(w.getBlockState(pos_), w, pos_) && w.getBlockState(pos_).getBlock() != ModBlocks.fire)
		{
			w.setBlockState(pos_, ModBlocks.fire.getDefaultState(), 3);
		
			player.getHeldItem(hand).damageItem(3, player);
		
			player.swingArm(hand);
		}
		
		return super.onItemUse(player, w, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	
	

}
