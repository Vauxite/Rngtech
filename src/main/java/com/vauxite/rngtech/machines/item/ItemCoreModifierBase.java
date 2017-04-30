package com.vauxite.rngtech.machines.item;

import java.util.List;

import com.vauxite.rngtech.machines.RngtechMachines;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCoreModifierBase extends Item  implements ItemModelProvider {
	 
	private String name;
	 private int tier;
	 /*
	  * 1 grinder
	  */
	 private int ProcessType = 1;
	 private int Force;
	 private int Durability;
	 private int EnergyMultiplier;
	 private int EffiencyMultiplier;
	 
	 public ItemCoreModifierBase(String name,int tier) {
		 this.name = name;
		 this.tier = tier;
		 this.Force = 1+tier*75; //Change
		 setUnlocalizedName(name);
		 setRegistryName(name);
		 setCreativeTab(RngtechMachines.creativeTab); 
		}	
	
	 @Override
	 public void registerItemModel(Item item) {
		 RngtechMachines.proxy.registerItemRenderer(this, 0, name);
	 }
	 
	 @Override
	 public ItemCoreModifierBase setCreativeTab(CreativeTabs tab) {
		 super.setCreativeTab(tab);
		 return this;
	 }
	 @Override
	 public int getItemStackLimit() {
		return 1;
	 }
	 public int Force(){
		 return this.Force;
	 }
	 public int Durability(){
		 return this.Durability;
	 }
	 public int EnergyMultiplier(){
		 return this.EnergyMultiplier;
	 }
	 public int EffiencyMultiplier(){
		 return this.EffiencyMultiplier;
	 }
	 public int ProcessType(){
		 return ProcessType;
	 }
	 
	 @Override
	 public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		// TODO Auto-generated method stub
		super.addInformation(stack, playerIn, tooltip, advanced);
	 }

}
