package com.vauxite.rngtech.machines.net;

import com.vauxite.rngtech.machines.tileentity.TileEntityMachine;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestMachine implements IMessage {
	private BlockPos Pos;
	private int Slot;
	private int Field;
	private int Value;
	private int Dimension;
	
	public PacketRequestMachine(BlockPos pos,int slot,int field,int value,int dimension){
		this.Pos 	= pos;
		this.Slot 	= slot;
		this.Field 	= field;
		this.Value 	= value;
		this.Dimension = dimension;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.Pos = BlockPos.fromLong(buf.readLong());
		this.Slot 	= buf.readInt();
		this.Field 	= buf.readInt();
		this.Value 	= buf.readInt();	
		this.Dimension = buf.readInt();

	}
	public PacketRequestMachine(){
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(this.Pos.toLong());
		buf.writeInt(this.Slot);
		buf.writeInt(this.Field);
		buf.writeInt(this.Value);
		buf.writeInt(this.Dimension);
	}
	public static class Handler implements IMessageHandler<PacketRequestMachine, IMessage>{

		@Override
		public IMessage onMessage(PacketRequestMachine message, MessageContext ctx) {
			
			World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(message.Dimension);
			FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(new Runnable(){
				@Override
				public void run(){
					TileEntityMachine machine = (TileEntityMachine)world.getTileEntity(message.Pos);
					if (machine != null) {
						//Change value on server
						machine.setField(message.Field, message.Slot, message.Value);
					}
				}
				
			});
			return null;
		}
	}
}


