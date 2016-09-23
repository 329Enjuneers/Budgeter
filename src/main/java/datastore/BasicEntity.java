package datastore;

import static com.googlecode.objectify.ObjectifyService.ofy;

public abstract class BasicEntity {
	
	public void save() {
		ofy().save().entity(this).now();
	}
}