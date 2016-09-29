package datastore;

import static com.googlecode.objectify.ObjectifyService.ofy;

public abstract class BasicEntity {
	
	public BasicEntity() {}
	
	public abstract Long getId();
	
	public void save() {
		ofy().save().entity(this).now();
	}
}