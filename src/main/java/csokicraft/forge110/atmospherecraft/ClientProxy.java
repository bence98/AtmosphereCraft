package csokicraft.forge110.atmospherecraft;

import csokicraft.forge110.atmospherecraft.item.ItemGasFilter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ClientProxy extends CommonProxy{
	@Override
	public void registerModels(){
		for(int i=0;i<ItemGasFilter.metaCount;i++){
			Item item=AtmosphereCraft.gasFilter;
			register(item, i);
		}
		register(Item.getItemFromBlock(AtmosphereCraft.blockMachine), 0);
	}
	
	public static void register(Item item, int meta){
		ItemModelMesher imm=Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		ModelResourceLocation res=new ModelResourceLocation(AtmosphereCraft.MODID+":"+item.getUnlocalizedName()+"."+meta);
		imm.register(item, meta, res);
		ModelBakery.registerItemVariants(item, res);
	}
}
