package com.vauxite.rngtech.machines.recipe;

import java.util.ArrayList;
import java.util.Map;

import com.google.common.collect.Maps;
import com.vauxite.rngtech.machines.item.ItemCoreModifierBase;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLLog;

public class ProcessRecipe {
	private ItemStack Input;
	
	private final Map<ItemStack, Integer> Output = Maps.<ItemStack, Integer>newHashMap();
	
	
	
	private ItemCoreModifierBase Cm;
	private int ProcessTime;
	private int DamageMultiplier;
	private int EnergyMultiplier;

	private int Tier;
	public ProcessRecipe(ItemStack input,ItemStack[] output,int tier,ItemCoreModifierBase cm,int processtime){
		this(tier,input,output,null, cm,processtime, 100, 100);
		
		
	}
	
	public ProcessRecipe(int tier,ItemStack input,ItemStack[] output,int[] chance,ItemCoreModifierBase cm,int processtime,int damagemultiplier,int energymultiplier){
		this.setInput(input);
		this.setMultipleOutput(output, chance);
		this.Cm					= cm;
		this.ProcessTime		= processtime;
		this.DamageMultiplier	= damagemultiplier;
		this.EnergyMultiplier	= energymultiplier;
		this.Tier				= tier;
		
	}
	
	
	private void setInput(ItemStack input){
		this.Input = input;
	}
	private void setMultipleOutput(ItemStack[] output,int[] chance){
		if(output.length != chance.length){
			FMLLog.severe("The output is not balanced for item");
		}else{
			for(int i = 0; i < output.length; i++){
				//System.out.println("Adding" +output[i] + " to output with "+ chance[i] +"%");
				Output.put(output[i], chance[i]);
			}
		}
		
		
	}
	public ItemStack Input(){
		return this.Input;
	}
	public ItemCoreModifierBase CoreModifier(){
		return this.Cm;
	}
	public int DamageMultiplier(){
		return this.DamageMultiplier;
	}
	public int EnergyMultiplier(){
		return this.EnergyMultiplier;
	}
	public int Tier(){
		return this.Tier;
	}
	public int ProcessTime(){
		return this.ProcessTime;
	}
	public Map<ItemStack, Integer> Output(){
		return this.Output;
	}
	
	
}
