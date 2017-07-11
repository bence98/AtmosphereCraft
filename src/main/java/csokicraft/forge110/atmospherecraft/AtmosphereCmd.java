package csokicraft.forge110.atmospherecraft;

import java.util.*;

import csokicraft.forge110.atmospherecraft.api.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.*;
import net.minecraft.command.server.CommandMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class AtmosphereCmd extends CommandBase{
	private List<String> aliases = new ArrayList<>();
	
	public AtmosphereCmd(){
		aliases.add(getCommandName());
		aliases.add(getCommandName().substring(0, 3));
	}

	@Override
	public String getCommandName(){
		return "atmosphere";
	}

	@Override
	public String getCommandUsage(ICommandSender sender){
		if(sender.getCommandSenderEntity() == null){
			return "command.atmosphere.usage.name";
		}
		return I18n.format("command.atmosphere.usage.change")+"\n"
			 + I18n.format("command.atmosphere.usage.get")+"\n"
			 + I18n.format("command.atmosphere.usage.name");
	}

	@Override
	public List<String> getCommandAliases(){
		return aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException{
		if(args.length<2) throw new WrongUsageException("command.atmosphere.error.fewargs");
		int gas_id = parseInt(args[1]);
		GasType gas_t = GasRegistry.inst.get(gas_id);
		if(gas_t == null)
			throw new WrongUsageException("command.atmosphere.error.noid", String.valueOf(gas_id));
		
		if(args[0].equalsIgnoreCase("name")){
			boolean fullName = false;
			if(args.length>=3) fullName=parseBoolean(args[2]);
			if(fullName)
				sender.addChatMessage(new TextComponentString(getFullGasName(gas_t)));
			else
				sender.addChatMessage(new TextComponentString(gas_t.getName()));
		}else try{
			EntityPlayer p = getCommandSenderAsPlayer(sender);
			AtmosphericChunk ch = AtmosphereCraft.getAtmosphericServer().getForWorld(p.worldObj).getChunk(p.getPositionVector());
			double gas_qty = ch.getGas(gas_t).getAmount();
			
			if(args[0].equalsIgnoreCase("get")){
				notifyCommandListener(sender, this,"command.atmosphere.success.get", getFullGasName(gas_t), String.valueOf(gas_qty));
			}else{
				if(args.length<3) throw new WrongUsageException("command.atmosphere.error.fewargs");
				double new_qty = parseDouble(args[2]);
				
				if(args[0].equalsIgnoreCase("set")){
					ch.addGas(new AtmosphericGas(gas_t, new_qty-gas_qty));
					notifyCommandListener(sender, this,"command.atmosphere.success.set");
				}else if(args[0].equalsIgnoreCase("add")){
					ch.addGas(new AtmosphericGas(gas_t, new_qty));
					notifyCommandListener(sender, this,"command.atmosphere.success.add");
				}else throw new SyntaxErrorException("command.atmosphere.error.wrongparams");
			}
		}catch(PlayerNotFoundException e){
			throw new PlayerNotFoundException("You must be a player to use that!");
		}
	}
	
	public static String getFullGasName(GasType type){
		return I18n.format("atmgas."+type.getName()+".name");
	}
	
	public static String getGasAsString(IAtmosphericGas gas){
		return getFullGasName(gas.getType())+": "+gas.getAmount();
	}
}
