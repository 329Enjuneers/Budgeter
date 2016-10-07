package datastore;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class IdList<EntityType extends BasicEntity> {
	private ArrayList<Long> ids;
	
	public IdList() {
		ids = new ArrayList<Long>();
	}
	
	public void add(Long id) {
		if (!hasId(id)) {
			ids.add(id);
		}
	}
	
	public void remove(Long id) {
		if (!hasId(id)) {
			throw new NoSuchElementException();
		}
		ids.remove(id);
	}
	
	public ArrayList<EntityType> fetch(BasicEntity instance) {
		ArrayList<EntityType> myList = new ArrayList<EntityType>();
		for (Long id : ids) {
			EntityType entity = instance.getById(id);
			myList.add(entity);
		}
		return myList;
	}
	
	public EntityType get(BasicEntity instance, Long id) {
		EntityType entity = instance.getById(id);
		return entity;
	}
	
	public EntityType getLast(BasicEntity instance) {
		int size = ids.size();
		if (size == 0) {
			return null;
		}
		int lastIndex = size - 1;
		Long id = ids.get(lastIndex);
		EntityType entity = instance.getById(id);
		return entity;
	}
	
	public boolean hasId(Long id) {
		for (Long existingId : ids) {

			if (existingId.equals(id)) {
				return true;
			}
		}
		return false;
	}
}
