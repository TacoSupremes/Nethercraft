package com.tacosupremes.nethercraft.common.blocks;

import com.tacosupremes.nethercraft.Nethercraft;
import com.tacosupremes.nethercraft.common.lib.LibMisc;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class BlockModContainer extends BlockContainer
{

	public BlockModContainer(Material materialIn, String s)
	{
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setCreativeTab(Nethercraft.tab);
		ModBlocks.blocks.add(this);
		GameRegistry.registerTileEntity(tile(), s);
	}

	protected abstract Class<? extends TileEntity> tile();

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public Block setUnlocalizedName(String name)
	{
		super.setUnlocalizedName(name);
		setRegistryName(LibMisc.MODID + ":" + name);
		return this;
	}

}
