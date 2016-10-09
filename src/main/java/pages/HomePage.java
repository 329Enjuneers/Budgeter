package pages;

import budgeter.BudgetTerm;
import java.text.DecimalFormat;
import pages.html_builder.Form;

public class HomePage extends Page {
	private BudgetTerm term;
	private boolean error = false;
	private DecimalFormat decimalFormat = new DecimalFormat("#0.00");

	public HomePage(String baseUrl)//,BudgetTerm term) 
	{
		super(baseUrl);
		htmlBuilder.includeAppHeader = true;
		decimalFormat.setGroupingUsed(true);
		decimalFormat.setGroupingSize(3);
	}

	public HomePage(String baseUrl, boolean error)
	{
		super(baseUrl);
		this.error = error;
		htmlBuilder.includeAppHeader = true;
	}

	public HomePage(String baseUrl, BudgetTerm term)
	{
		super(baseUrl);
		htmlBuilder.includeAppHeader = true;
		this.term = term;
		decimalFormat.setGroupingUsed(true);
		decimalFormat.setGroupingSize(3);
	}

	public String make() {
	    setTitle();
	    if (user == null) {
	    	addLogout();
	    	return htmlBuilder.build();
	    }
			htmlBuilder.addToBody("<h2>Budgeter</h2>");
			htmlBuilder.addToBody("<style>th, td { border-bottom: 1px solid #ddd; padding: 10px; } table { text-align: left; vertical-align: bottom;} .error { color: #E6E6E6; background-color: #990000; padding: 7px;} </style>");
			if(error) {
				htmlBuilder.addToBody("<div class='error'><strong>Start a new term to begin.</strong></div><br/>");
			}
			if(term == null){
				addNewTermHTML();
			}else{
				addCurrentBalanceHTML();
			}
	    return htmlBuilder.build();
	}

	private void setTitle() {
		try {
			htmlBuilder.setTitle("Home");
		} catch (Exception e) {}
	}
	
	private void addCurrentBalanceHTML(){
		String termSummary = "<div id='term-summary'>";
		termSummary += "<table>";
		termSummary += "<thead><tr>";
		termSummary += "<th colspan=\"2\">Term Summary</th>";
		termSummary += "<th></th>";
		termSummary += "<tr>";
		termSummary += "</tr></thead>";
		termSummary += "<tbody><tr>";
		termSummary += "<td>Total Expenses</td><td><strong>$ &nbsp;" + decimalFormat.format(term.getAmountSpent()) + "</strong></td>";
		termSummary += "</tr><tr>";
		if(term.getAmountRemaining() < 0){
			termSummary += "<td>Total Remaining</td><td style='color:red'><strong>$ &nbsp;" + decimalFormat.format(term.getAmountRemaining()) + "</strong></td>";
		}else{
			termSummary += "<td>Total Remaining</td><td style='color:green'><strong>$ &nbsp;" + decimalFormat.format(term.getAmountRemaining()) + "</strong></td>";
		}
		termSummary += "</tr>";
		termSummary += "</tbody>";
		termSummary += "</table>";
		termSummary += "</div><br/>";
		termSummary += addEndTermHTML();
		htmlBuilder.addToBody(termSummary);
	}
	
	private void addNewTermHTML(){
		Form newGroupForm = new Form();
		newGroupForm.addProperty("action", "/");
		newGroupForm.addProperty("method", "POST");
		newGroupForm.addProperty("style", "margin-bottom:2em");
		String newTerm = "<div id='new-term'>";
		newTerm += "<label><strong>Income: &nbsp;&nbsp;</strong></label>";
		newTerm += "$ &nbsp;<input id=\"income\" name=\"income\" type=\"text\" placeholder=\"Income\" required/><br/>";
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
		newTerm += "<td><button type='submit'><strong>START TERM</strong></button></td>";
		newTerm += "<hr/><br/>";
		newGroupForm.addElement(newTerm);
		htmlBuilder.addToBody(newGroupForm.toString());
	}

	private String addEndTermHTML(){
		Form newGroupForm = new Form();
		newGroupForm.addProperty("action", "/");
		newGroupForm.addProperty("method", "POST");
		newGroupForm.addProperty("style", "margin-bottom:2em");
		String newTerm = "<div id='new-term'>";
		newTerm += "<input type=\"hidden\" id=\"action\" name=\"action\" type=\"text\" value=\"end\"/>";
		newTerm += "</div><br/>";
		newTerm += "<td><button type='submit'><strong>END TERM</strong></button></td>";
		newTerm += "<hr/><br/>";
		newGroupForm.addElement(newTerm);
		return newGroupForm.toString();
	}

}

