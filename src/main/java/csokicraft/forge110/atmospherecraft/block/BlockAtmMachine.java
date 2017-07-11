package csokicraft.forge110.atmospherecraft.block;

import csokicraft.forge110.atmospherecraft.AtmosphereCraft;
import csokicraft.forge110.atmospherecraft.CommonProxy;
import csokicraft.forge110.atmospherecraft.tileentity.*;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAtmMachine extends BlockContainer{

	public BlockAtmMachine(){
		super(Material.IRON);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta){
		return new TileEntityAtmospherePylon();
	}

	@Override
	public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer p, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
		if(!w.isRemote)
			p.openGui(AtmosphereCraft.INSTANCE, CommonProxy.GUI_ID_PYLON, w, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
}
