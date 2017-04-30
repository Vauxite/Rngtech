package com.vauxite.rngtech.machines.init;

import com.vauxite.rngtech.machines.block.BlockBase;
import com.vauxite.rngtech.machines.item.ItemBase;
import com.vauxite.rngtech.machines.item.ItemCoreModifierBase;
import com.vauxite.rngtech.machines.item.ItemModelProvider;
import com.vauxite.rngtech.machines.item.ItemSeedBase;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
	public static ItemBase 			ingotCopper;
	public static ItemBase 			AlienMatter;
	
	//public static ItemSeedBase 		seedHallon;
	//public static ItemSeedBase		seedHjortron;
	public static ItemSeedBase		seedAlienIron;
	public static ItemSeedBase		seedAlienGold;
	public static ItemSeedBase		seedAlienDiamond;
	public static ItemSeedBase		seedAlienChorus;
	//public static ItemBase cropHallon;
	
    public static ItemBase 						circuitT1;
    public static ItemBase 						circuitT2;
    public static ItemBase 						circuitT3;
    public static ItemBase						circuitT4;
    public static ItemBase 						testCircuit;
    public static ItemCoreModifierBase 			grindstonet0;
    public static ItemCoreModifierBase 			grindstonet1;
    public static ItemCoreModifierBase 			grindstonet3;    
    
	public static void init() {
		
		
		/* Basic Materials */
		ingotCopper 	= register(new ItemBase("ingotCopper"));
		AlienMatter		=register(new ItemBase("AlienMatter"));
		
		/* Circuits used in T2 and above crafting */
        circuitT1       = register(new ItemBase("circuitT1"));
        circuitT2       = register(new ItemBase("circuitT2"));
        circuitT3       = register(new ItemBase("circuitT3"));
        circuitT4       = register(new ItemBase("circuitT4"));
        
        /* Test Stuff */
        testCircuit     = register(new ItemBase("testCircuit"));
        
        /* Crops */
        seedAlienIron	= register(new ItemSeedBase("seedAlienIron", ModBlocks.cropAlienIron, Blocks.FARMLAND));
        seedAlienGold	= register(new ItemSeedBase("seedAlienGold", ModBlocks.cropAlienGold, Blocks.FARMLAND));
        seedAlienDiamond= register(new ItemSeedBase("seedAlienDiamond", ModBlocks.cropAlienDiamond, Blocks.FARMLAND));
        seedAlienChorus	= register(new ItemSeedBase("seedAlienChorus", ModBlocks.cropAlienChorus, Blocks.FARMLAND));
        
        /* Grindstones to be used in grinder */
        grindstonet0	= register(new ItemCoreModifierBase("grindstonet0",0));
        grindstonet1	= register(new ItemCoreModifierBase("grindstonet1",1));
        grindstonet3	= register(new ItemCoreModifierBase("grindstonet3",3));
	}
	
	private static <T extends Item> T register(T item) {
		GameRegistry.register(item);
		
		if(item instanceof ItemModelProvider) {
			((ItemModelProvider)item).registerItemModel(item);
		}
		
		return item;
	}
}

