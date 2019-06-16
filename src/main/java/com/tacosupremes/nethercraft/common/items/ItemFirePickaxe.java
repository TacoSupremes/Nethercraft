package com.tacosupremes.nethercraft.common.items;

import com.tacosupremes.nethercraft.Nethercraft;
import com.tacosupremes.nethercraft.common.blocks.ModBlocks;
import com.tacosupremes.nethercraft.common.lib.LibMisc;
import com.tacosupremes.nethercraft.common.utils.BlockUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemFirePickaxe extends ItemPickaxe implements IRecipeGiver{

	public ItemFirePickaxe() {
		super(ToolMaterial.DIAMOND);
		ItemMod.registerNonItemMod(this, "fire_pickaxe");
	}
	
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


	@Override
	public boolean onBlockStartBreak(ItemStack itemStack, BlockPos pos, EntityPlayer player)
	{
		ItemStack block = BlockUtils.toItemStack(player.getEntityWorld().getBlockState(pos));
		
		ItemStack r = FurnaceRecipes.instance().getSmeltingResult(block);
		
		r.setCount(1);
			
		if(r.isEmpty())
			return super.onBlockStartBreak(itemStack, pos, player);
		
		boolean doubleOre = false;
		
		if(LibMisc.Ores.isOre(block))
		{
			
			
			int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemStack);
			
			int success  = player.getEntityWorld().rand.nextInt(6) + lvl;
			
			doubleOre = success > 4;
			
			if(doubleOre)
			{
				r.setCount(2);
				itemStack.damageItem(2, player);
			}
		}
		
		player.getEntityWorld().setBlockToAir(pos);
		
		//TODO spawn particles
		if(doubleOre)
			player.getEntityWorld().spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0, 0, 0);
		
		for(int i = 0; i< 15; i++)
			player.getEntityWorld().spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5D + Math.random() - Math.random(), pos.getY() + 0.5D + Math.random() - Math.random(), pos.getZ() + 0.5D + Math.random() - Math.random(), 0, 0, 0);
		
		player.getEntityWorld().getBlockState(pos).getBlock().spawnAsEntity(player.getEntityWorld(), pos, r);
		
		return true;
	}

	@Override
	public ItemStack[] getRecipe(int meta) 
	{
	
		
		ItemStack b = new ItemStack(ModItems.blazeIngot, 1, 1);
		
		ItemStack s = new ItemStack(Items.STICK);
		
		ItemStack n = ItemStack.EMPTY;
		
		return new ItemStack[] 
				{
					b,b,b,
					n,s,n,
					n,s,n
				};
	}

	@Override
	public RecipeType getType(int meta) 
	{
		return RecipeType.Shaped;
	}

	
	

}
