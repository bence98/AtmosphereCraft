package csokicraft.forge110.atmospherecraft.api;

public interface IAtmosphericGas{
	public GasType getType();
	public double getAmount();
	/** Adds the given gas to this one. If types don't match, it will return null.
	  * @return this */
	public IAtmosphericGas add(IAtmosphericGas gas);
	/** @return this gas with its amount multiplied by "x" */
	public IAtmosphericGas times(double x);
}
