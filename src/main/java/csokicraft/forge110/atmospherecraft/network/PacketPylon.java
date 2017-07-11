package csokicraft.forge110.atmospherecraft.network;

import csokicraft.forge110.atmospherecraft.tileentity.TileEntityAtmospherePylon;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import static csokicraft.forge110.atmospherecraft.network.TilePacketHelper.*;

public class PacketPylon implements IMessage{
	public static final byte ID=10;
	
	BlockPos pos;
	/** 0=no i/o; 1=only in;2=only out;3=in/out*/
	byte payload;
	
	public PacketPylon(TileEntityAtmospherePylon tile){
		pos=tile.getPos();
		payload=tile.getStateByte();
	}

	public PacketPylon(){}
	
	@Override
	public void fromBytes(ByteBuf buf){
		pos=readPos(buf);
		payload=buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf){
		writePos(buf, pos);
		buf.writeByte(payload);
	}

}
