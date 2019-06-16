package com.tacosupremes.nethercraft.common.items;

import com.tacosupremes.nethercraft.Nethercraft;
import com.tacosupremes.nethercraft.common.lib.LibMisc;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.resources.I18n;

public abstract class ItemMod extends Item implements IRecipeGiver
{

	public int meta;

	public ItemMod(String s, int meta)
	{
		super();
		this.setUnlocalizedName(s);
		if(addToTab())
			this.setCreativeTab(Nethercraft.tab);
		if (meta > 0)
			this.setHasSubtypes(true);
		ModItems.items.add(this);
		this.meta = meta;
		this.setMaxDamage(0);
	}

	public ItemMod(String s)
	{
		this(s, 0);

	}

	@Override
	public Item setUnlocalizedName(String name)
	{
		super.setUnlocalizedName(name);
		setRegistryName(new ResourceLocation(LibMisc.MODID + ":" + name));
		return this;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{

		if (stack.getItemDamage() > 0 && this.meta > 0)
			return super.getUnlocalizedName() + stack.getItemDamage();

		return super.getUnlocalizedName(stack);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{

		if (this.meta == 0 || needsDifferentNames())
			return super.getItemStackDisplayName(stack);
		
		

		return I18n.format(this.getUnlocalizedName(stack).replace(String.valueOf(stack.getMetadata()), "") + ".name");
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{

		if (!needsDifferentNames())
			super.getSubItems(tab, items);
		else
		{

			for (int i = 0; i <= meta; i++)
			{
				items.add(new ItemStack(this, 1, i));
			}

		}

	}
	
	public static void registerNonItemMod(Item item, String name)
	{
		item.setUnlocalizedName(name);
		item.setRegistryName(new ResourceLocation(LibMisc.MODID + ":" + name));
		item.setCreativeTab(Nethercraft.tab);
		ModItems.nitems.add(item);
	}

	public boolean needsDifferentNames()
	{
		return false;
	}

	public boolean skipVariants()
	{
		return false;
	}

	public boolean hasOneModel()
	{
		return false;
	}

	public IItemColor getColor()
	{
		return null;
	}

	public String getLocalizedName()
	{
		return I18n.format(this.getUnlocalizedName() + ".name");
	}

	public RecipeType getType(int meta)
	{
		return RecipeType.Shaped;
	}

	public boolean addToTab()
	{
		return true;
	}
	
	
	

}
