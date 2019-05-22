package com.tacosupremes.nethercraft.common.items;

import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemGhastCannon extends ItemMod {

	public ItemGhastCannon() 
	{
		super("ghast_cannon");
	}

	@Override
	public ItemStack[] getRecipe()
	{
		return null;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World w, EntityPlayer player, EnumHand hand)
	{
		Vector3 look = new Vector3(player.getLookVec()).normalize().multiply(10);
		
		EntityLargeFireball e = new EntityLargeFireball(w, player, look.x, look.y, look.z);
		
		look.divide(10);
		
		e.setPosition(player.posX + look.x, player.posY + player.height + look.y, player.posZ + look.z);	
		
		if(!w.isRemote)
			player.getEntityWorld().spawnEntity(e);
		
		return super.onItemRightClick(w, player, hand);
	}

}
