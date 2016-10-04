package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import budgeter.BudgetTerm;
import budgeter.Expense;
import budgeter.PurchasedItem;
import pages.ExpensePage;
import user.User;

public class ExpenseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final User user = User.getCurrentUser();
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		out.write(new ExpensePage(req.getRequestURI(), new Expense()).make());
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		BudgetTerm term = user.getCurrentBudgetTerm();
		if (term == null) {
			out.write("You have not started a budget term yet! Please visit the <a href='/'>home page</a> to start a new one!");
			return;
		}
		
		String[] names = req.getParameterValues("name");
		String[] costs = req.getParameterValues("cost");
		String storeName = req.getParameter("storeName");
		ArrayList<PurchasedItem> purchasedItems = new ArrayList<PurchasedItem>();
		if (names.length != costs.length) {
			out.write("The number of names must equal the number of costs!");
			return;
		}
		if (storeName == null) {
			out.write("Store name must be included");
			return;
		}
		
		for (int i = 0; i < names.length; i++) {
			float f = 0;
			try {
				f = Float.parseFloat(costs[i]);
			}
			catch(Exception e) {
				out.write("cost must be float!");
				return;
			}
			purchasedItems.add(new PurchasedItem(names[i], f));
		}
		
		Expense expense = new Expense(purchasedItems);
		expense.isVerified = true;
		expense.storeName = storeName;
		expense.authorId = user.getId();
		String timeCreated = req.getParameter("timeCreated");
		if (timeCreated != null) {
			expense.timeCreated = new Date();
		}
		expense.save();
		
		term.addExpense(expense);
		
		resp.sendRedirect("/receipt/existing?receiptId=" + expense.getId());
	}
}
