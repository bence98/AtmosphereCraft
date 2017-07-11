package csokicraft.forge110.atmospherecraft.gui;

import java.io.IOException;

import csokicraft.forge110.atmospherecraft.tileentity.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiAtmPylon extends GuiContainer{
	TileEntityAtmospherePylon te;

	public GuiAtmPylon(InventoryPlayer inventory, TileEntityAtmospherePylon tile){
		super(new ContainerAtmPylon(inventory, tile));
		te=tile;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		
	}
	
	@Override
	public void updateScreen(){
		super.updateScreen();
		buttonList.get(0).displayString="Entry: "+getState(te.canFilterIn());
		buttonList.get(1).displayString="Exit: "+getState(te.canFilterOut());
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException{
		super.actionPerformed(button);
		switch(button.id){
		case 0:
			te.setFilterIn(!te.canFilterIn());
			break;
		case 1:
			te.setFilterOut(!te.canFilterOut());
			break;
		}
		
	}
	
	@Override
	public void initGui(){
		super.initGui();
		buttonList.add(new GuiButton(0, 20, 30, "Toggle In"));
		buttonList.add(new GuiButton(1, 20, 50, "Toggle Out"));
	}

	private String getState(boolean b){
		return b?"On":"Off";
	}
}
