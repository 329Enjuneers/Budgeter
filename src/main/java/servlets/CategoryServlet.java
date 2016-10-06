package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import budgeter.Category;
import budgeter.BudgetTerm;
import pages.CategoryPage;
import user.User;

public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		User user = User.getCurrentUser();
		BudgetTerm term = user.getCurrentBudgetTerm();
		PrintWriter out = resp.getWriter();
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
				if (strAmount != null) {
					float amountAllocated = Float.parseFloat(strAmount);
					category.amountAllocated = amountAllocated;
					category.save();
				}
				resp.sendRedirect("/category");
			}
		}
		else {
			if (req.getParameterMap().containsKey("newcategory") && req.getParameterMap().containsKey("amount")) {
				Category newCategory = new Category(req.getParameter("newcategory"),Float.parseFloat(req.getParameter("amount")));
				term.addCategory(newCategory);
			}
			else {
				out.write("Invalid category id!");
			}
		}
		
	}
}
