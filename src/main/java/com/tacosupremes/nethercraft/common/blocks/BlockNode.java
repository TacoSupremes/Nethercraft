package com.tacosupremes.nethercraft.common.blocks;

import java.util.Random;

import com.tacosupremes.nethercraft.common.blocks.tiles.TileNode;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockNode extends BlockModContainer {

	public BlockNode()
	{
		super(Material.CIRCUITS, "node");
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
	}
	
	public static final PropertyDirection FACING = BlockDirectional.FACING;
    

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{	
		return new TileNode();
	}

	@Override
	protected Class<? extends TileEntity> tile()
	{
		
		return TileNode.class;
	}
	
	 private boolean canPlaceOn(World worldIn, BlockPos pos)
	    {
	        IBlockState state = worldIn.getBlockState(pos);
	        return state.getBlock().canPlaceTorchOnTop(state, worldIn, pos);
	    }

	    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	    {
	        for (EnumFacing enumfacing : FACING.getAllowedValues())
	        {
	            if (this.canPlaceAt(worldIn, pos, enumfacing))
	            {
	                return true;
	            }
	        }

	        return false;
	    }

	    private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing)
	    {
	        BlockPos blockpos = pos.offset(facing.getOpposite());
	        IBlockState iblockstate = worldIn.getBlockState(blockpos);
	        Block block = iblockstate.getBlock();
	        BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, blockpos, facing);

	        if (facing.equals(EnumFacing.UP) && this.canPlaceOn(worldIn, blockpos))
	        {
	            return true;
	        }
	        else if (facing != EnumFacing.UP)
	        {
	            return !isExceptBlockForAttachWithPiston(block) && blockfaceshape == BlockFaceShape.SOLID;
	        }
	        else
	        {
	            return false;
	        }
	    }

	    /**
	     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
	     * IBlockstate
	     */
	    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	    {
	        if (this.canPlaceAt(worldIn, pos, facing))
	        {
	            return this.getDefaultState().withProperty(FACING, facing);
	        }
	        else
	        {
	            for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
	            {
	                if (this.canPlaceAt(worldIn, pos, enumfacing))
	                {
	                    return this.getDefaultState().withProperty(FACING, enumfacing);
	                }
	            }

	            return this.getDefaultState();
	        }
	    }

	    /**
	     * Called after the block is set in the Chunk data, but before the Tile Entity is set
	     */
	    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	    {
	        this.checkForDrop(worldIn, pos, state);
	    }

	    /**
	     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
	     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
	     * block, etc.
	     */
	    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	    {
	        this.onNeighborChangeInternal(worldIn, pos, state);
	    }

	    protected boolean onNeighborChangeInternal(World worldIn, BlockPos pos, IBlockState state)
	    {
	        if (!this.checkForDrop(worldIn, pos, state))
	        {
	            return true;
	        }
	        else
	        {
	            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
	            EnumFacing.Axis enumfacing$axis = enumfacing.getAxis();
	            EnumFacing enumfacing1 = enumfacing.getOpposite();
	            BlockPos blockpos = pos.offset(enumfacing1);
	            boolean flag = false;

	            if (enumfacing$axis.isHorizontal() && worldIn.getBlockState(blockpos).getBlockFaceShape(worldIn, blockpos, enumfacing) != BlockFaceShape.SOLID)
	            {
	                flag = true;
	            }
	            else if (enumfacing$axis.isVertical() && !this.canPlaceOn(worldIn, blockpos))
	            {
	                flag = true;
	            }

	            if (flag)
	            {
	                this.dropBlockAsItem(worldIn, pos, state, 0);
	                worldIn.setBlockToAir(pos);
	                return true;
	            }
	            else
	            {
	                return false;
	            }
	        }
	    }

	    protected boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state)
	    {
	        if (state.getBlock() == this && this.canPlaceAt(worldIn, pos, (EnumFacing)state.getValue(FACING)))
	        {
	            return true;
	        }
	        else
	        {
	            if (worldIn.getBlockState(pos).getBlock() == this)
	            {
	                this.dropBlockAsItem(worldIn, pos, state, 0);
	                worldIn.setBlockToAir(pos);
	            }

	            return false;
	        }
	    }

	    @SideOnly(Side.CLIENT)
	    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	    {
	        EnumFacing enumfacing = (EnumFacing)stateIn.getValue(FACING);
	        double d0 = (double)pos.getX() + 0.5D;
	        double d1 = (double)pos.getY() + 0.7D;
	        double d2 = (double)pos.getZ() + 0.5D;
	        double d3 = 0.22D;
	        double d4 = 0.27D;

	        if (enumfacing.getAxis().isHorizontal())
	        {
	            EnumFacing enumfacing1 = enumfacing.getOpposite();
	            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.27D * (double)enumfacing1.getFrontOffsetX(), d1 + 0.22D, d2 + 0.27D * (double)enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
	            worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.27D * (double)enumfacing1.getFrontOffsetX(), d1 + 0.22D, d2 + 0.27D * (double)enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
	        }
	        else
	        {
	            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	            worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	        }
	    }

	    /**
	     * Convert the given metadata into a BlockState for this Block
	     */
	    public IBlockState getStateFromMeta(int meta)
	    {
	        IBlockState iblockstate = this.getDefaultState();

	        switch (meta)
	        {
	            case 1:
	                iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST);
	                break;
	            case 2:
	                iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST);
	                break;
	            case 3:
	                iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH);
	                break;
	            case 4:
	                iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH);
	                break;
	            case 5:
	            default:
	                iblockstate = iblockstate.withProperty(FACING, EnumFacing.UP);
	        }

	        return iblockstate;
	    }

	    @SideOnly(Side.CLIENT)
	    public BlockRenderLayer getBlockLayer()
	    {
	        return BlockRenderLayer.CUTOUT;
	    }

	    /**
	     * Convert the BlockState into the correct metadata value
	     */
	    public int getMetaFromState(IBlockState state)
	    {
	        int i = 0;

	        switch ((EnumFacing)state.getValue(FACING))
	        {
	            case EAST:
	                i = i | 1;
	                break;
	            case WEST:
	                i = i | 2;
	                break;
	            case SOUTH:
	                i = i | 3;
	                break;
	            case NORTH:
	                i = i | 4;
	                break;
	            case DOWN:
	            case UP:
	            default:
	                i = i | 5;
	        }

	        return i;
	    }

	    /**
	     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
	     * blockstate.
	     */
	    public IBlockState withRotation(IBlockState state, Rotation rot)
	    {
	        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	    }

	    /**
	     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
	     * blockstate.
	     */
	    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	    {
	        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	    }

	    protected BlockStateContainer createBlockState()
	    {
	        return new BlockStateContainer(this, new IProperty[] {FACING});
	    }

	    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_)
	    {
	        return BlockFaceShape.UNDEFINED;
	    }
	    
	    public boolean isOpaqueCube(IBlockState state)
	    {
	        return false;
	    }

	    public boolean isFullCube(IBlockState state)
	    {
	        return false;
	    }
	}