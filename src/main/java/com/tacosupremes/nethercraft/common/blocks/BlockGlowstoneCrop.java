package com.tacosupremes.nethercraft.common.blocks;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGlowstoneCrop extends BlockMod {

	public BlockGlowstoneCrop()
	{
		super(Material.GLASS, "glowstoneCrop");
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)));
	       
	}
	
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 5);
	// TODO: Bounds For Glowstone Crops
	private static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
	        return CROPS_AABB[((Integer)state.getValue(AGE)).intValue()];
	}   
	
	
	 protected int getAge(IBlockState state)
	 {
		 return ((Integer)state.getValue(AGE)).intValue();
	 }

	 public IBlockState withAge(int age)
	 {
		 return this.getDefaultState().withProperty(AGE, Integer.valueOf(age));
	 }

	 public boolean isMaxAge(IBlockState state)
	 {
		 return ((Integer)state.getValue(AGE)).intValue() >= 5;
	 }
	 
	 public IBlockState getStateFromMeta(int meta)
	 {
	     return this.withAge(meta);
	 }

	 public int getMetaFromState(IBlockState state)
	 {
		 return this.getAge(state);
	 }
	 
	 protected BlockStateContainer createBlockState()
	 {
		 return new BlockStateContainer(this, new IProperty[] {AGE});
	 }
	 
	 public boolean isOpaqueCube(IBlockState state)
	 {
	        return false;
	 }

	 public boolean isFullCube(IBlockState state)
	 {
	        return false;
	 }
	 
	 @SideOnly(Side.CLIENT)
	 public BlockRenderLayer getBlockLayer()
	 {
	        return BlockRenderLayer.CUTOUT;
	 }

	 public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_)
	 {
	        return BlockFaceShape.UNDEFINED;
	 }
	 
	 @Override
	 public boolean addToTab()
     {
		 	return false;
     }


	@Override
	public ItemStack[] getRecipe() {
		// TODO Auto-generated method stub
		return null;
	}

}
