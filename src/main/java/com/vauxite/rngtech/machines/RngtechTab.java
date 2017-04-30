package com.vauxite.rngtech.machines;

import com.vauxite.rngtech.machines.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RngtechTab extends CreativeTabs {

	public RngtechTab() {
		super(reference.MODID);
		//setBackgroundImageName("tutorialmod.png");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack (ModItems.ingotCopper); //shown icon on creative tab
	}
	
	 @Override
	 public boolean hasSearchBar() {
		 return true; // return false if you don't want search bar
	 }

}
