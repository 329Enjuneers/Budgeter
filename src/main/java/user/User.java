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
		previousBudgetTermIds = new IdList<BudgetTerm>();
	}

	public User(String email) {
		this.email = email;
		this.nickname = null;
		previousBudgetTermIds = new IdList<BudgetTerm>();
	}
	public User(String email, String nickname) {
		this.email = email;
		this.nickname = nickname;
		previousBudgetTermIds = new IdList<BudgetTerm>();
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
		
	public static Long getCurrentUserId() {
		User user = User.getCurrentUser();
		if (user != null) {
			return user.getId();
		}
		return null;
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
		Long oldBudgetTermId = currentBudgetTermId;
		currentBudgetTermId = null;
		previousBudgetTermIds.add(oldBudgetTermId);
		save();
	}

	public void startNewTerm(BudgetTerm term) {
		if (currentBudgetTermId != null) {
			endTerm();
		}
		currentBudgetTermId = term.getId();
		save();
	}

	public BudgetTerm getCurrentBudgetTerm() {
		System.out.println("curent budget term id is " + currentBudgetTermId);
		if (currentBudgetTermId == null) {
			return null;
		}
		BudgetTerm instance = new BudgetTerm();
		return instance.getById(currentBudgetTermId);
	}

	public BudgetTerm getMostRecentBudgetTerm() {
		return previousBudgetTermIds.getLast(new BudgetTerm());
	}

	@Override
	public Long getId() {
		return id;
	}

}
