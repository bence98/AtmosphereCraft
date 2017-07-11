package csokicraft.forge110.atmospherecraft.network;

import csokicraft.forge110.atmospherecraft.AtmosphereCraft;
import csokicraft.forge110.atmospherecraft.tileentity.TileEntityAtmospherePylon;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class HandlerPylonServer implements IMessageHandler<PacketPylon, IMessage>{

	@Override
	public IMessage onMessage(PacketPylon message, MessageContext ctx){
		World w=ctx.getServerHandler().playerEntity.worldObj;
		TileEntityAtmospherePylon te=(TileEntityAtmospherePylon) w.getTileEntity(message.pos);
		if(te==null)
			AtmosphereCraft.logger.severe("Failed to find pylon on server at "+message.pos);
		else
			te.setStateByte(message.payload);
		return null;
	}

}
