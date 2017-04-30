package com.vauxite.rngtech.machines.block;

import com.vauxite.rngtech.machines.RngtechMachines;
import com.vauxite.rngtech.machines.net.ModGuiHandler;
import com.vauxite.rngtech.machines.tileentity.TileEntityFrame;

import net.minecraft.block.ITileEntityProvider;
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

public class BlockFrame extends BlockElectricMachineBase implements ITileEntityProvider{

	public BlockFrame(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityFrame();
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
	    if (!worldIn.isRemote) {
	        playerIn.openGui(RngtechMachines.instance, ModGuiHandler.RNGTECH_FRAME, worldIn, pos.getX(), pos.getY(), pos.getZ());
	    }
	    return true;
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState blockstate) {
		TileEntityFrame te = (TileEntityFrame) world.getTileEntity(pos);
	    InventoryHelper.dropInventoryItems(world, pos, te);
	    super.breakBlock(world, pos, blockstate);
	}
	
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {

	}
}
