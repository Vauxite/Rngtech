package com.vauxite.rngtech.machines.block;

import com.vauxite.rngtech.machines.RngtechMachines;
import com.vauxite.rngtech.machines.init.ModItems;
import com.vauxite.rngtech.machines.net.ModGuiHandler;
import com.vauxite.rngtech.machines.tileentity.TileEntityGrinder;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGrinder extends BlockMachineBase implements ITileEntityProvider{

	public BlockGrinder(String name) {
		super(name);

	
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityGrinder();
	}

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
    	if (!worldIn.isRemote) {
	        playerIn.openGui(RngtechMachines.instance, ModGuiHandler.RNGTECH_GRINDER, worldIn, pos.getX(), pos.getY(), pos.getZ());
	    }
	    return true;
	}
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState blockstate) {
		TileEntityGrinder te = (TileEntityGrinder) world.getTileEntity(pos);
	    InventoryHelper.dropInventoryItems(world, pos, te);
	    super.breakBlock(world, pos, blockstate);
	}


	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {

	}

	
	
}
