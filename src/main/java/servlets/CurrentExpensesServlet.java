package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import budgeter.BudgetTerm;
import budgeter.Expense;
import pages.CurrentExpensesPage;
import pages.Page;
import pages.HomePage;
import user.User;

public class CurrentExpensesServlet extends BasicServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try{
			super.doGet(req, resp);
		}
		catch(IOException e) { return; }
		BudgetTerm term = user.getCurrentBudgetTerm();
		if (term == null) {
			out.write(new HomePage(req.getRequestURI(),true).make());
			return;
		}
		String feedback = req.getParameter("feedback");
		HashMap<String, ArrayList<Expense>> expenses = term.getExpenses();
		CurrentExpensesPage page = new CurrentExpensesPage(req.getRequestURI(), expenses);
		page.addFeedback(feedback);
		out.write(page.make());
	}

}
