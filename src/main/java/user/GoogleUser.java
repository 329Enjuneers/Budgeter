package user;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class GoogleUser {
	
	private String email;
	private String nickname;
	
	public GoogleUser(User user) {
		this.email = user.getEmail();
		this.nickname = user.getNickname();
	}
	
	public static GoogleUser getCurrentUser() {
		UserService userService = UserServiceFactory.getUserService();
		if(userService.getCurrentUser() == null){
			return null;
		}
		return new GoogleUser(userService.getCurrentUser());
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getNickname() {
		return nickname;
	}
}
