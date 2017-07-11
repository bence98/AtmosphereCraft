package csokicraft.forge110.atmospherecraft.tileentity;

import java.util.*;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AtmosphericTileEntityRemapper{
	protected static Map<Class<? extends TileEntity>, Class<? extends TileEntity>> classMap = new HashMap<>();
	private static boolean init = false;
	
	public static TileEntity remap(TileEntity te){
		try{
			Class<? extends TileEntity> cls = classMap.get(te.getClass());
			if(cls == null) return null;
			TileEntity new_te = cls.newInstance();
			
			NBTTagCompound tag = new NBTTagCompound();
			te.writeToNBT(tag);
			new_te.readFromNBT(tag);
			return te;
		}catch(Exception e){
			throw new RuntimeException("Couldn't remap "+te.getClass().getName()+"; unexpected "+e.getClass().getName()+": "+e.getMessage(), e);
		}
	}
	
	public static void init(){
		if(init) throw new IllegalStateException("AtmosphericTileEntityRemapper was already initialised!");
		classMap.put(TileEntityFurnace.class, TileEntityAtmosphericFurnace.class);
		init=true;
	}
}
