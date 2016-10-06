package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import budgeter.BudgetTerm;
import budgeter.Category;
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
//			out.write("You are not logged in! Login <a href='" + userService.createLoginURL(baseUrl) + "'> here </a>");
			return;
		}
		BudgetTerm term = user.getCurrentBudgetTerm();
		resp.setContentType("text/html");
		if(term == null){
			out.write("You have not started a budget term yet! Please visit the <a href='/'>home page</a> to start a new one!");
			return;
		}
		CategoryPage page = new CategoryPage(req.getRequestURI(),term);
		out.write(page.make());
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		User user = User.getCurrentUser();
		BudgetTerm term = user.getCurrentBudgetTerm();
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		if(term == null){
			out.write("You have not started a budget term yet! Please visit the <a href='/'>home page</a> to start a new one!");
			return;
		}
		String categoryId = req.getParameter("categoryId");
		if (categoryId != null) {
			Long id = Long.parseLong(categoryId);
			Category category = term.getCategory(id);
			if (category == null) {
				out.write("Invalid category id!");
				return;
			}
			
			String action = req.getParameter("action");
			if (action.equals("Update")) {
				String strAmount = req.getParameter("amountAllocated");
				String newName = req.getParameter("name");
				if (strAmount != null) {
					float amountAllocated = Float.parseFloat(strAmount);
					category.amountAllocated = amountAllocated;
					category.save();
				}
				if (newName != null) {
					category.name = newName;
					category.save();
				}
			}
			else if (action.equals("Delete")) {
				term.deleteCategory(category);
			}
			resp.sendRedirect("/category");
		}
		else {
			if (req.getParameterMap().containsKey("newcategory") && req.getParameterMap().containsKey("amount")) {
				Category newCategory = new Category(req.getParameter("newcategory"),Float.parseFloat(req.getParameter("amount")));
				try {
					term.addCategory(newCategory);
				}
				catch(IllegalStateException e) {
					out.write("A category with that name already exists!");
					return;
				}
				resp.sendRedirect("/category");
			}
			else {
				out.write("Invalid category id!");
			}
		}
		
	}
}
