package com.vauxite.rngtech.machines.block;

import com.vauxite.rngtech.machines.RngtechMachines;
import com.vauxite.rngtech.machines.item.ItemModelProvider;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

public class BlockMachineBase extends BlockContainer implements ItemModelProvider{
	protected String name;
	
	public BlockMachineBase(String name) {
		super(Material.IRON);
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
	 public BlockMachineBase setCreativeTab(CreativeTabs tab) {
		 super.setCreativeTab(tab);
		 return this;
	 }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
	
}
