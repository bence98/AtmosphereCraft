package csokicraft.forge110.atmospherecraft.api;

import java.util.*;

public class GasConstants{
	/** The volume of a chunk in litres (16*16*256 cubic metres) */
	public static final double STANDARD_CHUNK_VOL = 65_536_000;
	
	public static GasType n2 = new GasType(0, "N2"),
			  			  o2 = new GasType(1, "O2"),
						  co2 = new GasType(2, "CO2"),
						  h2o = new GasType(3, "H2O"),
						  ch4 = new GasType(4, "CH4"),
						  smoke = new GasType(5, "smoke");
	
	/** dimID -> [defaultGases] */
	public static Map<Integer, List<IAtmosphericGas>> defaults = new HashMap<>();
	
	private static boolean init = false;
	
	public static void init(){
		if(init) throw new IllegalStateException("GasConstants already initialised!");
		
		GasRegistry.inst.register(n2);
		GasRegistry.inst.register(o2);
		GasRegistry.inst.register(co2);
		GasRegistry.inst.register(h2o);
		GasRegistry.inst.register(ch4);
		GasRegistry.inst.register(smoke);
		
		putDefault(0, n2, .75);
		putDefault(0, o2, .24);
		putDefault(0, co2, .01);
		putDefault(-1, smoke, .85);
		putDefault(-1, co2, .10);
		putDefault(-1, ch4, .05);
		putDefault(1, n2, .90);
		putDefault(1, ch4, .09);
		putDefault(1, smoke, .01);
		
		init = true;
	}
	
	public static void putDefault(int dimID, GasType type, double percent){
		List<IAtmosphericGas> l = getDefaultsFor(dimID);
		l.add(new AtmosphericGas(type, STANDARD_CHUNK_VOL*percent));
	}
	
	public static List<IAtmosphericGas> getDefaultsFor(int dimID){
		if(!defaults.containsKey(dimID))
			defaults.put(dimID, new ArrayList<IAtmosphericGas>());
		return defaults.get(dimID);
	}
}