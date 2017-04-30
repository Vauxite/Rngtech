package com.vauxite.rngtech.machines.net;

import com.vauxite.rngtech.machines.RngtechMachines;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	
	private static int packetChannel = 64;
	public static SimpleNetworkWrapper network = null;
	
	public PacketHandler(){
		
	}
	public static int nextID(){
		return packetChannel++;
		
	}
	
	public static void registerMessages(String channelName){
		network = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
		registerMessages();
	}
	public static void registerMessages(){
		network.registerMessage(new PacketUpdateMachine.Handler(), PacketUpdateMachine.class, nextID(), Side.CLIENT);
		network.registerMessage(new PacketRequestMachine.Handler(), PacketRequestMachine.class, nextID(), Side.SERVER);		
	}
	
	
}

