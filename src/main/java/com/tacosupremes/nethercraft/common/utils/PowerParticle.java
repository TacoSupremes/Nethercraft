package com.tacosupremes.nethercraft.common.utils;

import java.util.List;

import net.minecraft.client.particle.ParticleRedstone;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class PowerParticle extends ParticleRedstone {

	private List<Vector3> path;

	public PowerParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, float r, float g, float b, List<Vector3> path) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, r, g, b);
		this.particleRed = r;
		this.particleGreen = g;
		this.particleBlue = b;
		this.path = path;
		this.canCollide = false;
		this.particleScale *= 2;
	}
	
	private int index = 0;
	
	 public void onUpdate()
	    {
	        this.prevPosX = this.posX;
	        this.prevPosY = this.posY;
	        this.prevPosZ = this.posZ;
	        
	        this.setParticleTextureIndex(7);

	        if (path.get(index) == null || index == path.size()-1 || this.world == null ||this.world.getTileEntity(path.get(index).toBlockPos()) == null)
	        {
	            this.setExpired();
	        }
	        
	        Vector3 v = new Vector3(path.get(index).getX()-this.posX, path.get(index).getY()-this.posY, path.get(index).getZ() - this.posZ).normalize().multiply(1.33D);
	         
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


}
