package com.vauxite.rngtech.machines.recipe;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;
import com.google.common.collect.Maps;
import com.vauxite.rngtech.machines.init.ModItems;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public class GrinderRecipes {
    private static final GrinderRecipes GRINDER_BASE = new GrinderRecipes();
    private final Map<ItemStack, ItemStack> grindList = Maps.<ItemStack, ItemStack>newHashMap();
    private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

    public static GrinderRecipes instance()
    {
        return GRINDER_BASE;
    }
    private GrinderRecipes(){
        this.addGrindingRecipeForBlock(Blocks.CLAY, new ItemStack(ModItems.AlienMatter), 1.0F);    	
        this.addGrindingRecipeForBlock(Blocks.SAND, new ItemStack(ModItems.AlienMatter), 0.25F);
        this.addGrindingRecipeForBlock(Blocks.GRAVEL, new ItemStack(ModItems.AlienMatter), 0.5F);   	
    }
    

    /**
     * Adds a grinding recipe, where the input item is an instance of Block.
     * @param input 
     * @param stack 
     * @param experience 
     */
    public void addGrindingRecipeForBlock(Block input, ItemStack stack, float experience)
    {
        this.addGrinding(Item.getItemFromBlock(input), stack, experience);
    }

    /**
     * Adds a grinding recipe using an Item as the input item.
     */
    public void addGrinding(Item input, ItemStack stack, float experience)
    {
        this.addGrindingRecipe(new ItemStack(input, 1, 32767), stack, experience);
    }

    
    /**
     * Adds a grinder recipe using an ItemStack as the input for the recipe.
     */
    public void addGrindingRecipe(ItemStack input, ItemStack stack, float experience)
    {
        if (getGrindResult(input) != null) {
        	net.minecraftforge.fml.common.FMLLog.info("Ignored grinding recipe with conflicting input: " + input + " = " + stack); 
        	return; 
        }
        this.grindList.put(input, stack);
        this.experienceList.put(stack, Float.valueOf(experience));
    }
    
    /**
     * Returns the grinding result of an item.
     */
    @Nullable
    public ItemStack getGrindResult(ItemStack stack)
    {
        for (Entry<ItemStack, ItemStack> entry : this.grindList.entrySet())
        {
            if (this.compareItemStacks(stack, (ItemStack)entry.getKey()))
            {
                return (ItemStack)entry.getValue();
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
