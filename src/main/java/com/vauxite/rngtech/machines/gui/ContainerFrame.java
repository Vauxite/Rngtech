package com.vauxite.rngtech.machines.gui;

import java.util.Iterator;

import com.vauxite.rngtech.machines.tileentity.TileEntityFrame;
import com.vauxite.rngtech.machines.tileentity.TileEntityGrinder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerFrame extends Container {
    private TileEntityFrame te;
    private int curProcessTime;
    private int ProcessTime;
    
    
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
        return this.te.isUseableByPlayer(playerIn);
	}
	
    public ContainerFrame(IInventory playerInv, TileEntityFrame te) {
        this.te = te;

        /* Core modifier*/
        this.addSlotToContainer(new Slot(te,0,120,100));        
        
        /* Input */ 
        Iterator InputSlots = this.te.getInputSlots().iterator();
        while (InputSlots.hasNext()){
        	int slot = (Integer) InputSlots.next(); 
        	int index = slot - te.getInStart();
        	this.addSlotToContainer(new Slot(te,slot,60+index*30,65));
        } 

        
        /* Output */
        Iterator Outputslots = this.te.getOutputSlots().iterator();
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
    
    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
            
            if (this.curProcessTime != this.te.getField(0))
            {
                icontainerlistener.sendProgressBarUpdate(this, 0, this.te.getField(0));
            }


            if (this.ProcessTime != this.te.getField(1))
            {
                icontainerlistener.sendProgressBarUpdate(this, 1, this.te.getField(1));
            }
        }
        	this.curProcessTime = this.te.getField(0);
        	this.ProcessTime = this.te.getField(1);
    }
    
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.te.setField(id, data);
    }   	
    
    
    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.te);
    }
    /*
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot)
    {
        ItemStack previous = null;
        Slot slot = (Slot) this.inventorySlots.get(fromSlot);

        if (slot != null && slot.getHasStack()) {
            ItemStack current = slot.getStack();
            previous = current.copy();
            
            if (fromSlot <= te.getSizeInventory()) {
                // From TE Inventory to Player Inventory
                if (!this.mergeItemStack(current, 9, 41, true))
                    return null;
            } else if ( !this.mergeItemStack(current, 0, 9, false)){
                // From Player Inventory to TE Inventory !te.isItemValidForSlot(0, current) ||
                return null;
            }
            
            if (current.getCount() == 0)
                slot.putStack((ItemStack) null);
            else
                slot.onSlotChanged();

            if (current.getCount()== previous.getCount())
                return null;
            slot.onPickupFromSlot(playerIn, current);
        }
        return previous;
    }    
	*/
}
