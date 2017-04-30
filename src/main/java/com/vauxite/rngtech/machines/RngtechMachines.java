package com.vauxite.rngtech.machines;

import com.vauxite.rngtech.machines.init.ModBlocks;
import com.vauxite.rngtech.machines.init.ModItems;
import com.vauxite.rngtech.machines.init.ModRecipes;
import com.vauxite.rngtech.machines.net.ModGuiHandler;
import com.vauxite.rngtech.machines.net.PacketHandler;
import com.vauxite.rngtech.machines.net.PacketRequestMachine;
import com.vauxite.rngtech.machines.net.PacketUpdateMachine;
import com.vauxite.rngtech.machines.proxy.CommonProxy;
import com.vauxite.rngtech.machines.tileentity.TileEntityFrame;
import com.vauxite.rngtech.machines.tileentity.TileEntityGrinder;
import com.vauxite.rngtech.machines.tileentity.TileEntityWindTurbine;

import net.minecraft.client.main.Main;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = RngtechMachines.modId, name = RngtechMachines.name, version = RngtechMachines.version, acceptedMinecraftVersions = "[1.11]")
public class RngtechMachines {

	@SidedProxy(serverSide = "com.vauxite.rngtech.machines.proxy.CommonProxy", clientSide = "com.vauxite.rngtech.machines.proxy.ClientProxy")
	public static CommonProxy proxy;
	
	public static final String modId = reference.MODID;
	public static final String name = reference.NAME;
	public static final String version = reference.VERSION;
	public static final RngtechTab creativeTab = new RngtechTab();	

	@Mod.Instance(modId)
	public static RngtechMachines instance;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println(name + " is loading!");
		

		PacketHandler.registerMessages(modId);
		ModBlocks.init();
		ModItems.init();
		ModRecipes.addRecipes();
		proxy.registerBlockRenderer();
		

		
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		GameRegistry.registerTileEntity(TileEntityGrinder.class, modId + 		"TileEntityGrinder");
		GameRegistry.registerTileEntity(TileEntityWindTurbine.class, modId + 	"TileEntityWindTurbine");
		GameRegistry.registerTileEntity(TileEntityFrame.class, modId + 		"TileEntityFrameT1");
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new ModGuiHandler());

	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}
}