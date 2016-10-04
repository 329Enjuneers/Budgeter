package pages;

import java.util.ArrayList;

import budgeter.BudgetGroup;
import budgeter.BudgetTerm;
import pages.html_builder.Form;

public class BudgetGroupPage extends Page {
	private BudgetTerm term;
	public BudgetGroupPage(String baseUrl) {
		super(baseUrl);
		htmlBuilder.includeAppHeader = true;
	}
	public BudgetGroupPage(String baseUrl, BudgetTerm term) {
		super(baseUrl);
		htmlBuilder.includeAppHeader = true;
		this.term = term;
	}

	public String make() {
	    setTitle();
	    if (user == null) {
	    	addLogout();
	    	return htmlBuilder.build();
	    }
			htmlBuilder.addToBody("<h2>Categories</h2>");
			htmlBuilder.addToBody("<style>th, td { border-bottom: 1px solid #ddd; padding: 10px; } table { text-align: left; vertical-align: bottom;} summary { width: 50%}</style>");
			addNewGroupForm();
			printExistingGroup();
	    addHorizontalRule();
	    return htmlBuilder.build();
	}

	private void setTitle() {
		try {
			htmlBuilder.setTitle("Categories");
		} catch (Exception e) {}
	}
	
	private void printExistingGroup(){
		if(term == null){return;}
		ArrayList<BudgetGroup> groups = term.getGroups();
		String groupSummary = "";
		groupSummary += "<table class='summary'>";
		groupSummary += "<thead><tr>";
		groupSummary += "<th>Category</th>";
		groupSummary += "<th>Spent</th>";
		groupSummary += "<th>Available</th>";
		groupSummary += "</tr></thead>";
		groupSummary += "<tbody>";
		if(groups != null && groups.size() > 0){
			for (BudgetGroup bg : groups) {
				groupSummary += "<tr>";
				groupSummary += "<td>"+bg.name+"</td><td>"+Double.toString(bg.getAmountSpent())+"</td><td>"+Double.toString(bg.getAmountRemaining())+"</td>";
				groupSummary += "</tr>";
			}
		}
		groupSummary += "</tbody>";
		groupSummary += "</table>";
		htmlBuilder.addToBody(groupSummary);
	}

	private void addNewGroupForm() {
		String groupFormTable = "<div id='group-table'>";
		groupFormTable += "<table>";
		groupFormTable += "<thead><tr>";
		groupFormTable += "<th colspan=\"2\">New Category</th>";
		groupFormTable += "<tr>";
		groupFormTable += "</tr></thead>";
		groupFormTable += "<tbody><tr>";
		groupFormTable += "<td>Name:</td><td>&nbsp;&nbsp;<input id='newgroup' name='newgroup' placeholder='New Category' required/></td>";
		groupFormTable += "</tr><tr>";
		groupFormTable += "<td>Amount:</td><td>$ &nbsp;<input id='amount' name='amount' placeholder='Amount to be allocated' required/></td>";
		groupFormTable += "</tr>";
		groupFormTable += "</tbody>";
		groupFormTable += "</table>";
		groupFormTable += "</div><br/>";
		Form newGroupForm = new Form();
	    newGroupForm.addProperty("action", "/group");
	    newGroupForm.addProperty("method", "POST");
	    newGroupForm.addProperty("style", "margin-bottom:2em");
	    newGroupForm.addElement(groupFormTable);
	    newGroupForm.addElement("<button type='submit'>Add</button></div>");
	    htmlBuilder.addToBody(newGroupForm.toString());
	}
}
