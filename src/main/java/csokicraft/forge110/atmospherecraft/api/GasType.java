package csokicraft.forge110.atmospherecraft.api;

public class GasType{
	/** id for gas, or -1 for unregistered */
	protected int id;
	private String name;
	
	public GasType(String name, String shortn){
		this(-1, name);
	}
	
	GasType(int x, String n){
		id=x;
		name=n;
	}
	
	public String getName(){
		return name;
	}
}