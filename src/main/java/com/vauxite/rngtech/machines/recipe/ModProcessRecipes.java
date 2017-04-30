package com.vauxite.rngtech.machines.recipe;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;
import com.vauxite.rngtech.machines.init.ModItems;
import com.vauxite.rngtech.machines.item.ItemCoreModifierBase;
import com.vauxite.rngtech.machines.utility.ModRecipe;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModProcessRecipes {
    private static final ModProcessRecipes RECIPE_BASE = new ModProcessRecipes();
    private final Map<ItemStack, ModRecipe> processList = Maps.<ItemStack, ModRecipe>newHashMap();
    
    private final Map<ItemStack, ItemStack> grindList = Maps.<ItemStack, ItemStack>newHashMap();
    private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

    public static ModProcessRecipes instance()
    {
        return RECIPE_BASE;
    }
    private ModProcessRecipes(){
    	// recipes.
    	this.addProcessRecipeForBlock(Blocks.CLAY, new ItemStack[]{new ItemStack(ModItems.AlienMatter)}, ModItems.grindstonet0, 1F);
    	this.addProcessRecipeForBlock(Blocks.SAND, new ItemStack[]{new ItemStack(ModItems.AlienMatter)}, ModItems.grindstonet0, 0.4F);
    	this.addProcessRecipeForBlock(Blocks.GRAVEL, new ItemStack[]{new ItemStack(ModItems.AlienMatter)}, ModItems.grindstonet0, 0.7F);
    	this.addProcessRecipeForBlock(Blocks.CLAY, new ItemStack[]{new ItemStack(ModItems.AlienMatter)}, ModItems.grindstonet1, 1F);
    	this.addProcessRecipeForBlock(Blocks.SAND, new ItemStack[]{new ItemStack(ModItems.AlienMatter)}, ModItems.grindstonet1, 0.4F);
    	this.addProcessRecipeForBlock(Blocks.GRAVEL, new ItemStack[]{new ItemStack(ModItems.AlienMatter)}, ModItems.grindstonet1, 0.7F);
    	this.addProcessRecipeForBlock(Blocks.CLAY, new ItemStack[]{new ItemStack(ModItems.AlienMatter)}, ModItems.grindstonet3, 1F);
    	this.addProcessRecipeForBlock(Blocks.SAND, new ItemStack[]{new ItemStack(ModItems.AlienMatter)}, ModItems.grindstonet3, 0.4F);
    	this.addProcessRecipeForBlock(Blocks.GRAVEL, new ItemStack[]{new ItemStack(ModItems.AlienMatter)}, ModItems.grindstonet3, 0.7F);    	
    	/* 
    	 * Multiresult example
    	 * this.addProcessRecipeForBlock(Blocks.LOG, new ItemStack[]{new ItemStack(Items.WOODEN_AXE),new ItemStack(Items.WOODEN_SHOVEL)}, ModItems.grindstonet0, 0.7F);
    	 */
    }
    

    /**
     * Adds a process recipe, where the input item is an instance of Block.
     * @param input 
     * @param stack 
     * @param experience 
     */
    public void addProcessRecipeForBlock(Block input, ItemStack[] result,ItemCoreModifierBase cmodifier, float experience)
    {
        this.addProcess(Item.getItemFromBlock(input), result, cmodifier, experience);
    }

    /**
     * Adds a process recipe using an Item as the input item.
     */
    public void addProcess(Item input, ItemStack[] result,ItemCoreModifierBase cmodifier, float experience)
    {
        this.addProcessRecipe(new ItemStack(input, 1, 32767), result,cmodifier, experience);
    }

    
    /**
     * Adds a grinder recipe using an ItemStack as the input for the recipe.
     */
    public void addProcessRecipe(ItemStack input, ItemStack[] result,ItemCoreModifierBase cmodifier, float experience)
    {
        if (getProcessResult(input,cmodifier) != null) {
        	net.minecraftforge.fml.common.FMLLog.info("Ignored process recipe with conflicting input: " + input + " = " + result.toString()); 
        	return; 
        }
        this.processList.put(input,new ModRecipe(input, cmodifier, result, experience));
    }
    
    /**
     * Returns the grinding result of an item.
     */
    @Nullable
    public ModRecipe getProcessResult(ItemStack input,Item item)
    {
        for (Entry<ItemStack, ModRecipe> entry : this.processList.entrySet())
        {
            if (this.compareItemStacks(input, (ItemStack)entry.getKey()) && entry.getValue().getModifier() == item)
            {
                return entry.getValue();
            }
        }
        return null;
    }
    
	/**
	 * Compares two itemstacks to ensure that they are the same. This checks both the item and the metadata of the item.
	*/
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	{
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
      
}
