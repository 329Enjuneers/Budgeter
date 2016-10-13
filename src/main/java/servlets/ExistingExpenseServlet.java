package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import budgeter.BudgetTerm;
import budgeter.Category;
import budgeter.Expense;
import budgeter.PurchasedItem;
import feedback.Feedback;
import pages.ExpensePage;
import pages.HomePage;

public class ExistingExpenseServlet extends BasicServlet {
	private static final long serialVersionUID = 1L;
	private BudgetTerm term;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try{
			super.doGet(req, resp);
		}
		catch(IOException e) { return; }

		term = user.getCurrentBudgetTerm();
		if (term == null) {
			out.write(new HomePage(req.getRequestURI(),true).make());
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
			System.out.println("Deleting expense");
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
		String categoryName = req.getParameter("category");
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
		moveExpense(expense, categoryName);
		new Feedback("Expense successfully updated", "green");
		resp.sendRedirect("/expense/existing?&expenseId=" + Long.toString(expense.getId()));
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
		term.removeExpense(expense);
	}
	
	private void moveExpense(Expense expense, String categoryName) {
		if (categoryName == null) {
			return;
		}
		Category categoryDestination = term.getCategory(categoryName);
		if (categoryDestination == null) {
			return;
		}
		Category currentCategory = null;
		ArrayList<Category> allCategories = term.getCategories();
		for (Category c : allCategories) {
			if (c.hasExpense(expense)) {
				currentCategory = c;
				break;
			}
		}
		categoryDestination.addExpense(expense);
		if (currentCategory != null && !currentCategory.equals(categoryDestination)) {
			currentCategory.removeExpense(expense);
		}
	}
}
