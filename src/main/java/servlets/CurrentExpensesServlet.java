package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import budgeter.BudgetTerm;
import budgeter.Expense;
import budgeter.PurchasedItem;
import pages.CurrentExpensesPage;
import user.User;

public class CurrentExpensesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static final User user = User.getCurrentUser();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		User user = User.getCurrentUser();
		BudgetTerm term = user.getCurrentBudgetTerm();
		if (term == null) {
			out.write("You have not started a budget term yet! Please visit the <a href='/'>home page</a> to start a new one!");
			return;
		}
		String feedback = req.getParameter("feedback");
		HashMap<String, ArrayList<Expense>> expenses = term.getExpenses();
		CurrentExpensesPage page = new CurrentExpensesPage(req.getRequestURI(), expenses);
		page.addFeedback(feedback);
		out.write(page.make());
	}

}
