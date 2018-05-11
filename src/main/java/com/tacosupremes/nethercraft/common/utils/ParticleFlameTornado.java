package com.tacosupremes.nethercraft.common.utils;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFlame;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ParticleFlameTornado extends ParticleFlame {

	
	private double r;
	
	public ParticleFlameTornado(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double r) {
		super(worldIn, xCoordIn-r/2, yCoordIn, zCoordIn-r, 0,0,0);
		
		this.r = r;
		
		  this.particleMaxAge = (int)(45.0F);
		// 
	}
	
	
	public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        
       
        this.motionX = r*Math.cos(this.particleAge);
		
        this.motionY = this.particleAge / 50D;
		
        this.motionZ = r*Math.sin(this.particleAge);
        
       // System.out.println(this.posX+"_"+this.posY+"_"+this.posZ+"\n\n");
        this.move(this.motionX, this.motionY, this.motionZ);
        
        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setExpired();
        }

    }

    
}
