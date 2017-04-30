package com.vauxite.rngtech.machines.block;

import java.util.List;
import java.util.Random;

import com.vauxite.rngtech.machines.init.ModItems;
import com.vauxite.rngtech.machines.item.ItemBase;
import com.vauxite.rngtech.machines.item.ItemSeedBase;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlockCropBase extends BlockCrops {
	private ItemBase crop;
	private ItemSeedBase seed;
	/**
	 * 
	 * @param name a unqiue name for the crop
	 * @param seedBase the seed for this crop
	 * @param crop the drop from this crop
	 */
	public BlockCropBase(String name,ItemSeedBase seedBase,ItemBase drop){
		this.crop = drop;
		this.seed = seedBase;
		setUnlocalizedName(name);
		setRegistryName(name);
	}
	
	@Override
	protected Item getSeed() {
		return this.seed;
	}

	@Override
	protected Item getCrop() {
		return this.crop;
	}
	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		if(getAge(state) < 1){
			return false;
		}
		return true;
	}
	@Override
	protected int getBonemealAgeIncrease(World worldIn) {
        return MathHelper.getInt(worldIn.rand, -2, -5);
	}
    @Override
    public java.util.List<ItemStack> getDrops(net.minecraft.world.IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        List<ItemStack> ret = new java.util.ArrayList<ItemStack>();
        int age = getAge(state);
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        
        if (age >= getMaxAge())
        {
            int k = 3 + fortune;

            for (int i = 0; i < 3 + fortune; ++i)
            {
                if (rand.nextInt(2 * getMaxAge()) <= age)
                {
                	ret.add(new ItemStack(this.crop));
                }
            }
        }
        
        return ret;
    }

	
}
