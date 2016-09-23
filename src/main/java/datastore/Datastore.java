package datastore;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class Datastore {
	
	public static <T> Object getEntityById(Class<T> clazz, Long id) {
		return ofy().load().type(clazz).id(id).now();
	}
}
