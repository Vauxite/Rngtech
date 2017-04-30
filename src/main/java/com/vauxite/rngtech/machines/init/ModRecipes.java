package com.vauxite.rngtech.machines.init;

import com.vauxite.rngtech.machines.recipe.AdvProcessRecipes;
import com.vauxite.rngtech.machines.recipe.GrinderRecipes;
import com.vauxite.rngtech.machines.recipe.ModProcessRecipes;
import com.vauxite.rngtech.machines.utility.ModRecipe;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {
	public static void addRecipes() {
		
		//shaped crafting recipes
		GameRegistry.addRecipe(new ItemStack(ModBlocks.Grinder), "CSC", "CPC", "WWW", 'C',Blocks.COBBLESTONE,'S',Items.STRING,'P',Items.STICK,'W',Blocks.WOODEN_SLAB);
		GameRegistry.addRecipe(new ItemStack(ModItems.grindstonet1), " I ", "III", " I ", 'I',Items.IRON_INGOT);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.WindTurbine), " SP", "PS ", " SP", 'S',Items.STICK,'P',Items.PAPER);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.WindTurbine), "PS ", " SP", "PS ", 'S',Items.STICK,'P',Items.PAPER);		

		
		//shapeless crafting recipes
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.seedAlienIron), new Object[] {Items.WHEAT_SEEDS, ModItems.AlienMatter, Items.IRON_INGOT});
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.seedAlienGold), new Object[] {Items.WHEAT_SEEDS, ModItems.AlienMatter, Items.GOLD_INGOT});
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.seedAlienDiamond), new Object[] {Items.WHEAT_SEEDS, ModItems.AlienMatter, Items.DIAMOND});
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.seedAlienChorus), new Object[] {Items.WHEAT_SEEDS, ModItems.AlienMatter, Items.CHORUS_FRUIT_POPPED});

		
		//smelting recipes
		GameRegistry.addSmelting(ModBlocks.oreCopper, new ItemStack(ModItems.ingotCopper), 1.0f);
		
		
	}
}
