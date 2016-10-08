package pages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import datastore.IdList;
import java.util.Comparator;
import java.util.Collections;
import java.util.ArrayList;
import java.text.Format;
import java.text.SimpleDateFormat;

import pages.html_builder.Form;
import budgeter.Category;
import budgeter.BudgetTerm;

public class HistoryPage extends Page {
	
	private BudgetTerm currentTerm;

	public HistoryPage(String baseUrl) {
		super(baseUrl);
		htmlBuilder.includeAppHeader = true;
		currentTerm = user.getCurrentBudgetTerm();
	}

	public HistoryPage(String baseUrl, BudgetTerm term) {
		super(baseUrl);
		htmlBuilder.includeAppHeader = true;
		currentTerm = user.getCurrentBudgetTerm();
	}

	public String make() {
	    setTitle();
	    if (user == null) {
	    	addLogout();
	    	return htmlBuilder.build();
	    }
			printBudgetTerms();
			// addHistoryHTML();
	    return htmlBuilder.build();
	}

	private void setTitle() {
		try {
			htmlBuilder.setTitle("Term Activity");
			htmlBuilder.addToBody("<style>th, td { border-bottom: 1px solid #ddd; padding: 10px; } table { text-align: center; vertical-align: bottom; width: 100%}</style>");
		} catch (Exception e) {}
	}

	private void addHistoryHTML() {
		String history = "<div id='history'>";
		history += "<h2>Term Activity</h2>";
		history += "<table>";
		history += "<thead><tr>";
		history += "<th>Location</th>";
		history += "<th>Date</th>";
		history += "<th>Category</th>";
		history += "<th>Amount</th>";
		history += "</tr></thead>";
		history += "<tbody><tr>";
// TODO: Update this <td> tags with actual expenses form BudgetTerm expenses.
		// for()
		history += "<td>Walmart</td><td>08/23/2016</td><td>Groceries</td><td><strong>$ &nbsp;45.30</strong></td>";
		history += "</tr><tr>";
		history += "<td>Amazon</td><td>10/01/2016</td><td>Electronics</td><td><strong>$ &nbsp;78.00</strong></td>";
		history += "</tr>";
		history += "</tbody>";
		history += "</table>";
		history += "</div><br/>";
		history += "<hr/><br/>";
		htmlBuilder.addToBody(history);
	}
	
	private void printBudgetTerms(){
		ArrayList<BudgetTerm> pastTerms = user.getPreviousTerms();
		Collections.sort(pastTerms, Collections.reverseOrder());
		// Collections.sort(pastTerms, new Comparator<BudgetTerm>() {
		// 	@Override
		// 	public int compare(BudgetTerm b1, BudgetTerm b2) {
		// 		 return b1.getStartDate().compareTo(b2.getStartDate());
		// 	}
		// });
		String history = "<div id='history-terms'>";
		history += "<h2>Previous Terms</h2>";
		history += "<table>";
		history += "<thead><tr>";
		history += "<th>Start Date</th>";
		history += "<th>End Date</th>";
		history += "<th>Starting Balance</th>";
		history += "<th>Ending Balance</th>";
		history += "</tr></thead>";
		history += "<tbody>";
		for(BudgetTerm bt: pastTerms){
			if(bt.isEnded()){
				Format formatter = new SimpleDateFormat("EEEE MMMM d, yyyy 'at' hh:mm a");
				String startTime = formatter.format(bt.getStartDate());
				String endTime = formatter.format(bt.getStartDate());
				String startBalance = Double.toString(bt.getStartingBalance());
				String endBalance = Double.toString(bt.getStartingBalance());
				history += "<tr>";
				history += "<td>"+startTime+"</td><td>"+endTime+"</td><td><strong>$&nbsp;"+startBalance+"</strong></td><td><strong>$ &nbsp;"+endBalance+"</strong></td>";
				history += "</tr>";
			}
		}
		history += "</tbody>";
		history += "</table>";
		history += "</div><br/>";
		history += "<hr/><br/>";
		htmlBuilder.addToBody(history);		
	}
}
