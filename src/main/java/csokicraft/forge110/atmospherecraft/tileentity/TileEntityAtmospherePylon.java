package csokicraft.forge110.atmospherecraft.tileentity;

import java.util.*;

import csokicraft.forge110.atmospherecraft.AtmosphereCraft;
import csokicraft.forge110.atmospherecraft.api.*;
import csokicraft.forge110.atmospherecraft.network.PacketPylon;
import net.minecraft.item.ItemStack;

public class TileEntityAtmospherePylon extends TileEntityInventory{
	private boolean filterIn=true;
	private boolean filterOut=true;

	public TileEntityAtmospherePylon(){
		super(1);
	}

	@Override
	public void update(){
		ItemStack is=getStackInSlot(0);
		if(is!=null&&is.getItem().equals(AtmosphereCraft.gasFilter)){
			GasType type=GasRegistry.inst.get(is.getItemDamage());
			AtmosphericChunk ach=AtmosphereCraft.getAtmosphericServer().getForWorld(worldObj).getChunk(getPos());
			ach.setCanReceive(type, canFilterIn());
			ach.setCanDisperse(type, canFilterOut());
		}
	}
	
	@Override
	public void invalidate(){
		ItemStack is=getStackInSlot(0);
		if(is!=null&&is.getItem().equals(AtmosphereCraft.gasFilter)){
			GasType type=GasRegistry.inst.get(is.getItemDamage());
			AtmosphericChunk ach=AtmosphereCraft.getAtmosphericServer().getForWorld(worldObj).getChunk(getPos());
			ach.setCanReceive(type, true);
			ach.setCanDisperse(type, true);
		}
		super.invalidate();
	}

	@Override
	protected String getDefaultName(){
		return "pylon";
	}

	public boolean canFilterIn(){
		return filterIn;
	}

	public void setFilterIn(boolean b){
		filterIn = b;
		if(worldObj.isRemote) AtmosphereCraft.nethndl.sendToServer(new PacketPylon(this));
	}
	
	public boolean canFilterOut(){
		return filterOut;
	}

	public void setFilterOut(boolean b){
		filterOut = b;
		if(worldObj.isRemote) AtmosphereCraft.nethndl.sendToServer(new PacketPylon(this));
	}

	public byte getStateByte(){
		return (byte) ((filterIn?1:0)+(filterOut?2:0));
	}

	public void setStateByte(byte payload){
		filterIn=(payload%2==1);
		filterOut=(payload/2==1);
	}
}
