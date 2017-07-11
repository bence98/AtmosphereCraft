package csokicraft.util.pending;

import java.util.*;

public class OneToManyMapper<K, V> implements Map<K, List<V>>{
	protected Map<K, List<V>> map;
	
	public OneToManyMapper(){
		map=new HashMap<>();
	}
	
	public void add(K key, V val){
		get(key).add(val);
	}
	
	@Override
	public List<V> get(Object key){
		List<V> l = map.get(key);
		if(l==null)
			put((K) key, l=new ArrayList<V>());
		return l;
	}

	@Override
	public void clear(){
		map.clear();
	}

	@Override
	public boolean containsKey(Object key){
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value){
		return map.containsValue(value);
	}

	@Override
	public Set<java.util.Map.Entry<K, List<V>>> entrySet(){
		return map.entrySet();
	}

	@Override
	public boolean isEmpty(){
		return map.isEmpty();
	}

	@Override
	public Set<K> keySet(){
		return map.keySet();
	}

	@Override
	public List<V> put(K key, List<V> value){
		return map.put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends List<V>> m){
		map.putAll(m);
	}

	@Override
	public List<V> remove(Object key){
		return map.remove(key);
	}

	@Override
	public int size(){
		return map.size();
	}

	@Override
	public Collection<List<V>> values(){
		return map.values();
	}
}

