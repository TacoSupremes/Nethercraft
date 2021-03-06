package com.tacosupremes.nethercraft.common.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ItemBlazeChestPlate extends ItemArmor implements IRecipeGiver {

	public ItemBlazeChestPlate()
	{
		super(ArmorMaterial.IRON, 0, EntityEquipmentSlot.CHEST);	
		ItemMod.registerNonItemMod(this, "blaze_chestplate");
	}
	
	private final String tag = "airTicks";
		
	@Override
	public void onArmorTick(World w, EntityPlayer player, ItemStack is) 
	{	
		if(!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		
		if(!is.getTagCompound().hasKey(tag))
			is.getTagCompound().setInteger(tag, 0);
			
		if(player.isInLava())
		{
			PotionEffect pe = new PotionEffect(PotionType.getPotionTypeForName("fire_resistance").getEffects().get(0).getPotion(), 5, 40);		
			player.addPotionEffect(pe);
			player.heal(1F);
		}
		
		if(player.isBurning())
		{
			player.extinguish();
			player.heal(1F);
		}
		
		if(player.isInWater() || player.isWet())
			player.attackEntityFrom(DamageSource.DROWN, 1F);
	
		if(!player.isCollidedVertically && player.motionY < 0 && !player.isSneaking())
			player.motionY /= 2D;
		
		if(!player.isCollidedVertically && is.getTagCompound().getInteger(tag) < 30 && player.motionY > 0.1)
		{
			is.getTagCompound().setInteger(tag, is.getTagCompound().getInteger(tag) + 1);
			player.motionY += 0.09D;
		}
		
		if(player.fallDistance > 0.1D)
			player.fallDistance = 0;
		
		if(player.isCollidedVertically)
			is.getTagCompound().setInteger(tag, 0);
	
	}
	
	@Override
	public ItemStack[] getRecipe(int meta) 
	{
		ItemStack b = new ItemStack(ModItems.blazeIngot, 1, 1);
			
		ItemStack s = new ItemStack(Items.STICK);
			
		ItemStack n = ItemStack.EMPTY;
			
		return new ItemStack[] {
					b,n,b,
					b,b,b,
					b,b,b,
				};
		}

	@Override
	public RecipeType getType(int meta)
	{
		return RecipeType.Shaped;
	}
}
