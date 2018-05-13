package com.tacosupremes.nethercraft.common.utils;

import java.util.List;

import net.minecraft.client.particle.ParticleRedstone;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class PowerParticle extends ParticleRedstone {

	private List<BlockPos> path;

	public PowerParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, float r, float g, float b, List<BlockPos> path) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, r, g, b);
		this.particleRed = r;
		this.particleGreen = g;
		this.particleBlue = b;
		this.path = path;
	}
	
	private int index = 0;
	
	 public void onUpdate()
	    {
	        this.prevPosX = this.posX;
	        this.prevPosY = this.posY;
	        this.prevPosZ = this.posZ;
	        
	        this.setParticleTextureIndex(7);

	        if (index == path.size()-1)
	        {
	            this.setExpired();
	        }
	        
	        Vector3 v = new Vector3(Math.round((path.get(index).getX()-this.posX)*100.0D)/100.0D, Math.round((path.get(index).getY()-this.posY)*100.0D)/100.0D, Math.round((path.get(index).getZ() - this.posZ)*100)/100);
	        
	        double mag = v.mag();
	        	        
	        System.out.println(v.toString()+" "+mag);
	        
	        this.motionX = v.x;
	        
	        this.motionY = v.y;
	        
	        this.motionZ = v.z;
	        
	        this.move(motionX, motionY, motionZ);
	        
	        
	        if(mag >= 0 && mag <=0.2)      
	    		index++;
	        
	        System.out.println(v.toString()+index);
	        
	        
	        
	    }

}
