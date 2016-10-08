package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import budgeter.BudgetTerm;
import budgeter.Expense;
import budgeter.PurchasedItem;
import feedback.Feedback;
import pages.ExpensePage;
import pages.Page;
import user.User;

public class ExistingExpenseServlet extends BasicServlet {
	private static final long serialVersionUID = 1L;
	private User user;
	private BudgetTerm term;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try{
			super.doGet(req, resp);
		}
		catch(IOException e) { return; }

		UserService userService = UserServiceFactory.getUserService();
		BudgetTerm term = user.getCurrentBudgetTerm();
		if (term == null) {
			out.write("You have not started a budget term yet! Please visit the <a href='/'>home page</a> to start a new one!");
			return;
		}
		Expense expense = getExpense(req.getParameter("expenseId"));
		if (expense == null) {
			out.write("Receipt not found");
			return;
		}
		ExpensePage page = new ExpensePage(req.getRequestURI(), expense);
		out.write(page.make());
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try{
			super.doPost(req, resp);
		}
		catch(IOException e) { return; }
		Expense expense = getExpense(req.getParameter("expenseId"));
		if (expense == null) {
			out.write("Receipt not found");
			return;
		}
		String action = req.getParameter("action");
		if (action != null && action.equals("delete")) {
			deleteExpense(expense);
			new Feedback("Expense successfully deleted", "green");
			resp.sendRedirect("/expense/current");
			return;
		}

		if (!expense.isVerified) {
			expense.isVerified = true;
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
			out.write("Store name must be included!");
			return;
		}

		for (int i = 0; i < names.length; i++) {
			float f = 0;
			try {
				f = Float.parseFloat(costs[i]);
			}
			catch(Exception e) {
				out.write("Cost must be float!");
				return;
			}
			purchasedItems.add(new PurchasedItem(names[i], f));
		}
		expense.purchasedItems = purchasedItems;
		expense.storeName = storeName;
		expense.save();
		new Feedback("Expense successfully updated", "green");
		resp.sendRedirect("/expense/existing?&expenseId=" + URLEncoder.encode(Long.toString(expense.getId()), "UTF-8"));
	}

	private Expense getExpense(String rawExpenseId) {
		if (rawExpenseId == null) {
			return null;
		}

		Long expenseId;
		try {
			expenseId = Long.parseLong(rawExpenseId);
		}
		catch(ClassCastException e) {
			return null;
		}

		Expense instance = new Expense();
		Expense expense = instance.getById(expenseId);
		if (expense == null || !expense.authorId.equals(user.getId())) {
			return null;
		}
		return expense;
	}

	private void deleteExpense(Expense expense) {
//		TODO
		term.removeExpense(expense);
	}
}
