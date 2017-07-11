package csokicraft.forge110.atmospherecraft.tileentity;

import java.util.*;

import csokicraft.forge110.atmospherecraft.AtmosphereCraft;
import csokicraft.forge110.atmospherecraft.api.*;
import csokicraft.forge110.atmospherecraft.item.*;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntity;

public class TileEntityAbsorber extends TileEntityInventory{

	public TileEntityAbsorber(){
		super(2);
	}
	
	@Override
	protected String getDefaultName(){
		return "absorber";
	}
	
	@Override
	public void update(){
		AtmosphericChunk ach=AtmosphereCraft.getAtmosphericServer().getForWorld(worldObj).getChunk(getPos());
		if(checkInput()&&checkOutput()){
			double volume=ach.getVolume();
			List<IAtmosphericGas> bottled = new ArrayList<>();
			for(IAtmosphericGas gas:ach.getGases()){
				AtmosphericGas tmp = new AtmosphericGas(gas.getType(), gas.getAmount()*100/volume);
				ach.removeGas(tmp);
				bottled.add(tmp);
			}
			setInventorySlotContents(1, GasBottleStack.toItemStack(new GasBottleStack(bottled)));
		}
	}
	
	private boolean checkInput(){
		return getStackInSlot(0)!=null&&getStackInSlot(0).getItem().equals(Items.GLASS_BOTTLE);
	}
	
	private boolean checkOutput(){
		return getStackInSlot(1)==null||getStackInSlot(1).stackSize<=0;
	}
}
