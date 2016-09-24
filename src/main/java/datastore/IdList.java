package datastore;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class IdList<EntityType> {
	private ArrayList<Long> ids;
	
	public IdList() {
		ids = new ArrayList<Long>();
	}
	
	public void add(Long id) {
		if (!idAlreadyExists(id)) {
			ids.add(id);
		}
	}
	
	public void remove(Long id) {
		if (!idAlreadyExists(id)) {
			throw new NoSuchElementException();
		}
		ids.remove(id);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<EntityType> fetch(Class<? extends BasicEntity> clazz) {
		ArrayList<EntityType> myList = new ArrayList<EntityType>();
		QueryFactory factory = new QueryFactory(clazz);
		for (Long id : ids) {
			EntityType entity = (EntityType) factory.getEntityById(id);
			myList.add(entity);
		}
		return myList;
	}
	
	private boolean idAlreadyExists(Long id) {
		for (Long existingId : ids) {
			if (existingId == id) {
				return true;
			}
		}
		return false;
	}
}
