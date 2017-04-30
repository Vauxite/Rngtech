package com.vauxite.rngtech.machines.utility;

import com.vauxite.rngtech.machines.item.ItemCoreModifierBase;

import net.minecraft.item.ItemStack;

public class ModRecipe {
	private float experience;
	private ItemCoreModifierBase cmodifier;
 	private boolean isUnqiue;
 	private ItemStack[] result;
 	private ItemStack input;
 	
 	/**
 	 * 
 	 * Create a process recipe for # tier machine.
 	 * 
 	 * @param input
 	 * @param cmodifier
 	 * @param result
 	 * @param experience
 	 */
 	public ModRecipe(ItemStack input,ItemCoreModifierBase cmodifier, ItemStack[] result, float experience){
 		this.input			= input;
 		this.cmodifier		= cmodifier;
 		this.result			= result;
 		this.experience		= experience;
 	}
 	public ItemStack[] getResult(){
 		return this.result;	
 	}
 	public float getExp(){
 		return this.experience;
 		
 	}
 	public ItemCoreModifierBase getModifier(){
 		return this.cmodifier;
 	}
}
