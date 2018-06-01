package com.tacosupremes.nethercraft;

import com.tacosupremes.nethercraft.common.enchantments.ModEnchantments;
import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NCEventHandler
{
	@SubscribeEvent
	public void onLiving(LivingUpdateEvent event)
	{
		if(event.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.getEntityLiving();
			
			if(player.getItemStackFromSlot(EntityEquipmentSlot.FEET) != null)
			{
				ItemStack is = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
				
				if(is.isItemEnchanted() && EnchantmentHelper.getEnchantmentLevel(ModEnchantments.lavaWalker, is) > 0)
				{
					BlockPos pos = player.getPosition().down();
					
					for(int x = -3; x<=3; x++)
					{
						for(int z = -3; z<=3; z++)
						{
							BlockPos pos_ = pos.add(x, 0, z);
			
							boolean place = (player.getEntityWorld().getBlockState(pos_).getBlock() == Blocks.LAVA || player.getEntityWorld().getBlockState(pos_).getBlock() == Blocks.FLOWING_LAVA) && pos.distanceSq(pos_) <= 10D;
						
							if(place && !player.getEntityWorld().isRemote)
								player.getEntityWorld().setBlockState(pos_, Blocks.NETHERRACK.getDefaultState(), 3);
						}
					}
									
				}
			}
			
		}
	}
	
}
