package feedback;

import java.util.HashMap;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import datastore.BasicEntity;
import user.User;

@Entity
public class Feedback extends BasicEntity {
	@Id private Long id;
	@Index private Long userId;
	public String text;
	public String color;
	
	public Feedback() {}
	
	public Feedback(String text) {
		this.text = text;
		this.color = null;
		userId = User.getCurrentUserId();
		save();
	}
	
	public Feedback(String text, String color) {
		this.text = text;
		this.color = color;
		System.out.println("constructor color: " + color);
		userId = User.getCurrentUserId();
		save();
	}
	
	public static Feedback getRelevant() {
		User user = User.getCurrentUser();
		if (user == null) {
			return null;
		}
		HashMap<String, Long> query = new HashMap<String, Long>();
		query.put("userId", user.getId());
		Feedback f = new Feedback().getSingleBy(query);
		if (f != null) {
			f.delete();
		}
		return f;
	}

	@Override
	public Long getId() {
		return id;
	}
}
