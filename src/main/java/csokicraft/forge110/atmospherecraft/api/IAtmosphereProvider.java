package csokicraft.forge110.atmospherecraft.api;

import java.util.List;

import net.minecraft.util.math.Vec3d;

/** An interface for gas containers
  * @author CsokiCraft */
public interface IAtmosphereProvider{
	/** Adds the gas to this provider's storage.
	  * @return true if the operation succeeded, false if failed ( most likely capacity overflow/underflow)*/
	public boolean addGas(IAtmosphericGas gas);
	/** Takes the gas from this provider's storage.
	  * Optimal implementations call <code>addGas(gas.times(-1));</code> */
	public boolean removeGas(IAtmosphericGas gas);
	/** Plays a "quack"-sound. No, of course not. */
	public IAtmosphericGas getGas(GasType type);
	/** @return A read-only list of gases */
	public List<IAtmosphericGas> getGases();
	/** Calculates the sum of gas volumes */
	public double getVolume();
}
