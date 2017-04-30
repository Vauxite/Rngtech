package com.vauxite.rngtech.machines.net;

import com.vauxite.rngtech.machines.gui.ContainerFrame;
import com.vauxite.rngtech.machines.gui.ContainerGrinder;
import com.vauxite.rngtech.machines.gui.GuiFrame;
import com.vauxite.rngtech.machines.gui.GuiGrinder;
import com.vauxite.rngtech.machines.tileentity.TileEntityFrame;
import com.vauxite.rngtech.machines.tileentity.TileEntityGrinder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ModGuiHandler implements IGuiHandler{

    public static final int RNGTECH_GRINDER = 0;
    public static final int RNGTECH_FRAME	= 1;
    
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == RNGTECH_GRINDER){
        	
            return new ContainerGrinder(player.inventory, (TileEntityGrinder) world.getTileEntity(new BlockPos(x, y, z)));
        }else if (ID == RNGTECH_FRAME){
            return new ContainerFrame(player.inventory, (TileEntityFrame) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == RNGTECH_GRINDER){
            return new GuiGrinder(player.inventory, (TileEntityGrinder) world.getTileEntity(new BlockPos(x, y, z)));
        } else if (ID == RNGTECH_FRAME){
            return new GuiFrame(player.inventory, (TileEntityFrame) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }

}
