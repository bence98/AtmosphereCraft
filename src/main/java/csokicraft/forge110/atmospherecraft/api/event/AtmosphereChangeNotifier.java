package csokicraft.forge110.atmospherecraft.api.event;

import java.util.*;

public class AtmosphereChangeNotifier{
	protected List<IAtmosphereChangeListener> l;
	
	public AtmosphereChangeNotifier() {
		l=new LinkedList<>();
	}
	
	public AtmosphereChangeNotifier(IAtmosphereChangeListener lst){
		this();
		register(lst);
	}
	
	public boolean fireEvent(AtmosphereChangeEvent e){
		for(IAtmosphereChangeListener lst:l)
			if(lst.onEvent(e))
				return true;
		return false;
	}
	
	public void register(IAtmosphereChangeListener lst){
		l.add(lst);
	}
	
	public void unregister(IAtmosphereChangeListener lst){
		l.remove(lst);
	}
}
