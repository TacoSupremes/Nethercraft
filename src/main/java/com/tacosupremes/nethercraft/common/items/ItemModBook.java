package com.tacosupremes.nethercraft.common.items;

import com.tacosupremes.nethercraft.Nethercraft;
import com.tacosupremes.nethercraft.gui.GuiHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemModBook extends ItemMod {

	public ItemModBook() 
	{
		super("mod_book");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		
		player.openGui(Nethercraft.instance, GuiHandler.getEntryFromName("Nether-Tome").getID(), world, (int)player.posX, (int)player.posY, (int)player.posZ);
		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public ItemStack[] getRecipe() 
	{
		return null;
	}
	
	
	
	

}
