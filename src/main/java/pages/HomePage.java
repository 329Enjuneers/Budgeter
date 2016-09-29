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
		String termSummary =  "<div id='term-summary'>";
		termSummary += "<table>";
		termSummary += "<thead><tr>";
		termSummary += "<th colspan=\"2\">Term Summary</th>";
		termSummary += "<th></th>";
		termSummary += "<tr>";
		termSummary += "</tr></thead>";
		termSummary += "<tbody><tr>";
		termSummary += "<td>Total Expenses</td><td><strong>$ &nbsp;"+Double.toString(term.getAmountSpent())+"</strong></td>";
		termSummary += "<td rowspan=\"2\"><button><strong>END TERM</strong></button></td>";
		termSummary += "</tr><tr>";
		termSummary += "<td>Total Remaining</td><td><strong>$ &nbsp;"+Double.toString(term.getAmountRemaining())+"</strong></td>";
		termSummary += "</tr>";
		termSummary += "</tbody>";
		termSummary += "</table>";
		termSummary += "</div><br/><hr/><br/>";
		htmlBuilder.addToBody(termSummary);
	}
	
	private void addNewTermOption(){
		String newTerm = "<div id='new-term'>";
		newTerm += "<table>";
		newTerm += "<thead><tr>";
		newTerm += "<th colspan=\"3\">Term Start Date</th>";
		newTerm += "<th colspan=\"3\">Term End Date</th>";
		newTerm += "<th></th>";
		newTerm += "<tr>";
		newTerm += "</tr></thead>";
		newTerm += "<tbody><tr>";
		newTerm += addDateOption();
		newTerm += addDateOption();
		newTerm += "<td><button><strong>CREATE TERM</strong></button></td>";
		newTerm += "</tr>";
		newTerm += "</tbody>";
		newTerm += "</table>";
		newTerm += "</div><br/><hr/><br/>";
		htmlBuilder.addToBody(newTerm);
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

	private void addNewGroupForm() {
		Form newGroupForm = new Form();
	    newGroupForm.addProperty("action", "/");
	    newGroupForm.addProperty("method", "POST");
	    newGroupForm.addProperty("style", "margin-bottom:2em");
	    newGroupForm.addElement("<div style='margin-bottom: 1em'><label><b>New Group</b></label></div>");
	    newGroupForm.addElement("<input name='group-name' placeholder='Group Name' required>");
	    newGroupForm.addElement("<button type='submit'>Add Group</button>");
	    htmlBuilder.addToBody(newGroupForm.toString());
	}

}
