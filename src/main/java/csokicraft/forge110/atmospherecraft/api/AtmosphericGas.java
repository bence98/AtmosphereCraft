package csokicraft.forge110.atmospherecraft.api;

public class AtmosphericGas implements IAtmosphericGas {
	private GasType type;
	private double qty;
	
	public AtmosphericGas(GasType t){
		this(t, 0);
	}
	
	public AtmosphericGas(GasType t, double val){
		type=t;
		qty=val;
	}

	@Override
	public GasType getType() {
		return type;
	}

	@Override
	public double getAmount() {
		return qty;
	}

	@Override
	public IAtmosphericGas add(IAtmosphericGas gas) {
		if(!gas.getType().equals(type)) return null;
		qty += gas.getAmount();
		return this;
	}

	@Override
	public IAtmosphericGas times(double x) {
		return new AtmosphericGas(type, qty*x);
	}
}
