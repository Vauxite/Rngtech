package com.vauxite.rngtech.machines.net;

import com.vauxite.rngtech.machines.tileentity.TileEntityMachine;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateMachine implements IMessage {
	private BlockPos Pos;
	private int Slot;
	private int Field;
	private int Value;
	
	public PacketUpdateMachine(BlockPos pos,int slot,int field,int value){
		this.Pos 	= pos;
		this.Slot 	= slot;
		this.Field 	= field;
		this.Value 	= value;
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.Pos 	= BlockPos.fromLong(buf.readLong());
		this.Slot 	= buf.readInt();
		this.Field 	= buf.readInt();
		this.Value 	= buf.readInt();

	}
	public PacketUpdateMachine(){
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(this.Pos.toLong());
		buf.writeInt(this.Slot);
		buf.writeInt(this.Field);
		buf.writeInt(this.Value);

	}

	public static class Handler implements IMessageHandler<PacketUpdateMachine, IMessage>{

		@Override
		public IMessage onMessage(PacketUpdateMachine message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable(){
				
				@Override
				public void run(){
					TileEntityMachine te = (TileEntityMachine)Minecraft.getMinecraft().world.getTileEntity(message.Pos);
					
					te.setField(message.Field, message.Slot, message.Value);	
				}
			
				
			});
			return null;
		}
		
	}
}
