package com.vauxite.rngtech.machines.tileentity;

import com.vauxite.rngtech.machines.RngtechMachines;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityWindTurbine extends TileEntity{
	public double speed = 4;
	public long lastchangetime;
	public double angle;
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		nbt.setLong("lastchangetime", lastchangetime);
		//compound.setInteger("CrackerCount", crackerCount);
		return nbt;
		
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(getPos(), getPos().add(1, 2, 1));
	}
	
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){	
		this.lastchangetime = nbt.getLong("lastchangetime");
		super.readFromNBT(nbt);
	}
}
