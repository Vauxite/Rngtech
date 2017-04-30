package com.vauxite.rngtech.machines.utility;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotType extends Slot{
	
	
	
	public SlotType(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);

	}

	public static boolean isCoreModifier(int slot) {
		return false;
	}
	
	

}
