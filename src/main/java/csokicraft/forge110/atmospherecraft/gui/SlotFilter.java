package csokicraft.forge110.atmospherecraft.gui;

import csokicraft.forge110.atmospherecraft.AtmosphereCraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFilter extends Slot{

	public SlotFilter(IInventory inventoryIn, int index, int xPosition, int yPosition){
		super(inventoryIn, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack){
		return stack.getItem().equals(AtmosphereCraft.gasFilter);
	}
}
