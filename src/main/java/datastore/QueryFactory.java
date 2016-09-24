package datastore;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.googlecode.objectify.cmd.LoadType;
import com.googlecode.objectify.cmd.Query;

public class QueryFactory {
	private LoadType<? extends BasicEntity> loader;
	private Query<? extends BasicEntity> query;
	
	public QueryFactory(Class<? extends BasicEntity> clazz) {
		loader = ofy().load().type(clazz);
		query = null;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends BasicEntity> T getEntityById(Long id) {
		return (T) loader.id(id).now();
	}
	
	public void addFilter(String filterName, String filterValue) {
		query = loader.filter(filterName, filterValue);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends BasicEntity> T get() throws IllegalStateException {
		if (query == null) {
			throw new IllegalStateException("No filters have been added");
		}
		return (T) query.first().now();
	}
	
	
}
