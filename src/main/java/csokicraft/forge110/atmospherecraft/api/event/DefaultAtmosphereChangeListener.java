package csokicraft.forge110.atmospherecraft.api.event;

import java.util.*;

import javax.vecmath.Point2i;

import csokicraft.forge110.atmospherecraft.AtmosphereCraft;
import csokicraft.forge110.atmospherecraft.api.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.*;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;

public class DefaultAtmosphereChangeListener implements IAtmosphereChangeListener{
	protected AtmosphericServer srv;
	
	public DefaultAtmosphereChangeListener(AtmosphericServer asrv){
		srv = asrv;
	}
	
	@Override
	public boolean onEvent(AtmosphereChangeEvent e){
		switch(e.getEventType()){
		case CHUNK_CREATE:
			onChunkCreated(e.getWorld(), e.getChunk());
			return true;
		case TICK:
			onTick(e.getWorld(), e.getChunk());
			return true;
		case WORLD_CREATE:
			onWorldCreated(e.getWorld());
			return true;
		default:
			AtmosphereCraft.logger.severe("Unknown change event: "+e.getEventType());
			return false;
		}
	}
	
	protected void onWorldCreated(AtmosphericWorld aw){}
	
	protected void onChunkCreated(AtmosphericWorld aw, AtmosphericChunk ach){
		List<IAtmosphericGas> l = GasConstants.getDefaultsFor(srv.getID(aw));
		for(IAtmosphericGas g:l)
			ach.addGas(g);
	}
	
	protected void onTick(AtmosphericWorld aw, AtmosphericChunk ach){
		//status effects
		if(true){//TODO add config option check
			World w=srv.getWorldOf(aw);
			AxisAlignedBB aabb=new AxisAlignedBB(ach.getCoords().getXStart(), 0, ach.getCoords().getZStart(),
												 ach.getCoords().getXEnd(), w.getMinecraftServer().getBuildLimit(), ach.getCoords().getZEnd());
			List<EntityLivingBase> l = w.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
			for(EntityLivingBase e:l){
				affect((EntityLivingBase) e, ach);
			}
		}
		//gas flow
		for(GasType type:GasRegistry.inst.registeredGases()){
			if(ach.canDisperse(type))
				gasFlow(aw, ach, type);
		}
	}
	
	private void gasFlow(AtmosphericWorld aw, AtmosphericChunk ach, GasType type){
		Point2i offsets[]=new Point2i[]{new Point2i(0, 1), new Point2i(0, -1), new Point2i(1, 0), new Point2i(-1, 0)};
		for(Point2i ofs:offsets){
			ChunkPos chpos=new ChunkPos(ach.getCoords().chunkXPos+ofs.x, ach.getCoords().chunkZPos+ofs.y);
			if(!aw.hasChunk(chpos))
				continue;
			AtmosphericChunk dest=aw.getChunk(chpos);
			double difference=ach.getGas(type).getAmount()-dest.getGas(type).getAmount();
			
			if(!dest.canReceive(type)||difference<=0)
				continue;
			
			double transfer=Math.min(1, difference);
			AtmosphericGas flow=new AtmosphericGas(type, transfer);
			if(dest.addGas(flow))
				ach.removeGas(flow);
		}
	}

	private void affect(EntityLivingBase ent, AtmosphericChunk ach){
		if(ach.getGas(GasConstants.o2).getAmount()<10){
			ent.addPotionEffect(new PotionEffect(Potion.getPotionById(15), 30));
			ent.attackEntityFrom(DamageSource.drown, 1);
		}
	}
}