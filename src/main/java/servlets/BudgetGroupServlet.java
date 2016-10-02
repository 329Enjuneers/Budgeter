package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import budgeter.BudgetTerm;
import budgeter.BudgetGroup;
import pages.BudgetGroupPage;
import user.User;

public class BudgetGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private User user = User.getCurrentUser();
	private BudgetTerm term = user.getCurrentBudgetTerm();
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		if(term != null){
			out.write(new BudgetGroupPage(req.getRequestURI(),term).make());
		}else{
			out.write(new BudgetGroupPage(req.getRequestURI()).make());
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		if(term != null){
			if(req.getParameterMap().containsKey("newgroup") && req.getParameterMap().containsKey("amount")){
				BudgetGroup newGroup = new BudgetGroup(req.getParameter("newgroup"),Double.parseDouble(req.getParameter("amount")));
				term.addGroup(newGroup);
			}
			out.write(new BudgetGroupPage(req.getRequestURI(),term).make());
		}else{
			out.write(new BudgetGroupPage(req.getRequestURI()).make());
		}
	}
}
