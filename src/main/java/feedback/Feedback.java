package feedback;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import datastore.BasicEntity;

@Entity
public class Feedback extends BasicEntity {
	@Id private Long id;
	
	public Feedback() {}
	
	public Feedback(String text) {
		
	}
	
	public Feedback(String text, String color) {
		
	}

	@Override
	public Long getId() {
		return id;
	}
}
