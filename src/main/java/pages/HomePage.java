package pages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import pages.html_builder.Form;

public class HomePage extends Page {

	public HomePage(String baseUrl) {
		super(baseUrl);
		htmlBuilder.includeAppHeader = true;
	}

	public String make() {
	    setTitle();
	    if (user == null) {
	    	addLogout();
	    	return htmlBuilder.build();
	    }
			htmlBuilder.addToBody("<h2>Budgeter</h2>");
			addTermSummary();
			addHorizontalRule();
	    return htmlBuilder.build();
	}

	private void setTitle() {
		try {
			htmlBuilder.setTitle("Home");
		} catch (Exception e) {}
	}
	
	private void addTermSummary(){
		String termSummary = "<div id='term-summary'>";
		termSummary += "<table>";
		termSummary += "<thead><tr>";
		termSummary += "<th colspan=\"3\">Term Start Date</th>";
		termSummary += "<th colspan=\"3\">Term End Date</th>";
		termSummary += "</tr></thead>";
		termSummary += "<tbody>";
		termSummary += "<tr>";
		termSummary += addDateOption();
		termSummary += addDateOption();
		termSummary += "</tr>";
		termSummary += "</tbody>";
		termSummary += "</table>";
		termSummary += "</div>";
		termSummary += "<button>Create Term</button>";
		htmlBuilder.addToBody(termSummary);
	}
	
	private String addDateOption(){
		String dateOption = "";
		dateOption += "<td>";
		dateOption += "<select>";
		for(int i=1; i<=31; i++){
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
