package pages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import pages.html_builder.Form;
import budgeter.BudgetTerm;
import budgeter.BudgetGroup;

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
	    addNewGroupForm();
			printExistingGroup();
	    addHorizontalRule();
	    return htmlBuilder.build();
	}

	private void setTitle() {
		try {
			htmlBuilder.setTitle("Budget Groups");
		} catch (Exception e) {}
	}
	
	private void printExistingGroup(){
		htmlBuilder.addToBody("<ul>");
		for (BudgetGroup bg : term.getGroups()) {
			htmlBuilder.addToBody(bg.name);
		}
		htmlBuilder.addToBody("</ul>");
	}

	private void addNewGroupForm() {
		Form newGroupForm = new Form();
	    newGroupForm.addProperty("action", "/");
	    newGroupForm.addProperty("method", "POST");
	    newGroupForm.addProperty("style", "margin-bottom:2em");
	    newGroupForm.addElement("<div style='margin-bottom: 1em'><label><strong>New Budget Group</strong></label></div>");
	    newGroupForm.addElement("<input name='group-name' placeholder='Group Name' required>");
	    newGroupForm.addElement("<button type='submit'>Add Group</button>");
	    htmlBuilder.addToBody(newGroupForm.toString());
	}

}
