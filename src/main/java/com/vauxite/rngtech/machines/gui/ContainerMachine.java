package com.vauxite.rngtech.machines.gui;

import com.vauxite.rngtech.machines.tileentity.TileEntityMachine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;

public class ContainerMachine extends Container {
	private TileEntityMachine machine;
    private int[] progress;
    private int[] processTime;
    private int[] assignedForce;
	
	
	
	public ContainerMachine(IItemHandler playerInv, TileEntityMachine machineInv) {
        this.machine = machineInv;
        progress = new int[machineInv.getSizeInventory()];
        processTime = new int[machineInv.getSizeInventory()];
        
	}



	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		// TODO Auto-generated method stub
		return false;
	}

}
