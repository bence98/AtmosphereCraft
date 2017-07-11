package csokicraft.forge110.atmospherecraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;

public class TilePacketHelper{
	public static BlockPos readPos(ByteBuf buf){
		int x=buf.readInt(),
			y=buf.readInt(),
			z=buf.readInt();
		return new BlockPos(x, y, z);
	}

	public static void writePos(ByteBuf buf, BlockPos pos){
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}
}
