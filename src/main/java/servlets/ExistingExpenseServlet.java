package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import budgeter.BudgetTerm;
import budgeter.Expense;
import budgeter.PurchasedItem;
import pages.ExpensePage;
import user.User;

public class ExistingExpenseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private User user;
	private BudgetTerm term;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		user = User.getCurrentUser();
		term = user.getCurrentBudgetTerm();
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		Expense expense = getExpense(req.getParameter("expenseId"));
		if (expense == null) {
			out.write("Receipt not found");
			return;
		}
		String wasUpdated = req.getParameter("wasUpdated");
		ExpensePage page = new ExpensePage(req.getRequestURI(), expense);
		if (wasUpdated != null) {
			page.wasUpdated = true;
		}
		out.write(page.make());
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		user = User.getCurrentUser();
		term = user.getCurrentBudgetTerm();
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		Expense expense = getExpense(req.getParameter("expenseId"));
		if (expense == null) {
			out.write("Receipt not found");
			return;
		}
		String action = req.getParameter("action");
		if (action != null && action.equals("delete")) {
			deleteExpense(expense);
			resp.sendRedirect("/expense/current?feedback=Expense%20successfully%20deleted");
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
			out.write("The number of names must equal the number of costs.!");
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
		expense.purchasedItems = purchasedItems;
		expense.storeName = storeName;
		expense.save();
		resp.sendRedirect("/expense/existing?wasUpdated=1&expenseId=" + URLEncoder.encode(Long.toString(expense.getId()), "UTF-8"));
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
