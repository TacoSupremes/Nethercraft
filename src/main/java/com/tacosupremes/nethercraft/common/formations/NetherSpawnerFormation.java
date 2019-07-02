package com.tacosupremes.nethercraft.common.formations;

import java.util.ArrayList;
import java.util.List;

import com.tacosupremes.nethercraft.common.blocks.ModBlocks;
import com.tacosupremes.nethercraft.common.blocks.tiles.TileFormationBase;
import com.tacosupremes.nethercraft.common.items.ItemUpgradeRune;
import com.tacosupremes.nethercraft.common.items.ItemUpgradeRune.RuneType;
import com.tacosupremes.nethercraft.common.items.ModItems;
import com.tacosupremes.nethercraft.common.utils.EnchantUtils;
import com.tacosupremes.nethercraft.common.utils.Vector3;
import com.tacosupremes.nethercraft.common.utils.WeightedObject;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandom.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class NetherSpawnerFormation implements IConsumerFormation {

	@Override
	public ItemStack[] getBlocks() 
	{
		ItemStack p = new ItemStack(ModItems.portal);
		
		ItemStack o = new ItemStack(Blocks.OBSIDIAN);
		
		ItemStack a = ItemStack.EMPTY;
		
		return new ItemStack[]
		{
		 o,p,p,p,o,
		 p,a,a,a,p,
		 p,a,a,a,p,
		 p,a,a,a,p,
		 o,p,p,p,o				
		};
	}
	
	@Override
	public void usePower(World w, BlockPos pos, NBTTagCompound nbt, TileFormationBase te)
	{
		if(w.getWorldTime() % ItemUpgradeRune.getSpeed(te.getUpgradeRune(), 60) != 0)
			return;
		
		if(w.getDifficulty() == EnumDifficulty.PEACEFUL)
			return;
		
	//	System.out.println("NETHERSPAWNER" + te.power);
		
		if(te.power >= ItemUpgradeRune.getCost(te.getUpgradeRune(), 300))
		{
			EntityLiving e;
			
			e = randEntity(w, new Entity[]{new EntityBlaze(w), new EntityGhast(w), new EntityPigZombie(w), new EntityMagmaCube(w), new EntityWitherSkeleton(w)});
			
			e.setPosition(pos.getX(), pos.getY() + 3, pos.getZ());
			
			e.setCanPickUpLoot(true);		
			
			if((e instanceof EntityWitherSkeleton || e instanceof EntityPigZombie))
			{
				if(te.getUpgradeRune() == RuneType.Chaos && w.rand.nextInt((6)) == 0)
				{
					ItemStack is = new ItemStack(Items.DIAMOND_SWORD);
				
					EnchantUtils.enchantItem(is, Enchantments.SHARPNESS, 5);
				
					e.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, is);
					
					e.setDropChance(EntityEquipmentSlot.MAINHAND, 0.25F);
					
					if(e instanceof EntityWitherSkeleton)
					{
						e.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.SKULL, 1, 1));
						
						e.setDropChance(EntityEquipmentSlot.HEAD, 1F);
						
					}
					else
					{
						int m = w.rand.nextInt(5);
						
						ItemStack head = new ItemStack(Items.SKULL, 1, m);
						
						if(m == 3)
						{
							head.setTagCompound(new NBTTagCompound());
							
							String[] gods = new String[] {"TacoSupremes","Notch", "Dinnerbone", "Honeydew", "CaptainSparklez"};
							
							String c = gods[w.rand.nextInt(gods.length)];
							
							head.getTagCompound().setString("SkullOwner", c);
							
							e.setCustomNameTag(c);
						}
						
						e.setItemStackToSlot(EntityEquipmentSlot.HEAD, head);
						
						e.setDropChance(EntityEquipmentSlot.HEAD, 1F);
					}
				}
				else
				{
					ItemStack is = new ItemStack(Items.GOLDEN_SWORD);
					
					if(e instanceof EntityWitherSkeleton)
					{
						is = new ItemStack(Items.STONE_SWORD);
					}
				
					if(w.rand.nextBoolean())
						EnchantUtils.enchantItem(is, Enchantments.SHARPNESS, w.rand.nextInt(5));
			
					e.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, is);
				}

			}
			
			e.enablePersistence();
			
			e.timeUntilPortal = 10000;
			
			if(!w.isRemote)
				w.spawnEntity(e);
			
			te.power -= ItemUpgradeRune.getCost(te.getUpgradeRune(), 300);
		}
	}
	
	
	public EntityLiving randEntity(World w, Entity[] l)
	{	
		List<WeightedObject> l2  = new ArrayList<WeightedObject>();
		l2.add(new WeightedObject(l[0], 70));
		l2.add(new WeightedObject(l[1], 45));
		l2.add(new WeightedObject(l[2], 160));
		l2.add(new WeightedObject(l[3], 60));
		l2.add(new WeightedObject(l[4], 35));
		
		return (EntityLiving)WeightedRandom.getRandomItem(w.rand, l2).getObject();
		
	}

	@Override
	public Vector3 getOffset() 
	{	
		return Vector3.zero;
	}

	@Override
	public int getMaxPower()
	{	
		return 5000;
	}

	@Override
	public int getRange()
	{	
		return -1;
	}

	@Override
	public String getName() 
	{
		return "nether_spawner";
	}

/*

	@Override
	public Block getSpecialBlock() 
	{

		return Blocks.PORTAL;
	}
	
*/
	

}
