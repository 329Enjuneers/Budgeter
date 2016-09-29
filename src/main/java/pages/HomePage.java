package pages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import pages.html_builder.Form;
import budgeter.BudgetTerm;

public class HomePage extends Page {
	private BudgetTerm term;
	public HomePage(String baseUrl) {
		super(baseUrl);
		htmlBuilder.includeAppHeader = true;
		term = new BudgetTerm(5000.00);
	}

	public String make() {
	    setTitle();
	    if (user == null) {
	    	addLogout();
	    	return htmlBuilder.build();
	    }
			htmlBuilder.addToBody("<h2>Budgeter</h2>");
			htmlBuilder.addToBody("<style>th, td { border-bottom: 1px solid #ddd; padding: 10px; } table { text-align: left; vertical-align: bottom;}</style>");
			addCurrentBalance();
			addNewTermOption();
	    return htmlBuilder.build();
	}

	private void setTitle() {
		try {
			htmlBuilder.setTitle("Home");
		} catch (Exception e) {}
	}
	
	private void addCurrentBalance(){
		String termSummary = "<div id='term-summary'>";
		termSummary += "<table>";
		termSummary += "<thead><tr>";
		termSummary += "<th colspan=\"2\">Term Summary</th>";
		termSummary += "<th></th>";
		termSummary += "<tr>";
		termSummary += "</tr></thead>";
		termSummary += "<tbody><tr>";
		termSummary += "<td>Total Expenses</td><td><strong>$ &nbsp;"+Double.toString(term.getAmountSpent())+"</strong></td>";
		termSummary += "</tr><tr>";
		termSummary += "<td>Total Remaining</td><td><strong>$ &nbsp;"+Double.toString(term.getAmountRemaining())+"</strong></td>";
		termSummary += "</tr>";
		termSummary += "</tbody>";
		termSummary += "</table>";
		termSummary += "</div><br/>";
		termSummary += "<button><strong>END TERM</strong></button>";
		termSummary += "<hr/><br/>";
		htmlBuilder.addToBody(termSummary);
	}
	
	private void addNewTermOption(){
		Form newGroupForm = new Form();
		newGroupForm.addProperty("action", "/group");
		newGroupForm.addProperty("method", "POST");
		newGroupForm.addProperty("style", "margin-bottom:2em");
		String newTerm = "<div id='new-term'>";
		newTerm += "<label><strong>Income: &nbsp;&nbsp;</strong></label>";
		newTerm += "<input id=\"income\" name=\"income\" type=\"text\" placeholder=\"Income\" required/><br/>";
		// newTerm += "<br/><label><strong>Dates: &nbsp;&nbsp;</strong></label>";
		// newTerm += "<table>";
		// newTerm += "<thead><tr>";
		// newTerm += "<th colspan=\"3\">Term Start Date</th>";
		// newTerm += "<th colspan=\"3\">Term End Date</th>";
		// newTerm += "<th></th>";
		// newTerm += "<tr>";
		// newTerm += "</tr></thead>";
		// newTerm += "<tbody><tr>";
		// newTerm += addDateOption();
		// newTerm += addDateOption();
		// newTerm += "</tr>";
		// newTerm += "</tbody>";
		// newTerm += "</table>";
		newTerm += "</div><br/>";
		newTerm += "<td><button type='submit'><strong>CREATE TERM</strong></button></td>";
		newTerm += "<hr/><br/>";
		newGroupForm.addElement(newTerm);
		htmlBuilder.addToBody(newGroupForm.toString());
	}
	
	private String addDateOption(){
		String dateOption = "";
		dateOption += "<td>";
		dateOption += "<select>";
		for(int i=1; i<=12; i++){
			dateOption += "<option value="+i+">"+i+"</option>";
		}
		dateOption += "</select></td>";
		dateOption += "<td><select>";
		for(int i=1; i<=31; i++){
			dateOption += "<option value="+i+">"+i+"</option>";
		}
		dateOption += "</select></td>";
		dateOption += "<td><select>";
		for(int i=2016; i<=2020; i++){
			dateOption += "<option value="+i+">"+i+"</option>";
		}
		dateOption += "</select></td>";
		return dateOption;
	}
}

