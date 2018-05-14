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
		this.canCollide = false;
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
	        
	        Vector3 v = new Vector3(path.get(index).getX()-this.posX, path.get(index).getY()-this.posY, path.get(index).getZ() - this.posZ).normalize().multiply(0.25D);
	        
	        Vector3 v2 = new Vector3(path.get(index).getX()-this.posX, path.get(index).getY()-this.posY, path.get(index).getZ() - this.posZ);
	                
	      //  double mag = v2.mag();
	        	        
	    //    System.out.println(v.toString()+" "+mag);
	        
	        
	        if(v.mag() > v2.mag())
	        	v = v.normalize().multiply(v2.mag());
	        
	        this.motionX = v.x;
	        
	        this.motionY = v.y;
	        
	        this.motionZ = v.z;
	        
	        this.move(motionX, motionY, motionZ);
	        
	        
	      
	        if(v2.mag() <= 0.2)      
	    		index++;
	        
	     //   System.out.println(v.toString()+index);
	        
	        
	        
	    }
	 @Override
	 public int getBrightnessForRender(float p_189214_1_)
	    {
	       
	        return 1000;
	    }

}
