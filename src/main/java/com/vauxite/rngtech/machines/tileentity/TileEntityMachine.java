package com.vauxite.rngtech.machines.tileentity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.vauxite.rngtech.machines.item.ItemCoreModifierBase;
import com.vauxite.rngtech.machines.net.PacketHandler;
import com.vauxite.rngtech.machines.net.PacketRequestMachine;
import com.vauxite.rngtech.machines.net.PacketUpdateMachine;
import com.vauxite.rngtech.machines.recipe.AdvProcessRecipes;
import com.vauxite.rngtech.machines.recipe.ModProcessRecipes;
import com.vauxite.rngtech.machines.recipe.ProcessRecipe;
import com.vauxite.rngtech.machines.utility.ModRecipe;
import com.vauxite.rngtech.machines.utility.ProcessSlot;
import com.vauxite.rngtech.machines.utility.rngmagic;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMachine extends TileEntity implements ITickable, ISidedInventory{
	/* Stats */
	private List<Integer> inputSlots = new ArrayList<Integer>();
	private List<Integer> outputSlots = new ArrayList<Integer>();
	private List<Integer> upgradeSlots = new ArrayList<Integer>();
	private int invSize;
	private int maxilvl;


	/* Configuration */
	public boolean lastState;
	public boolean isActive;
	public boolean clientActive;

	/* Inventory */
	//private ItemStack[] inventory;
	private ItemStackHandler Inventory;
	private ItemStackHandler ProcessInventory;
	
	private int[] progress;
	private int[] assignedForce;
	private int[] processTime;
	
	public TileEntityMachine(int Tier)
	{
		init(Tier);
		this.isActive		= false;
        this.markDirty();	
	}
	
	public void init(int tier){
		
		if(this.getInputSlots().size() == 0){
			this.setInputSlots(rngmagic.createInputSlots(tier));
		}
		if(this.getOutputSlots().size() == 0){
			this.setOutputSlots(rngmagic.createOutputSlots(tier));
		}
		if(this.getUpgradeSlots().size() == 0){
			this.setUpgradeSlots(rngmagic.createUpgradeSlots(tier));
		}
		this.invSize 	= 	inputSlots.size()+outputSlots.size()+upgradeSlots.size();
		
		progress = new int[20];
		processTime = new int[20];
		assignedForce = new int[20];
		ProcessInventory = new ItemStackHandler(20);

		if (this.Inventory == null){
			this.Inventory 		= new ItemStackHandler(this.invSize);
		}	
	}
	
	/**
	 * Assign inputslots to this tileentity 
	 * 
	 * @param slot_count number (int) of input slots
	 */
	public void setInputSlots(int slot_count){
		this.inputSlots.clear();
		int index = this.getInStart();
		for(int i=0;i<slot_count;i++){
			int inventory_slot = i+index;
			this.inputSlots.add(inventory_slot);
			
		}
    }
	/**
	 * Assign outputslots to this tileentity 
	 * 
	 * @param slot_count number (int) of outputslots
	 */
	public void setOutputSlots(int slot_count){
		this.outputSlots.clear();
		int index = this.getOutStart();
		for(int i=0;i<slot_count;i++){
			int inventory_slot = i+index;
	 		this.outputSlots.add(inventory_slot);
	 	}
    }
	public void setUpgradeSlots(int slot_count){
		this.upgradeSlots.clear();
		int index = this.getUpgradeStart();
		for(int i=0;i<slot_count;i++){
	 		this.upgradeSlots.add(i+index);
	 	} 	
    }
	public void setInventorySize(int size){
		this.invSize = size;
		this.Inventory = new ItemStackHandler(size);
	}
	public List<Integer> getOutputSlots(){
		return this.outputSlots;		
	}
	public List<Integer> getInputSlots(){
		return this.inputSlots;		
	}
	public List<Integer> getUpgradeSlots(){
		return this.upgradeSlots;		
	}
	public int getInStart(){		
		return 1;
	}
	public int getOutStart(){
		return this.inputSlots.get(this.inputSlots.size()-1)+1;
	}
	public int getUpgradeStart(){
		return this.outputSlots.get(this.outputSlots.size()-1)+1;
	}
	public ItemCoreModifierBase getModifier(){
		ItemStack itemstack = this.Inventory.getStackInSlot(0);
		if(!itemstack.isEmpty()){// && itemstack.getItem().getClass().isInstance(ItemCoreModifierBase.class
			ItemCoreModifierBase cm = (ItemCoreModifierBase) itemstack.getItem();
			return cm;
		}
		return null;
	}
	
	/**
	 * A helper function to get available outputslots. Will return an empty list if there isn't one. 
	 * 
	 * @param item
	 * @return Return a list with valid outputslots
	 */
	public List<Integer> GetValidOutput(ItemStack item){
		List<Integer> ValidOutput = new ArrayList<Integer>();
        Iterator Outputslots = this.getOutputSlots().iterator();
        while (Outputslots.hasNext()){
        	int slot = (Integer) Outputslots.next(); 
        	if(this.getStackInSlot(slot).isEmpty() ||
        			(this.getStackInSlot(slot).isItemEqual(item) &&
        					this.getStackInSlot(slot).getCount() + item.getCount() <= this.getStackInSlot(slot).getMaxStackSize()) ){
        		ValidOutput.add(slot);
        	}
        }    
		return ValidOutput;
	}    
    
	private void outputItem(ItemStack item){
		List<Integer> outputslots = this.GetValidOutput(item);
		if (outputslots.isEmpty()){
			/* If there is not a valid output slot. Spit out the item.*/
			this.getWorld().spawnEntity(new EntityItem(this.getWorld(), pos.getX() +0.5, pos.getY()+1, pos.getZ()+ 0.5,item.copy()));
		}else{
			int slot = outputslots.get(0);
			if(this.getStackInSlot(slot).isEmpty()){
				this.setInventorySlotContents(slot, item.copy());
				
			}else if (this.getStackInSlot(slot).getItem() == item.getItem()){
				this.getStackInSlot(slot).grow(1);
			}
			
		}
	}
	
    private ProcessRecipe getRecipe(ItemStack item){
    	return (item.isEmpty() ) ? null : AdvProcessRecipes.instance().getSingleInputProcessResult(item, this.getModifier());
    }
	public void processItem(ProcessRecipe recipe){
		for (Entry<ItemStack, Integer> entry: recipe.Output().entrySet()){
			ItemStack item 		= entry.getKey();
			int item_guaranteed	= entry.getValue() / 100;
			int item_chance		= entry.getValue() % 100;
			if(rngmagic.chance_item(item_chance)){
				item.setCount(item_guaranteed+1);
				
			}else{
				item.setCount(item_guaranteed);
			}
			this.outputItem(entry.getKey());
		}
		
}    
	private void MoveToProcess(int slot){
		Item item = this.Inventory.getStackInSlot(slot).getItem();
		this.getStackInSlot(slot).shrink(1);
		this.ProcessInventory.setStackInSlot(slot,new ItemStack(item));
	}
	
	

	
	
	@Override
	public void update() {

        boolean isDirty = false;
        /* Only do stuff on server :) */
        if (!this.getWorld().isRemote)
        {        
        	if(this.getModifier() != null){
        		for(Integer slot : inputSlots){
        			if(!this.getStackInSlot(slot).isEmpty()){
			        	ProcessRecipe recipe = this.getRecipe(this.getStackInSlot(slot));
						if (recipe != null && this.ProcessInventory.getStackInSlot(slot).isEmpty() && this.getField(2, slot) > 0){
							this.MoveToProcess(slot);
							this.progress[slot] = 0;
							this.processTime[slot] = recipe.ProcessTime();
							isDirty = true;
						}
						

        			}
					if(!this.ProcessInventory.getStackInSlot(slot).isEmpty()){
			        	ProcessRecipe p_recipe = this.getRecipe(this.ProcessInventory.getStackInSlot(slot));
						if(this.progress[slot] > this.processTime[slot]){
							this.processItem(p_recipe);
							this.ProcessInventory.setStackInSlot(slot, ItemStack.EMPTY);
						}else{
							//System.out.println("Increasing progress on slot="+slot+". P="+this.progress[slot]+"/"+p_recipe.ProcessTime());
							if( this.assignedForce[slot]>0){
								this.progress[slot] += this.assignedForce[slot];
							}
						}
						isDirty = true;	
						

					}
        		}
        	}    
        }

        if (isDirty)
        {
        	if(this.getWorld().isRemote){
        		//this.UpdateClient();
        	}
            this.markDirty();
        }
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
	    super.writeToNBT(nbt);

	    /*
	     * Save slot configuration
	     */
	    nbt.setInteger("inputslots", 	this.getInputSlots().size());
	    nbt.setInteger("outputslots", 	this.getOutputSlots().size());	   
	    nbt.setInteger("upgradeslots", 	this.getUpgradeSlots().size());
	    nbt.setInteger("invsize", 		this.invSize);
	    
	    /*
	     * Store current crafting/processing progress
	     */
	    for(int i : inputSlots){
	    	nbt.setInteger("progress" + i, 		this.progress[i]);
	    	nbt.setInteger("assignedForce" +i, 	this.assignedForce[i]);
	    } 
	  
	    /*
	     * Save inventory
	     */
	    nbt.setTag("Inventory", Inventory.serializeNBT());
	    nbt.setTag("ProcessInventory", ProcessInventory.serializeNBT());
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
	    super.readFromNBT(nbt);
	    
	   /*
	    * Save slot configuration
	    */
	    this.setInputSlots(		nbt.getInteger("inputslots"));
	    this.setOutputSlots(	nbt.getInteger("outputslots"));
	    this.setUpgradeSlots(	nbt.getInteger("upgradeslots"));
	    this.setInventorySize(	nbt.getInteger("invsize"));
	    
	    /*
	     * Load current crafting/processing progress
	     */
	    for(int i : inputSlots){
	    	this.progress[i] 		= nbt.getInteger("progress" + i);
	    	this.assignedForce[i] 	= nbt.getInteger("assignedForce"+i);
	    }
	    
 
	    /*
	     * Load inventory
	     */
	    this.Inventory.deserializeNBT(nbt.getCompoundTag("Inventory"));
	    this.ProcessInventory.deserializeNBT(nbt.getCompoundTag("ProcessInventory"));
	    


	}

	@Override
	public ItemStack decrStackSize(int slot, int count) {
	    if (!this.getStackInSlot(slot).isEmpty()) {
	        ItemStack itemstack;
	        if (this.getStackInSlot(slot).getCount() <= count) {
	            itemstack = this.getStackInSlot(slot);
	            this.setInventorySlotContents(slot, itemstack.EMPTY);
	            return itemstack;
	        } else {
	            itemstack = this.getStackInSlot(slot).splitStack(count);

	            if (this.getStackInSlot(slot).getCount() <= 0) {
	                this.setInventorySlotContents(slot, itemstack.EMPTY);
	            } else {
	                //Just to show that changes happened
	                this.setInventorySlotContents(slot, this.getStackInSlot(slot));
	            }
	            return itemstack;
	        }
	    } else {
	        return null;
	    }
	}
	
    public ItemStack getStackInSlotOnClosing(int index) {
        ItemStack stack = this.getStackInSlot(index);
        this.setInventorySlotContents(index, ItemStack.EMPTY);
        return stack;
    }
    
	@Override
	public ItemStack removeStackFromSlot(int slot) {
       return ItemStack.EMPTY;
	}

	public boolean isInputSlot(int slot){
		return this.inputSlots.contains(slot);	
	}

	public int getInventoryStackLimit(int slot) {
		return getInventoryStackLimit();
	}
	
	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isUseableByPlayer(EntityPlayer player) {
		   return this.getWorld().getTileEntity(this.getPos()) == this && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}
	
	@Override
    public int getField(int id)
    {
		return 0;
    }
	/*
	 * 0 = current progress
	 * 1 = Process Time
	 * 2 = Assigned force
	 */
	public int getField(int field,int slot){
		switch(field){
			case 0:
				return this.progress[slot];
			case 1:
				return this.processTime[slot];
			case 2:
				return this.assignedForce[slot];
			default:
				return -1;
		}
		
	}
	/*
	 * 0 = current progress
	 * 1 = Assigned force
	 * 2 = Get time left
	 */
	public int setField(int field,int slot,int value){
		//
		switch(field){
			case 0:
				this.progress[slot] = value;
				return 0;
			case 1:
				this.processTime[slot] = value;
				return 0;
			case 2:
				this.setAssignedForce(slot, value);
				return 0;
			default:
				return -1;
		}
		
	}
	@Override
    public void setField(int id,int value)
    {
        switch (id)
        {
            case 0:
                this.progress[0]		= value;
            case 1:
            	this.processTime[0]		= value;
        }
    }	

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
	    for (int i = 0; i < this.getSizeInventory(); i++)
	        this.setInventorySlotContents(i, ItemStack.EMPTY);
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}
	
    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        this.readFromNBT(packet.getNbtCompound());
    }
    private int getTotalForce(){
    	return (this.getModifier() != null) ? this.getModifier().Force()  : 0;
    }
    
    private void setAssignedForce(int slot,int value){ 	
    	int totalForce = this.getTotalForce();
    	int spentForce = 0;
    	if(totalForce==0){
    		System.out.println("FUCK");   
    		return;
    	}
    	/*
    	 * Sum all assigned forced except the one we want to change
    	 */
    	for(int lslot :this.inputSlots){
    		if(lslot != slot){
    			spentForce += this.assignedForce[lslot];
    		}
    	}

    	if(spentForce+value <= totalForce && value >= 0){
    		//System.out.println("FORCE "+value+ " for slot "+slot);
    		this.assignedForce[slot] = value;
    	}
    }


    public boolean isItemValidForSlot(int slot, ItemStack items)
	{
    	if (slot == 0)
        {
            return false;
        }
    	else if(this.outputSlots.contains(slot)){    		
    		return false;
    	}
        else
        {
        	return true;
        }
	}

    public int[] getSlotsForFace(EnumFacing side)
    {     	
    	switch (side){
    	case UP:
    		int[] inslots = new int[inputSlots.size()];
    		for (int i = 0; i < inputSlots.size(); i++) {
    			inslots[i] = Integer.valueOf(inputSlots.get(i));
    		}
    		return inslots;
		default:
    		int[] outslots = new int[outputSlots.size()];
    		for (int i = 0; i < outputSlots.size(); i++) {
    			outslots[i] = Integer.valueOf(outputSlots.get(i));
    		}
    		return outslots;
        }


    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side.
     */
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side.
     */
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        if (direction == EnumFacing.UP)
        {
        	return false;   
        }
        return true;
    }
	

	
    net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
    net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);
    net.minecraftforge.items.IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.WEST);
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing)
    {
        if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (facing == EnumFacing.DOWN)
                return (T) handlerBottom;
            else if (facing == EnumFacing.UP)
                return (T) handlerTop;
            else
                return (T) handlerSide;
        return super.getCapability(capability, facing);
    }

	@Override
	public boolean isEmpty() {
		return false;
	}

    @SideOnly(Side.CLIENT)
    public int getProgressScaled(int pixels,int slot){
    	if (this.getField(1, slot) != 0 ){
        	return this.getField(0, slot)*pixels / this.getField(1, slot);
    	}else{
    		return 0;
    	}
    }
	
	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public int getSizeInventory() {
		return this.Inventory.getSlots();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.Inventory.getStackInSlot(slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.Inventory.setStackInSlot(slot, stack);
	}
	
}
