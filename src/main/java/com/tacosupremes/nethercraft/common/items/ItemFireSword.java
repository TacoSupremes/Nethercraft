package com.tacosupremes.nethercraft.common.items;

import java.util.List;

import com.tacosupremes.nethercraft.Nethercraft;
import com.tacosupremes.nethercraft.common.utils.BlockUtils;
import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemFireSword extends ItemSword 
{

	public ItemFireSword() 
	{
		super(ToolMaterial.DIAMOND);
		ItemMod.registerNonItemMod(this, "fire_sword");		
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) 
	{	
		target.setFire(5);
		
		return super.hitEntity(stack, target, attacker);
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) 
	{
		
		if(entityLiving instanceof EntityPlayer)
		{
			Entity e = BlockUtils.rayTraceEntity(entityLiving.world, (EntityPlayer)entityLiving, 15);
			
			if(e != null)
			{
					e.setFire(4);
					
					e.attackEntityFrom(DamageSource.ON_FIRE, 3);
					
					stack.damageItem(2, entityLiving);
					
					Nethercraft.proxy.flameTornadoFX(e.posX, e.posY, e.posZ, 1);
			}
		}
		return super.onEntitySwing(entityLiving, stack);
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) 
	{		
		super.onUsingTick(stack, player, count);
	}
	
	public ActionResult<ItemStack> onItemRightClick(World w, EntityPlayer player, EnumHand handIn)
    {
        ItemStack itemstack = player.getHeldItem(handIn);

		Nethercraft.proxy.flameTornadoFX(player.posX, player.posY, player.posZ, 2D);
		
		itemstack.damageItem(1, player);
		
		List<Entity> ent = w.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(player.getPosition().add(2.25D, 3, 2.25D), player.getPosition().add(-2.25D, -3, -2.25D)));
		
		if(!ent.isEmpty())
		{
			for(Entity e : ent)
			{
				
				Vector3 v = new Vector3(e.getForward()).normalize().multiply(-1);
				
				e.motionX += v.x;
				e.motionY += v.y;
				e.motionZ += v.z;
						
				if(e instanceof EntityLiving)
				{
					e.setFire(5);
				}
				else if(e instanceof IProjectile)
				{
					e.setFire(2);
					e.setDead();
				}
			}
		}

        return new ActionResult(EnumActionResult.PASS, itemstack);     
    }
		
	 public EnumAction getItemUseAction(ItemStack stack)
	 {
	        return EnumAction.BLOCK;
	 }
	 
	 public int getMaxItemUseDuration(ItemStack stack)
	 {
	        return 72000;
	 }

}
