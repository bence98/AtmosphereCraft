package csokicraft.forge110.atmospherecraft.item;

import java.util.*;

import csokicraft.forge110.atmospherecraft.*;
import csokicraft.forge110.atmospherecraft.api.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;


public class ItemGasBottle extends Item{
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced){
		addInformation(GasBottleStack.fromItemStack(stack), tooltip);
		super.addInformation(stack, playerIn, tooltip, advanced);
	}

	private void addInformation(GasBottleStack stack, List<String> l){
		if(!stack.gases.isEmpty()){
			if(l.size()>1)
				l.add("Stored gases:");
			else
				l.add("Stored gas:");
			for(IAtmosphericGas gas:stack.gases){
				l.add(AtmosphereCmd.getGasAsString(gas));
			}
		}else{
			l.add("<Empty>");
		}
	}
}
