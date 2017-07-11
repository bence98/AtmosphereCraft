package csokicraft.forge110.atmospherecraft.api;

import java.util.*;

import csokicraft.forge110.atmospherecraft.api.*;
import csokicraft.forge110.atmospherecraft.api.event.AtmosphereChangeEvent;
import csokicraft.forge110.atmospherecraft.api.event.DefaultAtmosphereChangeListener;
import csokicraft.forge110.atmospherecraft.api.event.AtmosphereChangeNotifier;
import csokicraft.forge110.atmospherecraft.api.event.AtmosphereChangeEvent.ChangeEventType;
import csokicraft.util.CollectionUtils;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;

public class AtmosphericServer implements ITickable{
	protected Map<Integer, AtmosphericWorld> worlds;
	protected Map<AtmosphericWorld, Integer> worldids;
	protected AtmosphereChangeNotifier notifier;
	
	public AtmosphericServer(){
		worlds = Collections.synchronizedMap(new HashMap<Integer, AtmosphericWorld>());
		worldids = Collections.synchronizedMap(new HashMap<AtmosphericWorld, Integer>());
		notifier = new AtmosphereChangeNotifier(new DefaultAtmosphereChangeListener(this));
	}

	public AtmosphericWorld getForID(int i){
		if(!worlds.containsKey(i)){
			AtmosphericWorld aw = new AtmosphericWorld(i);
			addWorld(i, aw);
			notifier.fireEvent(new AtmosphereChangeEvent(aw, null, ChangeEventType.WORLD_CREATE));
		}
		return worlds.get(i);
	}
	
	protected void addWorld(int i, AtmosphericWorld aw){
			worlds.put(i, aw);
			worldids.put(aw, i);
	}
	
	public AtmosphericWorld getForWorld(World w){
		return getForID(w.provider.getDimension());
	}
	
	public int getID(AtmosphericWorld aw){
		return worldids.get(aw);
	}
	
	public World getWorldOf(AtmosphericWorld aw){
		return DimensionManager.getWorld(getID(aw));
	}

	public Chunk getChunkOf(AtmosphericWorld aw, AtmosphericChunk ach){
		ChunkPos chpos=ach.getCoords();
		return getWorldOf(aw).getChunkFromChunkCoords(chpos.chunkXPos, chpos.chunkZPos);
	}
	
	public AtmosphereChangeNotifier getAtmosphereChangeNotifier(){
		return notifier;
	}
	
	public void update(){
		synchronized (worlds){
			for(AtmosphericWorld aw:worlds.values()){
				synchronized (aw.chunks){
					List<AtmosphericChunk> l=CollectionUtils.toList(aw.chunks.values());
					for(AtmosphericChunk ach:l){
						notifier.fireEvent(new AtmosphereChangeEvent(aw, ach, ChangeEventType.TICK));
					}
				}
			}
		}
	}
}
