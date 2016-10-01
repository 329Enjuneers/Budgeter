package user;

import java.util.HashMap;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import budgeter.BudgetTerm;
import datastore.BasicEntity;
import datastore.IdList;

@Entity
public class User extends BasicEntity {
	@Id Long id;
	@Index public String email;
	public String nickname;
	private Long currentBudgetTermId;
	public IdList<BudgetTerm> previousBudgetTermIds;

	public User() {
		this.email = null;
		this.nickname = null;
	}

	public User(String email) {
		this.email = email;
		this.nickname = null; 
	}
	public User(String email, String nickname) {
		this.email = email;
		this.nickname = nickname;
	}

	public static User getCurrentUser() {
		GoogleUser googleUser = GoogleUser.getCurrentUser();
		if (googleUser == null) {
			return null;
		}

		User user = getOrInsert(googleUser.getEmail());
		if (user.nickname == null) {
			user.nickname = googleUser.getNickname();
			user.save();
		}

		return user;
	}

	private static User getOrInsert(String email) {
		User user = User.get(email);
		if (user == null) {
			user = new User(email);
			user.save();
		}
		return user;
	}

	public static User get(String email) {
		User instance = new User();
		HashMap<String, String> filterMap = new HashMap<String, String>();
		filterMap.put("email", email);
		return instance.getSingleBy(filterMap);
	}
	
	public void endTerm() {
		// TODO end current term
	}
	
	public void startNewTerm(BudgetTerm term) {
		// TODO start new term. If a term currently exists,
		// put it into the history and remove from current.
	}
	
	public BudgetTerm getCurrentBudgetTerm() {
		BudgetTerm instance = new BudgetTerm();
		return instance.getById(currentBudgetTermId);
	}
	
	@Override
	public Long getId() {
		return id;
	}
	
}
