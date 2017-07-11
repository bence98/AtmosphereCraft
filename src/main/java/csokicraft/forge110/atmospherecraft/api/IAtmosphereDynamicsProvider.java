package csokicraft.forge110.atmospherecraft.api;

/** An interface for inter-chunk gas flow mechanics.
  * @author CsokiCraft */
public interface IAtmosphereDynamicsProvider{
	/** Can this DynamicsProvider give gas to other DynamicsProviders? */
	public boolean canDisperse(GasType gas);
	/** Can this DynamicsProvider get gas from other DynamicsProviders? */
	public boolean canReceive(GasType gas);
	public void setCanDisperse(GasType type, boolean bool);
	public void setCanReceive(GasType type, boolean bool);
}
