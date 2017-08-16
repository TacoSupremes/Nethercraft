package com.tacosupremes.nethercraft.common.formations;

import java.util.ArrayList;
import java.util.List;

import com.tacosupremes.nethercraft.common.utils.Vector3;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Formations
{
	public static List<IFormation> formations = new ArrayList<IFormation>();

	
	public static void preInit()
	{
		formations.add(new NetherGenFormation());
		formations.add(new NetherSpawnerFormation());
		formations.add(new NetherWartFarm());
		
		//TODO GlowStone growing, Miner, smelter, lava gen, soul gen.
	}


	public static IFormation getFormation(World w, BlockPos pos) 
	{
		formLoop:
		for(int i = 0; i< formations.size(); i++)
		{
			Block[] b = formations.get(i).getBlocks();
			
			int r = (int) (Math.sqrt(b.length) - 1) / 2;
			
			int index = -1;
			
			Vector3 v3 = formations.get(i).getOffset();
			
			for(int x = -r; x <= r; x++)
			{
				for(int z = -r; z <= r; z++)
				{
					index++;
					
					if(b[index] == Blocks.AIR)
						continue;
					
					if(w.getBlockState(pos.add(x, 0, z).add(v3.x, v3.y, v3.z)).getBlock() != b[index])
					{
						continue formLoop;
					}
				}
				
			}
			
			System.out.println(formations.get(i));
			return formations.get(i);
		}
		
		return null;
	}
	
}
