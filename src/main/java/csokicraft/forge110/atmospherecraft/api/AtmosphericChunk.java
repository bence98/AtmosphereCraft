package csokicraft.forge110.atmospherecraft.api;

import java.util.*;

import csokicraft.forge110.atmospherecraft.api.*;
import net.minecraft.util.math.*;
import net.minecraft.world.chunk.Chunk;

public class AtmosphericChunk implements IAtmosphereProvider, IAtmosphereDynamicsProvider{
	protected Map<GasType, IAtmosphericGas> gases;
	protected Map<GasType, Boolean> canOutput, canInput;
	protected ChunkPos coords;
	
	public AtmosphericChunk(ChunkPos cp){
		gases = Collections.synchronizedMap(new HashMap<GasType, IAtmosphericGas>());
		canOutput = Collections.synchronizedMap(new HashMap<GasType, Boolean>());
		canInput = Collections.synchronizedMap(new HashMap<GasType, Boolean>());
		coords = cp;
	}
	
	public AtmosphericChunk(ChunkPos cp, Map<GasType, IAtmosphericGas> m){
		this(cp);
		gases.putAll(m);
	}
	
	public boolean addGas(IAtmosphericGas gas){
		IAtmosphericGas tmp=getGas(gas.getType());
		
		if(tmp.getAmount()<-gas.getAmount()) //check for capacity underflow i.e. when (tmp+gas).getAmount()<0
			return false;
		
		tmp.add(gas);
		if(tmp.getAmount()<0){
			gases.remove(gas.getType());
		}
		return true;
	}
	
	public boolean removeGas(IAtmosphericGas gas){
		return addGas(gas.times(-1));
	}

	@Override
	public IAtmosphericGas getGas(GasType type){
		if(!gases.containsKey(type))
			gases.put(type, new AtmosphericGas(type));
		return gases.get(type);
	}

	@Override
	public List<IAtmosphericGas> getGases(){
		List<IAtmosphericGas> l = new ArrayList<>();
		l.addAll(gases.values());
		return Collections.unmodifiableList(l);
	}
	
	public ChunkPos getCoords(){
		return coords;
	}
	
	public double getVolume(){
		double volume=0;
		for(IAtmosphericGas gas:gases.values()){
			volume+=gas.getAmount();
		}
		return volume;
	}

	@Override
	public boolean canDisperse(GasType gas){
		if(!canOutput.containsKey(gas))
			setCanDisperse(gas, true);
		return canOutput.get(gas);
	}

	@Override
	public boolean canReceive(GasType gas){
		if(!canInput.containsKey(gas))
			setCanReceive(gas, true);
		return canInput.get(gas);
	}

	@Override
	public void setCanDisperse(GasType type, boolean bool){
		canOutput.put(type, bool);
	}

	@Override
	public void setCanReceive(GasType type, boolean bool){
		canInput.put(type, bool);
	}
}
