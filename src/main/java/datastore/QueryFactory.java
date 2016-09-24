package datastore;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.googlecode.objectify.cmd.LoadType;

public class QueryFactory {
	private LoadType<? extends BasicEntity> query;
	
	public QueryFactory(Class<? extends BasicEntity> clazz) {
		query = ofy().load().type(clazz);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends BasicEntity> T getEntityById(Long id) {
		return (T) query.id(id).now();
	}
	
	public void addFilter(String filterName, String filterValue) {
		query.filter(filterName, filterValue);
	}
	
	
}
