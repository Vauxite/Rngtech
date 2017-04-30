package com.vauxite.rngtech.machines.utility;


import com.vauxite.rngtech.machines.recipe.ProcessRecipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ProcessSlot {
	private ProcessRecipe Recipe;
	private int Progress;
	private int Force;
	
	public ProcessSlot(){
		this.Clear();
	}
	public void Clear(){
		this.Progress = 0;
		this.Force = 0;
		this.Recipe = null;
	}
	public ProcessRecipe Recipe(){
		return this.Recipe;
	}
	public void setRecipe(ProcessRecipe newrecipe){
		this.Recipe = newrecipe;
	}
	public void setForce(int newforce){
		this.Force = newforce;
	}
	public boolean update(){
		if(this.Progress > this.Recipe.ProcessTime()){
			return true;
		}else{
			this.Progress += this.Force;
			return false;
		}
	}

	
}
