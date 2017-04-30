package com.vauxite.rngtech.machines.init;

import com.vauxite.rngtech.machines.block.BlockBase;
import com.vauxite.rngtech.machines.block.BlockCropBase;
import com.vauxite.rngtech.machines.block.BlockFrame;
import com.vauxite.rngtech.machines.block.BlockGrinder;
import com.vauxite.rngtech.machines.block.BlockOre;
import com.vauxite.rngtech.machines.block.BlockWindTurbine;
import com.vauxite.rngtech.machines.item.ItemModelProvider;
import com.vauxite.rngtech.machines.item.ItemSeedBase;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
	public static BlockOre 			oreCopper;
	public static BlockBase			MachineFrameT0;
	public static BlockGrinder 		Grinder;
	public static BlockWindTurbine 	WindTurbine;
	//public static BlockCropBase 	cropHallon;
	//public static BlockCropBase		cropHjortron;
	public static BlockCropBase		cropAlienIron;
	public static BlockCropBase		cropAlienGold;
	public static BlockCropBase		cropAlienDiamond;
	public static BlockCropBase		cropAlienChorus;
	public static BlockFrame		MachineFrameT1;
	public static void init() {
		/* Ores */
		oreCopper			= register(new BlockOre("oreCopper"));
		
		
		/* Crops */
		//cropHallon			= register(new BlockCropBase("cropHallon",ModItems.seedHallon, ModItems.circuitT1), null);
		//cropHjortron		= register(new BlockCropBase("cropHjortron",ModItems.seedHjortron, ModItems.circuitT2), null);
		
		
		cropAlienIron		= register(new BlockCropBase("cropAlienIron",ModItems.seedAlienIron, ModItems.circuitT1), null);
		cropAlienGold		= register(new BlockCropBase("cropAlienGold",ModItems.seedAlienGold, ModItems.circuitT2), null);
		cropAlienDiamond	= register(new BlockCropBase("cropAlienDiamond",ModItems.seedAlienDiamond, ModItems.circuitT3), null);
		cropAlienChorus		= register(new BlockCropBase("cropAlienChorus",ModItems.seedAlienChorus, ModItems.circuitT4), null);
		
		
		
		WindTurbine			= register(new BlockWindTurbine("WindTurbine"));
		
		
		//MachineFrameT0	= register(new BlockBase(Material.ROCK,"MachineFrameT0"));
		/* Machines */
		Grinder				= register(new BlockGrinder("Grinder"));
		//MachineFrameT1		= register(new BlockFrame("MachineFrameT1"));
	}
	
	private static <T extends Block> T register (T block, ItemBlock itemBlock) {
	
	GameRegistry.register(block);
	if(itemBlock != null) {
		GameRegistry.register(itemBlock);
	}
	
	if(block instanceof ItemModelProvider) {
		((ItemModelProvider)block).registerItemModel(itemBlock);
	}
	
	return block;
	}
	
	private static <T extends Block> T register(T block) {
		ItemBlock itemBlock = new ItemBlock(block);
		itemBlock.setRegistryName(block.getRegistryName());
	return register(block, itemBlock);
	}
}