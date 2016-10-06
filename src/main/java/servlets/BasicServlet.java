package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import user.User;

public abstract class BasicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static UserService userService = UserServiceFactory.getUserService();
	
	protected PrintWriter out;
	protected User user;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		this.request = req;
		this.response = resp;
		resp.setContentType("text/html");
		user = User.getCurrentUser();
		out = resp.getWriter();
		validateUser();
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		this.request = req;
		this.response = resp;
		resp.setContentType("text/html");
		user = User.getCurrentUser();
		out = resp.getWriter();
		validateUser();
	}
	
	private void validateUser() throws IOException{
		if (user == null) {
			response.sendError(401);
			redirectToLogin();
			throw new IOException();
		}
	}
	
	private void redirectToLogin() {
		out.write("You are not logged in! Login <a href='" + userService.createLoginURL(request.getRequestURI()) + "'> here </a>");
		
	}
}
