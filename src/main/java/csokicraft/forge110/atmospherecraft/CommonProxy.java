package csokicraft.forge110.atmospherecraft;

import csokicraft.forge110.atmospherecraft.gui.*;
import csokicraft.forge110.atmospherecraft.network.PacketPylon;
import csokicraft.forge110.atmospherecraft.tileentity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler{
	public static final int GUI_ID_PYLON=2;
	
	public void registerModels(){}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
		
		if(ID==GUI_ID_PYLON&&(te instanceof TileEntityAtmospherePylon)){
			TileEntityAtmospherePylon pylon=(TileEntityAtmospherePylon) te;
			AtmosphereCraft.nethndl.sendTo(new PacketPylon(pylon), (EntityPlayerMP) player);
			return new ContainerAtmPylon(player.inventory, (TileEntityAtmospherePylon) te);
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
		
		if(ID==GUI_ID_PYLON&&(te instanceof TileEntityAtmospherePylon)){
			TileEntityAtmospherePylon pylon=(TileEntityAtmospherePylon) te;
			return new GuiAtmPylon(player.inventory, pylon);
		}
		
		return null;
	}
	
	
}
