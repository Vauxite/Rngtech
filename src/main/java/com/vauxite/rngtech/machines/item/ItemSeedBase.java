package com.vauxite.rngtech.machines.item;

import com.vauxite.rngtech.machines.RngtechMachines;
import com.vauxite.rngtech.machines.block.BlockBase;
import com.vauxite.rngtech.machines.block.BlockCropBase;
import com.vauxite.rngtech.machines.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;

public class ItemSeedBase extends ItemSeeds implements ItemModelProvider {
	protected String name;
	
	/**
	 * 
	 * @param name a unqiue name for the crop.
	 * @param crop that will be planted from the seed.
	 * @param farmland plant the seed on this.
	 */
	public ItemSeedBase(String name,BlockCropBase crop,Block farmland) {
		super(crop,farmland);
		this.name = name;
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(RngtechMachines.creativeTab); 
	}
	
	 @Override
	 public void registerItemModel(Item item) {
		 RngtechMachines.proxy.registerItemRenderer(item, 0, name);
	 }
	 
	 @Override
	 public ItemSeedBase setCreativeTab(CreativeTabs tab) {
		 super.setCreativeTab(tab);
		 return this;
	 }

}
