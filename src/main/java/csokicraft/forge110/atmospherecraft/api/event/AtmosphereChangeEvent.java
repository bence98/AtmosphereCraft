package csokicraft.forge110.atmospherecraft.api.event;

import csokicraft.forge110.atmospherecraft.api.AtmosphericChunk;
import csokicraft.forge110.atmospherecraft.api.AtmosphericWorld;

public class AtmosphereChangeEvent{
	protected AtmosphericWorld w;
	protected AtmosphericChunk ch;
	protected ChangeEventType t;
	
	public AtmosphereChangeEvent(AtmosphericWorld world, AtmosphericChunk chunk, ChangeEventType type){
		w = world;
		ch = chunk;
		t = type;
	}
	
	public AtmosphericWorld getWorld(){
		return w;
	}
	public AtmosphericChunk getChunk(){
		return ch;
	}
	public ChangeEventType getEventType(){
		return t;
	}
	
	public static enum ChangeEventType{
		WORLD_CREATE,
		CHUNK_CREATE,
		TICK
	}
}
