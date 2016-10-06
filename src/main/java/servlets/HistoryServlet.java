package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import pages.HistoryPage;
import pages.HomePage;
import user.User;


public class HistoryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		User user = User.getCurrentUser();

		if(user == null)
		{
			out.write(new HomePage(req.getRequestURI()).make());
			//out.write("You are not logged in! Login <a href='" + userService.createLoginURL(baseUrl) + "'> here </a>");
			return;
		}
		out.write(new HistoryPage(req.getRequestURI()).make());
		
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		out.write(new HistoryPage(req.getRequestURI()).make());
	}

}
