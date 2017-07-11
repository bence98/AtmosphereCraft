package csokicraft.forge110.atmospherecraft.api;

import java.io.*;
import java.util.*;

import org.apache.commons.io.FileUtils;

import csokicraft.forge110.atmospherecraft.AtmosphereCraft;
import csokicraft.forge110.atmospherecraft.api.*;
import net.minecraft.nbt.*;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.util.Constants.NBT;

public class AtmosphereSaver{
	//save
	public static void saveServer(AtmosphericServer srv, File dir){
		if(!dir.isDirectory())
			AtmosphereCraft.logger.severe(dir.getAbsolutePath()+" is not a directory! The atmosphere couldn't be saved!");
		try{
			for(int id:srv.worlds.keySet()){
				File save=new File(dir, "dim"+id+".dat");
				NBTTagCompound tag = saveWorld(srv.getForID(id));
				CompressedStreamTools.writeCompressed(tag, new FileOutputStream(save));
			}
		}catch(IOException ex){
			AtmosphereCraft.logger.severe("An I/O error occured ("+ex.getClass().getName()+":"+ex.getMessage()+"). The atmosphere couldn't be saved!");
			ex.printStackTrace();
			try {
				FileUtils.deleteDirectory(dir);
			}catch (IOException e1){
				throw new RuntimeException(e1);
			}
		}
	}
	
	public static NBTTagCompound saveWorld(AtmosphericWorld w){
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagList nbtlist = new NBTTagList();
		for(ChunkPos pos:w.chunks.keySet()){
			AtmosphericChunk ch = w.chunks.get(pos);
			NBTTagCompound nbtchunk = saveChunk(ch);
			nbtchunk.setTag("pos", saveChunkpos(pos));
			nbtlist.appendTag(nbtchunk);
		}
		nbt.setTag("chunks", nbtlist);
		return nbt;
	}
	
	public static NBTTagList saveChunkpos(ChunkPos pos){
		NBTTagList nbtlist = new NBTTagList();
		NBTTagInt tagX = new NBTTagInt(pos.chunkXPos),
				  tagZ = new NBTTagInt(pos.chunkZPos);
		nbtlist.appendTag(tagX);
		nbtlist.appendTag(tagZ);
		return nbtlist;
	}

	public static NBTTagCompound saveChunk(AtmosphericChunk ch){
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("gases", saveAtmProvider(ch));
		nbt.setTag("dyna", saveDynProvider(ch));
		return nbt;
	}
	
	public static NBTTagList saveAtmProvider(IAtmosphereProvider prov){
		NBTTagList nbtlist = new NBTTagList();
		for(IAtmosphericGas gas:prov.getGases())
			nbtlist.appendTag(saveGas(gas));
		return nbtlist;
	}
	
	public static NBTTagList saveDynProvider(IAtmosphereDynamicsProvider prov){
		NBTTagList nbtlist = new NBTTagList();
		for(GasType gas:GasRegistry.inst.registeredGases()){
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("gasid", gas.id);
			nbt.setBoolean("in", prov.canReceive(gas));
			nbt.setBoolean("out", prov.canDisperse(gas));
			nbtlist.appendTag(nbt);
		}
		return nbtlist;
	}

	public static NBTTagCompound saveGas(IAtmosphericGas gas){
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("gas_id", GasRegistry.inst.idOf(gas.getType()));
		nbt.setDouble( "amount", gas.getAmount());
		return nbt;
	}
	
	//load
	public static AtmosphericServer loadServer(File dir){
		AtmosphericServer srv = new AtmosphericServer();
		
		if(dir.exists()&&dir.isDirectory()){
			try{
				for(File f:dir.listFiles()){
					String fname=f.getName();
					int id=Integer.parseInt(fname.substring(3, fname.length()-4));
					NBTTagCompound tag=CompressedStreamTools.readCompressed(new FileInputStream(f));
					srv.addWorld(id, loadWorld(tag, id));
				}
			}catch(IOException ex){
				AtmosphereCraft.logger.severe("An I/O error occured ("+ex.getClass().getName()+":"+ex.getMessage()+"). The atmosphere couldn't be loaded!");
				ex.printStackTrace();
			}
		}
		
		return srv;
	}
	
	public static AtmosphericWorld loadWorld(NBTTagCompound nbt, int id){
		NBTTagList nbtlist = nbt.getTagList("chunks", NBT.TAG_COMPOUND);
		Map<ChunkPos, AtmosphericChunk> m = new HashMap<>();
		for(int i=0;i<nbtlist.tagCount();i++){
			NBTTagCompound compound = nbtlist.getCompoundTagAt(i);
			ChunkPos pos = loadChunkpos(compound.getTagList("pos", NBT.TAG_INT));
			m.put(pos, loadChunk(compound, pos));
		}
		return new AtmosphericWorld(id, m);
	}
	
	public static ChunkPos loadChunkpos(NBTTagList nbt){
		return new ChunkPos(nbt.getIntAt(0), nbt.getIntAt(1));
	}
	
	public static AtmosphericChunk loadChunk(NBTTagCompound nbt, ChunkPos cp){
		AtmosphericChunk ach = new AtmosphericChunk(cp, loadAtmProvider(nbt.getTagList("gases", NBT.TAG_COMPOUND)));
		loadDynProvider(nbt.getTagList("dyna", NBT.TAG_COMPOUND), ach);
		return ach;
	}
	
	public static Map<GasType, IAtmosphericGas> loadAtmProvider(NBTTagList nbtlist){
		Map<GasType, IAtmosphericGas> m = new HashMap<>();
		for(int i=0;i<nbtlist.tagCount();i++){
			IAtmosphericGas gas = loadGas(nbtlist.getCompoundTagAt(i));
			m.put(gas.getType(), gas);
		}
		return m;
	}
	
	public static void loadDynProvider(NBTTagList nbtlist, IAtmosphereDynamicsProvider prov){
		for(int i=0;i<nbtlist.tagCount();i++){
			NBTTagCompound tag = nbtlist.getCompoundTagAt(i);
			GasType gas=GasRegistry.inst.get(tag.getInteger("gasid"));
			prov.setCanReceive(gas, tag.getBoolean("in"));
			prov.setCanDisperse(gas, tag.getBoolean("out"));
		}
	}
	
	public static IAtmosphericGas loadGas(NBTTagCompound nbt){
		return new AtmosphericGas(GasRegistry.inst.get(nbt.getInteger("gas_id")), nbt.getDouble("amount"));
	}
}
