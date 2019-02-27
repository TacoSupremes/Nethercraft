package com.tacosupremes.nethercraft.common.items;

import java.util.List;

import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemNetherWalker extends ItemMod {

	public ItemNetherWalker() {
		super("nether_walker", 1);
		
	}

	@Override
	public void onUpdate(ItemStack stack, World w, Entity e, int itemSlot, boolean isSelected) 
	{
		
		if(stack.getItemDamage() == 0)
			return;
		
		if(e.isSprinting())
		{
			Vector3 lv = new Vector3(e.getForward()).normalize().multiply(2);
				
			if(e instanceof EntityPlayer)
				((EntityPlayer)e).getFoodStats().addExhaustion(0.6F);
			
			e.motionX = lv.x;
			e.motionZ = lv.z;
			
		}
		
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World w, EntityPlayer player, EnumHand handIn) 
	{
		ItemStack is = player.getHeldItem(handIn);
		
		if(is.getItemDamage() == 0)
			is.setItemDamage(1);
		else
			is.setItemDamage(0);
		
		return super.onItemRightClick(w, player, handIn);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		
		super.addInformation(stack, worldIn, tooltip, flagIn);
		
		tooltip.add(stack.getItemDamage() != 0 ? (I18n.format(this.getUnlocalizedName() + ".active")) : (I18n.format(this.getUnlocalizedName() + ".disabled")));
	}

	
	
	
	

}
