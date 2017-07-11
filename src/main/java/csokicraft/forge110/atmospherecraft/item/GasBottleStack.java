package csokicraft.forge110.atmospherecraft.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import csokicraft.forge110.atmospherecraft.AtmosphereCraft;
import csokicraft.forge110.atmospherecraft.api.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

public class GasBottleStack implements IAtmosphereProvider{
	protected List<IAtmosphericGas> gases;
	
	public GasBottleStack(List<IAtmosphericGas> l){
		gases=l;
	}
	
	public GasBottleStack() {
		this(new ArrayList<IAtmosphericGas>());
	}
	
	public static ItemStack toItemStack(GasBottleStack st){
		ItemStack is = new ItemStack(AtmosphereCraft.gasBottle);
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagList nbtlist = new NBTTagList();
		for(IAtmosphericGas gas:st.gases)
			nbtlist.appendTag(AtmosphereSaver.saveGas(gas));
		nbt.setTag("gases", nbtlist);
		is.setTagCompound(nbt);
		return is;
	}
	
	public static GasBottleStack fromItemStack(ItemStack is){
		NBTTagCompound nbt = is.getTagCompound();
		if(nbt != null){
			NBTTagList nbtlist = nbt.getTagList("gases", 0);
			if(nbtlist != null){
				List<IAtmosphericGas> lg=new ArrayList<>();
				for(int i=0;i<nbtlist.tagCount();i++){
					NBTTagCompound tag=nbtlist.getCompoundTagAt(i);
					lg.add(AtmosphereSaver.loadGas(tag));
				}
				return new GasBottleStack(lg);
			}
		}
		
		return new GasBottleStack();
	}

	@Override
	public boolean addGas(IAtmosphericGas gas){
		IAtmosphericGas local=getGas(gas.getType());
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeGas(IAtmosphericGas gas){
		return addGas(gas.times(-1));
	}

	@Override
	public IAtmosphericGas getGas(GasType type){
		for(IAtmosphericGas gas:gases)
			if(gas.getType().equals(type))
				return gas;
		return null;
	}

	@Override
	public List<IAtmosphericGas> getGases(){
		return Collections.unmodifiableList(gases);
	}

	@Override
	public double getVolume(){
		// TODO Auto-generated method stub
		return 0;
	}
}
