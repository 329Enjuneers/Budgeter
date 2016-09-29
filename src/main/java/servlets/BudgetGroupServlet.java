package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pages.BudgetGroupPage;
import budgeter.BudgetTerm;

public class BudgetGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		out.write(new BudgetGroupPage(req.getRequestURI(), new BudgetTerm(Float.parseFloat(req.getParameter("income")))).make());
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		// out.write(new BudgetGroupPage(req.getRequestURI()).make());
		out.write(new BudgetGroupPage(req.getRequestURI(), new BudgetTerm(Float.parseFloat(req.getParameter("income")))).make());
	}
}
