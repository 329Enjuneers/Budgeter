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
		if(term != null){
			out.write(new CategoryPage(req.getRequestURI(),term).make());
		}else{
			out.write(new CategoryPage(req.getRequestURI()).make());
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
				term.addGroup(newCategory);
			}
			out.write(new CategoryPage(req.getRequestURI(),term).make());
		}else{
			out.write(new CategoryPage(req.getRequestURI()).make());
		}
	}
}
