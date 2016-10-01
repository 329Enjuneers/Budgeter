package datastore;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.googlecode.objectify.cmd.LoadType;
import com.googlecode.objectify.cmd.Query;

public abstract class BasicEntity {
	
	public BasicEntity() {}
	
	public abstract Long getId();
	
	@SuppressWarnings("unchecked")
	public <T extends BasicEntity> T getById(Long id) {
		return (T) ofy().load().type(this.getClass()).id(id).now();
	}
	
	/**
	 * @param map
	 * 		hashmap of filter names and values to filter entities by
	 * 		Ex:
	 * 			"email" : "email@iastate.edu"
	 * 			"name"  : "John Doe"
	 * 		This would filter entities of this type by the email
	 * 		and name specified
	 */
	@SuppressWarnings("unchecked")
	public <T extends BasicEntity> T getSingleBy(HashMap<String, ?> map) {
		LoadType<? extends BasicEntity> loader = ofy().load().type(this.getClass());
		Query<? extends BasicEntity> query = null;
		for (Entry<String, ?> entry : map.entrySet()) {
			String filterName = entry.getKey();
			Object filterValue = entry.getValue();
			if (query == null) {
				query = loader.filter(filterName, filterValue);
			}
			else {
				query = query.filter(filterName, filterValue);
			}
		}
		return (T) query.first().now();
	}
	
	/**
	 * @param map
	 * 		hashmap of filter names and values to filter entities by
	 * 		Ex:
	 * 			"email" : "email@iastate.edu"
	 * 			"name"  : "John Doe"
	 * 		This would filter entities of this type by the email
	 * 		and name specified
	 */
	@SuppressWarnings("unchecked")
	public <T extends BasicEntity> List<T> getManyBy(HashMap<String, ?> map) {
		LoadType<? extends BasicEntity> loader = ofy().load().type(this.getClass());
		Query<? extends BasicEntity> query = null;
		for (Entry<String, ?> entry : map.entrySet()) {
			String filterName = entry.getKey();
			Object filterValue = entry.getValue();
			if (query == null) {
				query = loader.filter(filterName, filterValue);
			}
			else {
				query = query.filter(filterName, filterValue);
			}
		}
		return (List<T>) query.list();
	}
	
	public void save() {
		ofy().save().entity(this).now();
	}
}