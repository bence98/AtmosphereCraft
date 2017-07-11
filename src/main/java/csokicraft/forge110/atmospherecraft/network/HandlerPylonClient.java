package csokicraft.forge110.atmospherecraft.network;

import csokicraft.forge110.atmospherecraft.AtmosphereCraft;
import csokicraft.forge110.atmospherecraft.tileentity.TileEntityAtmospherePylon;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class HandlerPylonClient implements IMessageHandler<PacketPylon, IMessage>{

	@Override
	public IMessage onMessage(PacketPylon message, MessageContext ctx) {
		World w=Minecraft.getMinecraft().theWorld;
		TileEntityAtmospherePylon te=(TileEntityAtmospherePylon) w.getTileEntity(message.pos);
		if(te==null)
			AtmosphereCraft.logger.severe("Failed to find pylon on client at "+message.pos);
		else
			te.setStateByte(message.payload);
		return null;
	}

}
