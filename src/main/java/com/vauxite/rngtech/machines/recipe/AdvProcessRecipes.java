package com.vauxite.rngtech.machines.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;
import com.vauxite.rngtech.machines.init.ModItems;
import com.vauxite.rngtech.machines.item.ItemCoreModifierBase;


import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public class AdvProcessRecipes {
    private static final AdvProcessRecipes PROCESS_BASE = new AdvProcessRecipes();	
	private final Map<ItemCoreModifierBase, ProcessRecipe> RecipeList = Maps.<ItemCoreModifierBase, ProcessRecipe>newHashMap();
	
	private final List<ProcessRecipe> SingleInputRecipes = new ArrayList<ProcessRecipe>();
	private final List<ProcessRecipe> MultipleInputRecipes = new ArrayList<ProcessRecipe>();	
    public static AdvProcessRecipes instance(){
        return PROCESS_BASE;
    }
    private AdvProcessRecipes(){
    	this.addSingleInputBlockRecipe(Blocks.SAND, ModItems.grindstonet0, new ItemStack[]{new ItemStack(ModItems.AlienMatter)}, new int[]{100}, 250, 100, 100);
    	this.addSingleInputBlockRecipe(Blocks.CLAY, ModItems.grindstonet0, new ItemStack[]{new ItemStack(ModItems.AlienMatter),new ItemStack(Items.GOLD_INGOT)}, new int[]{100,50}, 250, 100, 100);
    	this.addSingleInputRecipe(new ItemStack(Items.CLAY_BALL), ModItems.grindstonet0, new ItemStack[]{new ItemStack(ModItems.AlienMatter)}, new int[]{75}, 50, 100, 100);
    }
    
    
    private void addSingleInputRecipe(ItemStack input,ItemCoreModifierBase cm,ItemStack[] result,int[] chance,int processtime, int damagemultiplier, int energymultiplier){
    	this.SingleInputRecipes.add(new ProcessRecipe(0, input, result, chance, cm, processtime, damagemultiplier, energymultiplier));    	
    }
    private void addSingleInputBlockRecipe(Block block_input,ItemCoreModifierBase cm,ItemStack[] result,int[] chance,int processtime, int damagemultiplier, int energymultiplier){
    	ItemStack input = new ItemStack(Item.getItemFromBlock(block_input));
    	this.SingleInputRecipes.add(new ProcessRecipe(0, input, result, chance, cm, processtime, damagemultiplier, energymultiplier));    	
    }
    
/*   
    private void addSingleInputChanceOutput(ItemStack input,ItemCoreModifierBase cm,ItemStack[] result,int[] chance,int processtime, int damagemultiplier, int energymultiplier){
    	ItemStack[] Arr_Input = {input};
    	this.recipes.add(new ProcessRecipe(0, Arr_Input, Arr_Input, chance, cm, processtime, damagemultiplier, energymultiplier));
    	
	}
    private void addMultipleInputChanceOutput(ItemStack input,ItemCoreModifierBase cm,ItemStack[] result,int[] chance,int processtime, int damagemultiplier, int energymultiplier){
    	
    	
    }
    private void addSingleInputProcessingrecipe(ItemStack input,ItemCoreModifierBase cm,ItemStack[] result,int[] chance,int processtime, int damagemultiplier, int energymultiplier){
    	
    	
    	
    	
    }
    private void addMultipleInputProcessingrecipe(ItemStack input,ItemCoreModifierBase cm,ItemStack[] result,int[] chance,int processtime, int damagemultiplier, int energymultiplier){
    	
    	
    	
    }
    */
	/**
	 * Compares two itemstacks to ensure that they are the same. This checks both the item and the metadata of the item.
	*/
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	{
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
	
    @Nullable
    public ProcessRecipe getSingleInputProcessResult(ItemStack input, ItemCoreModifierBase modifier){
    	if(input.isEmpty() || modifier == null){
    		return null;
    	}
    	for (ProcessRecipe Recipe: SingleInputRecipes){
    		if(Recipe.CoreModifier().ProcessType() == modifier.ProcessType()){
	    			if(this.compareItemStacks(input, Recipe.Input())){
	    				return Recipe;
	    			}
    			}
    		}
    		
    	
		return null;
    	
    	
    }
    
}
