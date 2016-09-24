package receipt_parser;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import datastore.BasicEntity;

@Entity
public class Receipt extends BasicEntity {
	@Id Long id;

	@Override
	public Long getId() {
		return id;
	}

}
