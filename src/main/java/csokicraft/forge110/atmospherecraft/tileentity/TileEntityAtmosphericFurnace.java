package csokicraft.forge110.atmospherecraft.tileentity;

import csokicraft.forge110.atmospherecraft.*;
import csokicraft.forge110.atmospherecraft.api.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.*;
import net.minecraftforge.fml.common.Loader;

public class TileEntityAtmosphericFurnace extends TileEntityFurnace{
	public static final double GAS_CONSUMPTION = .1;
	
	public TileEntityAtmosphericFurnace(){}
	
	@Override
	public void update(){
		super.update();
		if(isBurning()){
			AtmosphericChunk ach = AtmosphereCraft.getAtmosphericServer().getForWorld(worldObj).getChunk(this.getPos());
			if(ach.removeGas(new AtmosphericGas(GasConstants.o2, GAS_CONSUMPTION)))
				ach.addGas(new AtmosphericGas(GasConstants.co2, GAS_CONSUMPTION));
			else ach.addGas(new AtmosphericGas(GasConstants.smoke, GAS_CONSUMPTION*2));
		}
	}
}
