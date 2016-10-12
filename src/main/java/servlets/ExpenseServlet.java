package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import budgeter.BudgetTerm;
import budgeter.Category;
import budgeter.Expense;
import budgeter.PurchasedItem;
import pages.ExpensePage;
import pages.HomePage;
import pages.Page;
import user.User;

public class ExpenseServlet extends BasicServlet {
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
		out.write(new ExpensePage(req.getRequestURI(), new Expense()).make());
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try{
			super.doPost(req, resp);
		}
		catch(IOException e) { return; }
		BudgetTerm term = user.getCurrentBudgetTerm();
		if (term == null) {
			out.write(new HomePage(req.getRequestURI(),true).make());
			return;
		}

		String categoryName = req.getParameter("category");
		Category category = term.getCategory(categoryName);
		if (category == null) {
			out.write("Invalid category name provided. Please visit the <a href='/category'>home page</a> to add a new one!");
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
		expense.timeCreated = makeDate(req.getParameter("timeCreated"));
		expense.save();

		category.addExpense(expense);

		resp.sendRedirect("/expense/existing?expenseId=" + expense.getId());
	}
	
	private Date makeDate(String timeCreated) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date;
		try {
			date = sdf.parse(timeCreated);
		} catch (ParseException e) {
			date = new Date();
		}
		return date;
	}
}
