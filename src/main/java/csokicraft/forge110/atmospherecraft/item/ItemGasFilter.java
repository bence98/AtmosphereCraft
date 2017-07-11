package csokicraft.forge110.atmospherecraft.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;

public class ItemGasFilter extends Item{
	public static final int metaCount=6;
	
	public ItemGasFilter(){
		hasSubtypes=true;
	}
	
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems){
		for(int i=0;i<metaCount;i++){
			subItems.add(new ItemStack(itemIn, 1, i));
		}
	}
	
	public String getUnlocalizedName(ItemStack stack){
		String s = getUnlocalizedName();
		return s+"."+stack.getItemDamage();
	}
}
