package com.vauxite.rngtech.machines.block;

import com.vauxite.rngtech.machines.RngtechMachines;
import com.vauxite.rngtech.machines.item.ItemModelProvider;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BlockBase extends Block implements ItemModelProvider{

	protected String name;
	
	public BlockBase(Material materialIn, String name) {
		super(materialIn);
		this.name = name;
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(RngtechMachines.creativeTab); 
	}
	
	@Override
	public void registerItemModel(Item itemBlock) {
		RngtechMachines.proxy.registerItemRenderer(itemBlock, 0, name);
		
	}
	
	 @Override
	 public BlockBase setCreativeTab(CreativeTabs tab) {
		 super.setCreativeTab(tab);
		 return this;
	 }

	
}