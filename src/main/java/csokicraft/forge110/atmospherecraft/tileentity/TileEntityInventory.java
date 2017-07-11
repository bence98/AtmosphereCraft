package csokicraft.forge110.atmospherecraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.*;

public abstract class TileEntityInventory extends TileEntity implements IInventory, ITickable{
	protected String customName;
	protected ItemStackHandler itemhandler;
	
	public TileEntityInventory(int sizeInv){
		itemhandler=new ItemStackHandler(sizeInv);
	}
	
	protected abstract String getDefaultName();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing){
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing){
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) itemhandler;
		return super.getCapability(capability, facing);
	}
	
	//IInventory

	@Override
	public String getName(){
		return hasCustomName()?getName():"container."+getDefaultName()+".name";
	}

	@Override
	public boolean hasCustomName(){
		return customName!=null;
	}

	@Override
	public int getSizeInventory(){
		return itemhandler.getSlots();
	}

	@Override
	public ItemStack getStackInSlot(int index){
		return itemhandler.getStackInSlot(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count){
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index){
		ItemStack is=getStackInSlot(index);
		setInventorySlotContents(index, null);
		return is;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack){
		itemhandler.setStackInSlot(index, stack);
	}

	@Override
	public int getInventoryStackLimit(){
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player){
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player){}

	@Override
	public void closeInventory(EntityPlayer player){}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack){
		return true;
	}

	@Override
	public int getField(int id){
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value){
		// TODO Auto-generated method stub
	}

	@Override
	public int getFieldCount(){
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear(){
		itemhandler.setSize(getSizeInventory());
	}

}
