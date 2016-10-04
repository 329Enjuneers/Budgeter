package pages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import budgeter.DeprecatedExpense;
import pages.html_builder.Form;


public class DeprecatedExpensePage extends Page 
{
	private DeprecatedExpense deprecatedExpense;
	
	public DeprecatedExpensePage(String baseUrl) 
	{
		super(baseUrl);
		htmlBuilder.includeAppHeader = true;
		//TODO where does id come from?
		//expense = getExpense(id);
		deprecatedExpense = new DeprecatedExpense("walmart","food" ,50.00); //testing
	}

	public String make() 
	{
	    setTitle();
	    if (user == null) {
	    	addLogout();
	    	return htmlBuilder.build();
	    }
	    addExpenseInfo();
	    addHorizontalRule();
	    return htmlBuilder.build();
	}

	private void setTitle() 
	{
		try {
			htmlBuilder.setTitle("Expenses");
		} catch (Exception e) {}
	}
	/**
	 * store (editable)
	 * amount (editable)
	 * date (editable)
	 */
	private void addExpenseInfo()
	{
		String expenseSummary = "<div id='expense-summary'>";
		expenseSummary += "<table>";
		expenseSummary += "<thead><tr>";
		expenseSummary += "<th colspan=\"2\">Expense Information</th>";
		expenseSummary += "<th></th>";
		expenseSummary += "<tr>";
		expenseSummary += "</tr></thead>";
		expenseSummary += "<tbody><tr>";
		expenseSummary += "<td><strong>Store</strong></td><td>&nbsp;"+deprecatedExpense.store+"</td>";
		expenseSummary += "</tr><tr>";
		expenseSummary += "<td><strong>Name</strong></td><td>&nbsp;"+deprecatedExpense.name+"</td>";
		expenseSummary += "</tr><tr>";
		expenseSummary += "<td><strong>Amount</strong></td><td>&nbsp;"+deprecatedExpense.amount+"</td>";
		expenseSummary += "</tr><tr>";
		expenseSummary += "<td><strong>Date</strong></td><td>&nbsp;"+deprecatedExpense.date+"</td>";
		expenseSummary += "</tr>";
		expenseSummary += "</tbody>";
		expenseSummary += "</table>";
		expenseSummary += "</div><br/>";
		expenseSummary += "<hr/><br/>";
		htmlBuilder.addToBody(expenseSummary);
		
	}

}
