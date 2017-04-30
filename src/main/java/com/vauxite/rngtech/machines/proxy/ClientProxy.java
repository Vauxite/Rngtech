package com.vauxite.rngtech.machines.proxy;
import com.vauxite.rngtech.machines.RngtechMachines;
import com.vauxite.rngtech.machines.render.TESRWindTurbine;
import com.vauxite.rngtech.machines.tileentity.TileEntityWindTurbine;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import scala.actors.threadpool.helpers.Utils;

public class ClientProxy extends CommonProxy {

public void registerBlockRenderer(){
	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWindTurbine.class, new TESRWindTurbine());
}
	
 public void registerItemRenderer(Item item, int meta, String id) {
	 ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(RngtechMachines.modId + ":" + id, "inventory"));
	 System.out.println("Registred render for " +item.getUnlocalizedName().substring(5));
 }
}
