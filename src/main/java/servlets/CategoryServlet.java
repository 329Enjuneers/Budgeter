package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import budgeter.Category;
import budgeter.BudgetTerm;
import pages.CategoryPage;
import pages.Page;
import user.User;

public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		User user = User.getCurrentUser();
		PrintWriter out = resp.getWriter();
		UserService userService = UserServiceFactory.getUserService();
		if(user == null)
		{
			out.write(new Page(req.getRequestURI()).make());
			//out.write("You are not logged in! Login <a href='" + userService.createLoginURL(baseUrl) + "'> here </a>");
			return;
		}
		BudgetTerm term = user.getCurrentBudgetTerm();
		resp.setContentType("text/html");
		if(term != null){
			out.write(new CategoryPage(req.getRequestURI(),term).make());
		}else{
			
			out.write("You have not started a budget term yet! Please visit the <a href='/'>home page</a> to start a new one!");
			return;
			//out.write(new CategoryPage(req.getRequestURI()).make());
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		User user = User.getCurrentUser();
		BudgetTerm term = user.getCurrentBudgetTerm();
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		if(term != null){
			if(req.getParameterMap().containsKey("newcategory") && req.getParameterMap().containsKey("amount")){
				Category newCategory = new Category(req.getParameter("newcategory"),Float.parseFloat(req.getParameter("amount")));
				term.addCategory(newCategory);
			}
			out.write(new CategoryPage(req.getRequestURI(),term).make());
		}else{
			out.write(new CategoryPage(req.getRequestURI()).make());
		}
	}
}
