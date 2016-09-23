package user;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import budgeter.BudgetTerm;
import datastore.BasicEntity;

@Entity
public class User extends BasicEntity {
	@Id private Long id;
	@Index public String email;
	public String nickname;
	private Long currentBudgetTermKey;
	private ArrayList<Long> previousBudgetTermKeys;

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
		com.google.appengine.api.users.User googleUser = getCurrentGoogleUser();
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
		User user = ofy().load().type(User.class).filter("email", email).first().now();
		if (user == null) {
			user = new User(email);
			user.save();
		}
		return user;
	}

	public static User get(String email) {
		return ofy().load().type(User.class).filter("email", email).first().now();
	}

	private static com.google.appengine.api.users.User getCurrentGoogleUser() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}
	
	public void endTerm() {
		// TODO end current term
	}
	
	public void startNewTerm(BudgetTerm term) {
		// TODO start new term. If a term currently exists,
		// put it into the history and remove from current.
	}
	
	public BudgetTerm getCurrentBudgetTerm() {
		// TODO get current budget term
		return new BudgetTerm();
	}
	public ArrayList<BudgetTerm> getPreviousBudgetTerms() {
		// TODO get previous budget terms
		return new ArrayList<BudgetTerm>();
	}
	
}
