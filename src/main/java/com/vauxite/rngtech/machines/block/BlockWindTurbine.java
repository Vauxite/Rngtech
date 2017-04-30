package com.vauxite.rngtech.machines.block;

import java.util.List;

import com.vauxite.rngtech.machines.RngtechMachines;
import com.vauxite.rngtech.machines.tileentity.TileEntityWindTurbine;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import scala.reflect.internal.Trees.New;

public class BlockWindTurbine extends BlockMachineBase implements ITileEntityProvider{
	private float speed;
	private int durability;
	
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625*2, 0, 0.0625*2, 0.0625*14, 0.0625*16, 0.0625*14);

	public BlockWindTurbine(String name) {
		super(name);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		// TODO Auto-generated method stub
		return new TileEntityWindTurbine();
	}
	
	@Override
	public void registerItemModel(Item itemBlock) {
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	@Override
	public BlockRenderLayer  getBlockLayer(){
		return BlockRenderLayer.SOLID;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOUNDING_BOX;
	}
	
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,List<AxisAlignedBB> collidingBoxes, Entity entityIn) {
		super.addCollisionBoxToList(pos, entityBox, collidingBoxes,BOUNDING_BOX);
	}

}
