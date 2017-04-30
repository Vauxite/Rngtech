package com.vauxite.rngtech.machines.gui;

import java.util.Iterator;
import java.util.List;

import com.vauxite.rngtech.machines.RngtechMachines;
import com.vauxite.rngtech.machines.net.PacketHandler;
import com.vauxite.rngtech.machines.net.PacketRequestMachine;
import com.vauxite.rngtech.machines.net.PacketUpdateMachine;
import com.vauxite.rngtech.machines.tileentity.TileEntityGrinder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerGrinder extends Container {
    private TileEntityGrinder machineGrinder;
    private int[] progress;
    private int[] processTime;
    private int[] assignedForce;

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
        return this.machineGrinder.isUseableByPlayer(playerIn);
	}
	
	
	
    public ContainerGrinder(IInventory playerInv, TileEntityGrinder te) {
        this.machineGrinder = te;
        progress = new int[te.getSizeInventory()];
        processTime = new int[te.getSizeInventory()];
        
        /* Core modifier*/
        this.addSlotToContainer(new Slot(te,0,120,100));        
        
        /* Input */ 
        Iterator InputSlots = this.machineGrinder.getInputSlots().iterator();
        while (InputSlots.hasNext()){
        	int slot = (Integer) InputSlots.next(); 
        	int index = slot - te.getInStart();
        	this.addSlotToContainer(new Slot(te,slot,60+index*30,65));
        } 

        
        /* Output */
        Iterator Outputslots = this.machineGrinder.getOutputSlots().iterator();
        while (Outputslots.hasNext()){
        	int slot = (Integer) Outputslots.next(); 
        	int index = slot - te.getOutStart();
        	this.addSlotToContainer(new Slot(te,slot,60+index*30,145));
        }        


        
        
        int xPlayerStart = 0;
        int yPlayerStart = 270;
        
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, xPlayerStart + x * 18, yPlayerStart + y * 18));
            }
        }
		
        // Player Inventory, Slot 0-8, Slot IDs 36-44
        for (int x = 0; x < 9; ++x) {
            this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 380));
        }   	

    
    }
    @Override
    public void detectAndSendChanges()
    {
    	super.detectAndSendChanges();
		BlockPos pos = machineGrinder.getPos();
		int dimension = machineGrinder.getWorld().provider.getDimension();
		TargetPoint nearbyEntities = new NetworkRegistry.TargetPoint(dimension, pos.getX(), pos.getY(), pos.getZ(), 64);
		
        for (int i = 0; i < this.listeners.size(); ++i){
            IContainerListener listener = (IContainerListener)this.listeners.get(i);
            
	    	for(int slot : this.machineGrinder.getInputSlots()){
	    		if(this.progress[slot] != this.machineGrinder.getField(0, slot)){
	    			PacketUpdateMachine updateMessage = new PacketUpdateMachine(machineGrinder.getPos(),slot,0,this.machineGrinder.getField(0, slot));
	    			try{
	    				PacketHandler.network.sendToAllAround(updateMessage,nearbyEntities);
	    			}catch(Exception e){
	    				e.printStackTrace();
	    			}
	    		}
	    		if(this.processTime[slot] != this.machineGrinder.getField(1, slot)){
	    			PacketUpdateMachine updateMessage = new PacketUpdateMachine(machineGrinder.getPos(),slot,1,this.machineGrinder.getField(1, slot));
	    			PacketHandler.network.sendToAllAround(updateMessage,nearbyEntities);
	    		}
	    		
	    	}
        }
    	for(int slot : this.machineGrinder.getInputSlots()){
    		this.progress[slot] = this.machineGrinder.getField(0, slot);
    	}
    	for(int slot : this.machineGrinder.getInputSlots()){
    		this.processTime[slot] = this.machineGrinder.getField(1, slot);
    	}
    }
    
    
    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.machineGrinder);
    }

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);
	
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
	
			int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();
	
			if (index < containerSlots) {
				if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
				return ItemStack.EMPTY;
			}
	
			if (itemstack1.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
	
			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}
	
			slot.onTake(player, itemstack1);
		}
	
		return itemstack;
	}

}
