package receipt_parser;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import datastore.BasicEntity;

@Entity
public class Receipt extends BasicEntity {
	@Index private Long id;

	@Override
	public Long getId() {
		return id;
	}

}
