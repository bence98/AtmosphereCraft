package csokicraft.forge110.atmospherecraft.gui;

import csokicraft.forge110.atmospherecraft.tileentity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerAtmPylon extends Container{
	protected TileEntityAtmospherePylon te;

	public ContainerAtmPylon(InventoryPlayer inv, TileEntityAtmospherePylon tile) {
		te=tile;
		this.addSlotToContainer(new SlotFilter(te, 0, 95, 0));
		
		/*Borrowed code. This should totally be a public static function in vanilla/Forge */
		for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(inv, k, 8 + k * 18, 142));
        }
		/* End of borrowed code */
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn){
		return te.isUseableByPlayer(playerIn);
	}

}
