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
	
	public boolean hasId(Long id) {
		for (Long existingId : ids) {
			System.out.println("Existing: " + existingId);
			System.out.println("Given: " + id);
			if (existingId.equals(id)) {
				return true;
			}
		}
		return false;
	}
}
