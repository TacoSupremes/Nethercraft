package com.tacosupremes.nethercraft.common.items;

import com.tacosupremes.nethercraft.common.utils.BlockUtils;
import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;

public class ItemNetherScythe extends ItemSword implements IRecipeGiver
{

	public ItemNetherScythe() 
	{
		super(ToolMaterial.DIAMOND);
		ItemMod.registerNonItemMod(this, "nether_scythe");
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{	
		super.hitEntity(stack, target, attacker);
		
		int chance = target.getEntityWorld().rand.nextInt(100) + 1;
		
		if(attacker.getHealth() < attacker.getMaxHealth() && chance <= 35)
		{
			attacker.setHealth(Math.min(attacker.getHealth() + 2, attacker.getMaxHealth()));
			
			BlockUtils.drawLine(attacker.world, Vector3.fromEntityCenter(target), Vector3.fromEntityCenter(attacker), EnumParticleTypes.REDSTONE);
		}
		
		if(hasSkull(target) && !target.isEntityAlive() && chance <= (35 + EnchantmentHelper.getLootingModifier(attacker) * 5))
			target.entityDropItem(getSkull(target), 0);
		
		return true;
	}

	public boolean hasSkull(Entity e)
	{
		return getSkull(e) != ItemStack.EMPTY;
	}
	
	public ItemStack getSkull(Entity e)
	{
		if(e instanceof EntitySkeleton)
			return new ItemStack(Items.SKULL, 1, 0);
		
		if(e instanceof EntityWitherSkeleton)
			return new ItemStack(Items.SKULL, 1, 1);
		
		if(e instanceof EntityZombie)
			return new ItemStack(Items.SKULL, 1, 2);
		
		if(e instanceof EntityCreeper)
			return new ItemStack(Items.SKULL, 1, 4);
		
		if(e instanceof EntityPlayer)
		{
			ItemStack is = new ItemStack(Items.SKULL, 1, 3);
			
			is.setTagCompound(new NBTTagCompound());
			
			is.getTagCompound().setString("SkullOwner", ((EntityPlayer)e).getName());
			
			return is;
		}
			
		return ItemStack.EMPTY;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) 
	{	
		return super.onLeftClickEntity(stack, player, entity);
	}
	
	@Override
	public ItemStack[] getRecipe(int meta) 
	{
		ItemStack b = new ItemStack(ModItems.blazeIngot, 1, 1);
			
		ItemStack s = new ItemStack(Items.STICK);
			
		ItemStack n = ItemStack.EMPTY;
			
		return new ItemStack[] {
					b,b,b,
					n,s,b,
					s,n,n
				};
		}

	@Override
	public RecipeType getType(int meta)
	{
		return RecipeType.Shaped;
	}
		
}
