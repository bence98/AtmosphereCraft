package csokicraft.forge110.atmospherecraft.api;

import java.util.*;

import com.google.common.collect.*;

public class GasRegistry{
	public static GasRegistry inst = new GasRegistry();
	
	private BiMap<Integer, GasType> gases;
	
	protected GasRegistry(){
		gases = HashBiMap.<Integer, GasType>create();
	}
	
	public void register(GasType t){
		if(t.id==-1) t.id=gases.size();
		if(gases.get(t.id) != null)
			throw new IllegalArgumentException("There was already a gas registered "+t.id+" ("+gases.get(t.id).getName()+"), when adding "+t.getName());
		gases.put(t.id, t);
	}
	
	public GasType get(int id){
		return gases.get(id);
	}
	
	public int idOf(GasType t){
		return gases.inverse().get(t);
	}
	
	/** @return a read-only list of registered gases */
	public List<GasType> registeredGases(){
		List<GasType> l = new ArrayList<>();
		l.addAll(gases.values());
		return Collections.unmodifiableList(l);
	}
}
