package com.tacosupremes.nethercraft.common.formations;

import java.util.ArrayList;
import java.util.List;

import com.tacosupremes.nethercraft.common.items.ItemCloneBlock;
import com.tacosupremes.nethercraft.common.utils.BlockUtils;
import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class Formations
{
	public static List<IFormation> formations = new ArrayList<IFormation>();
	
	public static void preInit()
	{
		formations.add(new NetherGenFormation());
		formations.add(new NetherSpawnerFormation());
		formations.add(new NetherWartFarm());
		formations.add(new LavaGenFormation());
		formations.add(new GlowstoneFarm());
		formations.add(new OreMagnetFormation());
		formations.add(new LavaPitFormation());
		/*Tsoul gen, attacks entities for energy
		soul cage = (Containment for Entities) 
		Breeder/NetherPigman/MooShroom maker. 10% chance to create Mooshroom/pigman when breeding, make spawn mad cows that attack player/other cows*/
	}

	public static IFormation getFormation(World w, BlockPos pos) 
	{
		formLoop:
		for(int i = 0; i< formations.size(); i++)
		{
			ItemStack[] b = formations.get(i).getBlocks();
			
			int r = (int) (Math.sqrt(b.length) - 1) / 2;
			
			int index = -1;
			
			Vector3 v3 = formations.get(i).getOffset();
			
			for(int x = -r; x <= r; x++)
			{
				for(int z = -r; z <= r; z++)
				{
					index++;
					
					if(b[index] == ItemStack.EMPTY)
						continue;
					
					if(!(BlockUtils.toItemStack(w.getBlockState(pos.add(x, 0, z).add(v3.x, v3.y, v3.z))).isItemEqual(b[index]) || (b[index].getItem() instanceof ItemCloneBlock && w.getBlockState(pos.add(x, 0, z).add(v3.x, v3.y, v3.z)).getBlock() == ((ItemCloneBlock)b[index].getItem()).getBlock())))
						continue formLoop;
				}
			}
			System.out.println(formations.get(i));
			return formations.get(i);
		}
		
		return null;
	}
	
}
