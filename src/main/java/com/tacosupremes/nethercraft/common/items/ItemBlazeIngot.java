package com.tacosupremes.nethercraft.common.items;

import java.awt.Color;
import java.util.List;

import com.tacosupremes.nethercraft.common.lib.LibMisc;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;

public class ItemBlazeIngot extends ItemMod {
	
	@Override
	public boolean needsDifferentNames() {
		// TODO Auto-generated method stub
		return true;
	}


	public ItemBlazeIngot()
	{
		super("blaze_ingot", 1);
		
	}

	private final String name = "INLAVA";

	@Override
	public ItemStack[] getRecipe(int meta) 
	{
		if(meta == 1)
			return new ItemStack[] {new ItemStack(ModItems.blazeIngot, 1, 0)};		
		
		ItemStack b = new ItemStack(Items.BLAZE_POWDER);
		
		ItemStack i = new ItemStack(Items.IRON_INGOT);
		
		return new ItemStack[]
		{
				b,b,b,
				b,i,b,
				b,b,b
		};
	}
	
	@Override
	public int getCountOfRecipes()
	{
		return 2;
	}

	@Override
	public RecipeType getType(int meta)
	{
		return meta == 0 ? RecipeType.Shaped : RecipeType.OIOO;
	}


	@Override
	public boolean onEntityItemUpdate(EntityItem item)
	{
		if(item.isBurning() || item.isInLava())
			item.extinguish();
		
		if(!item.getIsInvulnerable())
			item.setEntityInvulnerable(true);
		
		if(!item.getItem().hasTagCompound())
		{
			item.getItem().setTagCompound(new NBTTagCompound());
			item.getItem().getTagCompound().setInteger(name, 0);
		}
		
		
	
		if(item.isInLava() && item.getItem().getItemDamage() == 0)
		{
			item.motionX *= Math.random() * 2.5D;
			item.motionY += Math.random() / 2;
			item.motionZ *= Math.random() * 2.5D;
			
			item.motionX = Math.min(item.motionX, 8D);
			item.motionY = Math.min(item.motionY, 3.5D);
			item.motionZ = Math.min(item.motionZ, 8D);	
				
			if(item.getEntityWorld().getBlockState(item.getPosition()).getBlock() == Blocks.LAVA)
				item.getEntityWorld().setBlockToAir(item.getPosition());
			else if(item.getEntityWorld().getBlockState(item.getPosition().down()).getBlock() == Blocks.LAVA)
				item.getEntityWorld().setBlockToAir(item.getPosition().down());
			else
				return super.onEntityItemUpdate(item);
			
			item.getItem().getTagCompound().setInteger(name, item.getItem().getTagCompound().getInteger(name) + 1);
					
			int i = item.getItem().getTagCompound().getInteger(name);
			
			if(i >= 16)
			{			
				item.setItem(new ItemStack(this, 1, 1));
				item.getItem().setItemDamage(1);
				if(item.getItem().hasTagCompound())
					item.getItem().getTagCompound().removeTag(name);		
			}
		}
		else if(item.isInLava())
		{
			item.motionY += Math.random() / 2;
			item.motionY = Math.min(item.motionY, 3.5D);
		}
		
		return super.onEntityItemUpdate(item);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if(stack.hasTagCompound())
			tooltip.add(I18n.format(LibMisc.MODID + "." + "lava") + ": " + stack.getTagCompound().getInteger(name));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}


	
	

}
