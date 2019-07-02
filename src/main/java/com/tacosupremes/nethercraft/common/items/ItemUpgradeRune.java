package com.tacosupremes.nethercraft.common.items;

import com.tacosupremes.nethercraft.common.blocks.tiles.TileFormationBase;
import com.tacosupremes.nethercraft.common.blocks.tiles.power.INode;
import com.tacosupremes.nethercraft.common.items.ItemUpgradeRune.RuneType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemUpgradeRune extends ItemMod {

	public ItemUpgradeRune()
	{
		super("upgrade_rune", RuneType.values().length - 1);	
	}
	
	@Override
	public boolean needsDifferentNames() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public ItemStack[] getRecipe(int meta) 
	{
		return null;
	}
	
	//SPEED increases Speed Potency gives buff cheaper but slower CHaos: EX lava_pit can double
	public enum RuneType
	{
		Speed(2.0D, 0.75D, 1.75D), Potency(0.6D, 3D, 2D), Chaos(1,1, 1.1D), Creative(50, 5, 0);
		
		public double speedMult; // GEnerating
		public double outputMult; // BOTH
		public double costMult; // Consumer

		private RuneType(double a, double b, double c)
		{
			this.speedMult = a;
			this.outputMult = b;
			this.costMult = c;
		}
	
	}
	
	public static int getPowerGen(RuneType r, int power)
	{
		if(r == null)
			return power;
		
		return (int)(r.outputMult * power);
	}
	
	public static int getSpeed(RuneType r, int speed)
	{
		if(r == null)
			return speed;
		
		return Math.max((int)(speed / r.speedMult), 1);
	}
	
	public static int getCost(RuneType r, int cost)
	{
		if(r == null)
			return cost;
		
		return (int)(cost * r.costMult);
	}
	
	public RuneType getType(ItemStack is)
	{
		if(!(is.getItem() instanceof ItemUpgradeRune))
			return null;
		
		return RuneType.values()[is.getItemDamage()];
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World w, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		if(w.getTileEntity(pos) != null && w.getTileEntity(pos) instanceof TileFormationBase)
		{
			TileFormationBase te = (TileFormationBase)w.getTileEntity(pos);
			
			if(!te.hasUpgradeRune())
			{
				te.setUpgradeRune(getType(player.getHeldItem(hand)));
				player.getHeldItem(hand).setCount(player.getHeldItem(hand).getCount() - 1);
			}
		}
		
		return super.onItemUse(player, w, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	

}
