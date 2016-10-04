package pages;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import budgeter.Expense;
import pages.html_builder.Div;
import pages.html_builder.Feedback;
import pages.html_builder.Form;

public class CurrentExpensesPage extends Page{
	private HashMap<String, ArrayList<Expense>> expenseMap;
	private String feedback;
	
	public CurrentExpensesPage(String baseUrl, HashMap<String, ArrayList<Expense>> expenseMap) {
		super(baseUrl);
		this.expenseMap = expenseMap;
		htmlBuilder.includeAppHeader = true;
		feedback = null;
	}
	
	public String make() {
	    setTitle();
	    if (user == null) {
	    	addLogout();
	    	return htmlBuilder.build();
	    }
	    addFeedbackHtml();
	    addHeader();
	    addCategories();
	    addHorizontalRule();
	    return htmlBuilder.build();
	}
	
	public void addFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	private void addFeedbackHtml() {
		if(feedback != null) {
			Feedback fb = new Feedback(feedback);
			htmlBuilder.addToBody(fb.toString());
		}
	}
	
	private void setTitle() {
		try {
			htmlBuilder.setTitle("Expenses");
		} catch (Exception e) {}
	}
	
	private void addHeader() {
		htmlBuilder.addToBody("<h2 style='margin-bottom:2em'>Expenses</h2>");
	}
	
	private void addCategories() {
		for(Entry<String, ArrayList<Expense>> categoryMap : expenseMap.entrySet()) {
			String groupName = categoryMap.getKey();
			ArrayList<Expense> expenses = categoryMap.getValue();
			Div div = new Div();
			div.addElement("<h3><u>" + groupName + "</u></h3>");
			div.addElement(getExpenses(expenses));
			htmlBuilder.addToBody(div.toString());
		}
	}
	
	private String getExpenses(ArrayList<Expense> expenses) {
		Div div = new Div();
		div.addElement("<ul>");
		if (expenses.size() > 0) {
			for (Expense expense : expenses) {
				div.addElement(getExpenseHtml(expense));
			}
		}
		else {
			div.addElement("<span>--</span>");
		}
		div.addElement("</ul>");
		return div.toString();
	}
	
	private String getExpenseHtml(Expense expense) {
		StringBuilder str = new StringBuilder();
		str.append("<li>");
		str.append(getExpenseLink(expense));
		str.append(" ");
		str.append(getTotal(expense));
		str.append(getRemoveButton(expense));
		str.append("</li>");
		return str.toString();
	}
	
	private String getExpenseLink(Expense expense) {
		Format formatter = new SimpleDateFormat("EEEE MMMM d, yyyy 'at' hh:mm a");
    	String time = expense.timeCreated != null ? formatter.format(expense.timeCreated) : "Not set";
    	return "<a href='/expense/existing?expenseId=" + expense.getId() + "'>" + time +"</a>";
	}
	
	private String getTotal(Expense expense) {
		return "<span>($" + expense.getTotal() + ")</span>";
	}
	
	private String getRemoveButton(Expense expense) {
		String button = "<button type='submit' style='margin-left:1.5em; cursor:pointer;'>X</button>";
		Form form = new Form();
		form.addProperty("action", "/expense/existing?expenseId=" + expense.getId() + "&action=delete");
		form.addProperty("method", "POST");
		form.addProperty("style", "display:-webkit-inline-box");
		form.addElement(button);
		return form.toString();
	}
}
