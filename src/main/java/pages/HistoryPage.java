package pages;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import budgeter.BudgetTerm;

public class HistoryPage extends Page {
	
	private BudgetTerm currentTerm;
	private ArrayList<BudgetTerm> pastTerms;
	private double totalIncome;
	private double totalExpenses;
	private DecimalFormat decimalFormat = new DecimalFormat("#0.00");

	public HistoryPage(String baseUrl) {
		super(baseUrl);
		htmlBuilder.includeAppHeader = true;
		currentTerm = user.getCurrentBudgetTerm();
		pastTerms = user.getPreviousTerms();
		totalIncome = 0.0;
		totalExpenses = 0.0;
		decimalFormat.setGroupingUsed(true);
		decimalFormat.setGroupingSize(3);
	}

	public HistoryPage(String baseUrl, BudgetTerm term) {
		super(baseUrl);
		htmlBuilder.includeAppHeader = true;
		currentTerm = user.getCurrentBudgetTerm();
		pastTerms = user.getPreviousTerms();
		totalIncome = 0.0;
		totalExpenses = 0.0;
		decimalFormat.setGroupingUsed(true);
		decimalFormat.setGroupingSize(3);
	}

	public String make() {
	    setTitle();
	    if (user == null) {
	    	addLogout();
	    	return htmlBuilder.build();
	    }
			printBudgetTerms();
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
		String history = "<div id='history-terms'>";
		history += "<h2>Previous Terms</h2>";
		if(pastTerms.size() > 0){
			Collections.sort(pastTerms, Collections.reverseOrder());
			history += "<table>";
			history += "<thead><tr>";
			history += "<th>Start Date</th>";
			history += "<th>End Date</th>";
			history += "<th>Starting Budget</th>";
			history += "<th>Expenses</th>";
			history += "<th>Ending Balance</th>";
			history += "</tr></thead>";
			history += "<tbody>";
			for(BudgetTerm bt: pastTerms){
				if(bt.isEnded()){
					totalIncome += bt.getStartingBalance();
					totalExpenses += bt.getAmountSpent();
					Format formatter = new SimpleDateFormat("EEE MMMM d, yyyy");
					String startTermTime = "<td>"+formatter.format(bt.getStartDate())+"</td>";
					String endTermTime = "<td>"+formatter.format(bt.getEndDate())+"</td>";
					String startTermBalance = "<td>$ &nbsp;" + decimalFormat.format(bt.getStartingBalance()) + "</td>";
					String expenses = "<td>$ &nbsp;" + decimalFormat.format(bt.getAmountSpent()) + "</td>";
					float eb = bt.getAmountRemaining();
					String endTermBalance = "";
					if(eb < 0){
						endTermBalance = "<td style='color:red'><strong>$ &nbsp;" + decimalFormat.format(eb) + "</strong></td>";
					}else{
						endTermBalance = "<td style='color:green'><strong>$ &nbsp;" + decimalFormat.format(eb) + "</strong></td>";
					}
					history += "<tr>";
					history += startTermTime + endTermTime + startTermBalance + expenses + endTermBalance;
					history += "</tr>";
				}
			}
			String endBalance = "";
			double remainingBalance = totalIncome - totalExpenses;
			if(remainingBalance < 0){
				endBalance = "<td style='color:red'><strong>$ &nbsp;" + decimalFormat.format(remainingBalance) + "</strong></td>";
			}else{
				endBalance = "<td style='color:green'><strong>$ &nbsp;" + decimalFormat.format(remainingBalance) + "</strong></td>";
			}
			history += "<tr>";
			history += "<td colspan='2' style='text-align:right'><strong>Totals</strong></td><td>$ &nbsp;" + decimalFormat.format(totalIncome)+"</td><td>$ &nbsp;" + decimalFormat.format(totalExpenses)+"</td>" + endBalance;
			history += "</tr>";
			history += "</tbody>";
			history += "</table>";
		}else if(currentTerm == null){
			history += "<p style='padding:5px'>You will find summary of your previous terms here.</br>Please visit <a href='/'>Home</a> to get started with your first term!</p>";
		}else{
			history += "<p style='padding:5px'>You will find summary of your previous terms after you successfully complete them. For information on your current term, use tabs above. </p>";
		}
		history += "</div><br/>";
		history += "<hr/><br/>";
		htmlBuilder.addToBody(history);		
	}
}
