package csokicraft.forge110.atmospherecraft;

import java.io.*;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

import csokicraft.forge110.atmospherecraft.api.*;
import csokicraft.forge110.atmospherecraft.block.*;
import csokicraft.forge110.atmospherecraft.item.*;
import csokicraft.forge110.atmospherecraft.network.*;
import csokicraft.forge110.atmospherecraft.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.*;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

/** "Brings the Minecraft atmosphere to life!"
  * @author CsokiCraft */
@Mod(modid = AtmosphereCraft.MODID, version=AtmosphereCraft.VERSION)
public class AtmosphereCraft{
	public static final String MODID = "AtmosphereCraft",
							   VERSION = "2.0-pre2";
	
	@SidedProxy(serverSide="csokicraft.forge110.atmospherecraft.CommonProxy", clientSide="csokicraft.forge110.atmospherecraft.ClientProxy")
	public static CommonProxy proxy;
	@Instance
	public static AtmosphereCraft INSTANCE;
	public static SimpleNetworkWrapper nethndl;
	public static Logger logger = Logger.getLogger(AtmosphereCraft.MODID);
	public static CreativeTabs tab=new CreativeTabs("atm_machines"){
		@Override
		public Item getTabIconItem(){
			return net.minecraft.init.Items.GLASS_BOTTLE;
		}
	};
	public static Item gasBottle = new ItemGasBottle();
	public static Item gasFilter = new ItemGasFilter().setCreativeTab(tab).setUnlocalizedName("gas_filter").setRegistryName("gasFilter");
	public static Block blockMachine = new BlockAtmMachine().setUnlocalizedName("machine").setRegistryName("machine");
	public static Item itemMachine = new ItemBlock(blockMachine).setCreativeTab(tab).setUnlocalizedName("machine").setRegistryName("machine");
	
	protected static AtmosphericServer atm_srv = null;
	
	@EventHandler
	public void init(FMLInitializationEvent e){
		nethndl=new SimpleNetworkWrapper(MODID);
		nethndl.registerMessage(HandlerPylonServer.class, PacketPylon.class, PacketPylon.ID, Side.SERVER);
		nethndl.registerMessage(HandlerPylonClient.class, PacketPylon.class, PacketPylon.ID, Side.CLIENT);
		
		MinecraftForge.EVENT_BUS.register(this);
		NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
		GasConstants.init();
		
		GameRegistry.registerTileEntity(TileEntityAtmosphericFurnace.class, MODID+":furnace");
		GameRegistry.registerTileEntity(TileEntityAtmospherePylon.class, MODID+":pylon");

		GameRegistry.register(gasFilter);
		GameRegistry.register(blockMachine);
		GameRegistry.register(itemMachine);
	}
	
	@EventHandler
	public void onServerLoad(FMLServerStartingEvent e){
		atm_srv=AtmosphereSaver.loadServer(new File(getWorldDir(), "atmosphere"));
		e.registerServerCommand(new AtmosphereCmd());
	}
	
	@EventHandler
	public void onServerUnload(FMLServerStoppingEvent e){
		File dir=new File(getWorldDir(), "atmosphere");
		if(!dir.exists())
			dir.mkdirs();
		AtmosphereSaver.saveServer(atm_srv, dir);
		
		atm_srv = null;
	}
	
	private File getWorldDir(){
		return new File(FMLCommonHandler.instance().getSavesDirectory(), FMLCommonHandler.instance().getMinecraftServerInstance().getWorldName());
	}
	
	/** Gets the current {@link AtmosphericServer}. Will return null if there's no server running.*/
	public static AtmosphericServer getAtmosphericServer(){
		return atm_srv;
	}
	
	@SubscribeEvent
	public void onBlockPlaced(PlaceEvent e){
		World w = e.getWorld();
		TileEntity te = w.getTileEntity(e.getPos());
		TileEntity new_te = AtmosphericTileEntityRemapper.remap(te);
		if(new_te != null) w.setTileEntity(e.getPos(), new_te);
	}
	
	@SubscribeEvent
	public void onTick(TickEvent.ServerTickEvent e){
		if(e.phase == Phase.END)
			atm_srv.update();
	}
}
