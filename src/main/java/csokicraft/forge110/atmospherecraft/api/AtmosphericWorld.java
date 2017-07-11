package csokicraft.forge110.atmospherecraft.api;

import java.util.*;

import csokicraft.forge110.atmospherecraft.AtmosphereCraft;
import csokicraft.forge110.atmospherecraft.api.*;
import csokicraft.forge110.atmospherecraft.api.event.AtmosphereChangeEvent;
import csokicraft.forge110.atmospherecraft.api.event.AtmosphereChangeEvent.ChangeEventType;
import net.minecraft.util.math.*;

public class AtmosphericWorld{
	protected Map<ChunkPos, AtmosphericChunk> chunks;
	
	public AtmosphericWorld(int i){
		chunks = Collections.synchronizedMap(new HashMap<ChunkPos, AtmosphericChunk>());
	}
	
	public AtmosphericWorld(int i, Map<ChunkPos, AtmosphericChunk> m){
		this(i);
		chunks.putAll(m);
	}

	public AtmosphericChunk getChunk(BlockPos pos){
		return getChunk(new ChunkPos(pos));
	}
	
	public AtmosphericChunk getChunk(Vec3d pos){
		return getChunk(new BlockPos(pos));
	}
	
	public AtmosphericChunk getChunk(ChunkPos chpos){
		if(!hasChunk(chpos)){
			AtmosphericChunk ach = new AtmosphericChunk(chpos);
			chunks.put(chpos, ach);
			AtmosphereCraft.getAtmosphericServer().getAtmosphereChangeNotifier().fireEvent(new AtmosphereChangeEvent(this, ach, ChangeEventType.CHUNK_CREATE));
		}
		return chunks.get(chpos);
	}
	
	public boolean hasChunk(ChunkPos chpos){
		return chunks.containsKey(chpos);
	}
}
