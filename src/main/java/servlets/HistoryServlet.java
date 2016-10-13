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


public class HistoryServlet extends BasicServlet {

	private static final long serialVersionUID = 1L;
	private static final User user = User.getCurrentUser();
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try{
			super.doGet(req, resp);
		}
		catch(IOException e) { return; }
		out.write(new HistoryPage(req.getRequestURI()).make());
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try{
			super.doPost(req, resp);
		}
		catch(IOException e) { return; }
		out.write(new HistoryPage(req.getRequestURI()).make());
	}

}
