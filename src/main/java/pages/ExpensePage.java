package pages;

import java.net.URLEncoder;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import budgeter.BudgetTerm;
import budgeter.Category;
import budgeter.Expense;
import budgeter.PurchasedItem;
import pages.html_builder.Div;
import pages.html_builder.Form;
import user.User;

public class ExpensePage extends Page{
	
	private Expense expense;
	
	public ExpensePage(String baseUrl, Expense expense) {
		super(baseUrl);
		this.expense = expense;
		htmlBuilder.includeAppHeader = true;
	}

	public String make() {
	    setTitle();
	    if (user == null) {
	    	addLogout();
	    	return htmlBuilder.build();
	    }
	    addHeader();
	    addReceiptForm();
	    addHorizontalRule();
	    addDeleteItemScript();
	    addNewItemScript();
	    return htmlBuilder.build();
	}

	private void setTitle() {
		try {
			htmlBuilder.setTitle("Existing Receipt");
		} catch (Exception e) {}
	}
	
	private void addHeader() {
		if (expense.willBeManuallyPopulated()) {
			htmlBuilder.addToBody("<h2 style='margin-bottom:2em'>Add Expenses</h2>");
		}
		else if (expense.isVerified) {
			htmlBuilder.addToBody("<h2 style='margin-bottom:2em'>Edit Expenses</h2>");
		}
		else {
			htmlBuilder.addToBody("<h2 style='margin-bottom:2em'>Verify Expenses</h2>");
		}
	}

	private void addReceiptForm() {
		Form receiptForm = new Form();
	    try {
			receiptForm.addProperty("action", "/expense/existing?expenseId=" + URLEncoder.encode(Long.toString(expense.getId()), "UTF-8"));
		} catch (Exception e) {
			receiptForm.addProperty("action", "/expense");
		}
	    receiptForm.addProperty("method", "POST");
	    receiptForm.addProperty("style", "margin-bottom:2em");
	    receiptForm.addProperty("id", "expense-form");
	    receiptForm.addElement("<hr>");
	    receiptForm.addElement(makeCategoryChoiceDiv());
	    receiptForm.addElement(makeStoreNameDiv());
	    receiptForm.addElement(makeTimeCreatedDiv());
	    receiptForm.addElement(makeLabelDiv());
	    receiptForm.addElement(makePurchasedItemsDiv());
	    receiptForm.addElement(makeNewRowButton());
	    receiptForm.addElement(makeSubmitButton());
	    htmlBuilder.addToBody(receiptForm.toString());
	}
	
	private String makeCategoryChoiceDiv() {
		User user = User.getCurrentUser();
		BudgetTerm term = user.getCurrentBudgetTerm();
		ArrayList<Category> categories = new ArrayList<Category>();
		if (term != null) {
			categories = term.getCategories();
		}
		Div div = new Div();
    	div.addProperty("style", "margin-bottom:1.5em; margin-top:3.5em;");
    	div.addElement("<label><b><u>Category:</u></b></label>");
    	div.addElement("<select style='margin-left: 15.1%' form='expense-form' name='category'>");
    	for (Category category : categories) {
    		String selected = "";
    		if (category.hasExpense(expense)) {
    			selected = "selected";
    		}
    		div.addElement("<option value='" + category.name + "' " + selected +">" + category.name + "</option>");
    	}
    	div.addElement("</select>");
    	div.addElement("<hr style='width:29.4%; margin-right:70%; margin-top: 1.5em;'>");
    	return div.toString();
	}
	
	private String makeStoreNameDiv() {
		Div div = new Div();
    	div.addProperty("style", "margin-bottom:1.5em; margin-top:3.5em;");
    	div.addElement("<label><b><u>Store Name:</u></b></label>");
    	String storeName = expense.storeName != null ? expense.storeName : "";
    	div.addElement("<input style='margin-left:14%' name='storeName' placeholder='Wal-Mart' value='" + storeName +"' required>");
    	div.addElement("<hr style='width:29.4%; margin-right:70%; margin-top: 1.5em;'>");
    	return div.toString();
	}
	
	private String makeTimeCreatedDiv() {
		Div div = new Div();
    	div.addProperty("style", "margin-bottom:1.5em; margin-top:3.5em;");
    	div.addElement("<label><b><u>Time Created:</u></b></label>");
    	Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String time = expense.timeCreated != null ? formatter.format(expense.timeCreated) : "";
    	div.addElement("<input style='margin-left:13%' name='timeCreated' placeholder='YYYY-MM-DD hh:mm:ss' value='" + time +"'>");
    	if (expense.timeCreated == null) {
    		div.addElement("<p style='margin-top: 0.5em; margin-left:20.3%; font-size:small; color: grey; font-family:serif;'>Defaults to now</p>");
    	}
    	div.addElement("<hr style='width:29.4%; margin-right:70%; margin-top: 1.5em;'>");
    	return div.toString();
	}
	
	private String makePurchasedItemsDiv() {
		Div itemsDiv = new Div();
	    itemsDiv.addProperty("id", "items");
	    for (int i = 0; i < expense.purchasedItems.size(); i++) {
	    	PurchasedItem purchasedItem = expense.purchasedItems.get(i);
	    	String cost = Float.toString(purchasedItem.cost);
	    	String purchasedItemDiv = makePurchasedItemDiv(purchasedItem.name, cost);
	    	itemsDiv.addElement(purchasedItemDiv);
	    }
	    return itemsDiv.toString();
	}
	
	private String makePurchasedItemDiv(String name, String cost) {
		Div div = new Div();
		div.addProperty("class", "item");
    	div.addProperty("style", "margin-bottom:1.5em");
    	div.addElement("<input class='name' name='name' value='" + name + "' required>");
    	div.addElement("<input class='cost' style='margin-left:11%' name='cost' value='" + cost + "' required>");
    	div.addElement("<button class='delete-button' style='margin-left:1em' type='button'>X</button>");
    	return div.toString();
	}
	
	private String makeLabelDiv() {
		Div labelDiv = new Div();
	    labelDiv.addProperty("style", "margin-bottom:2em");
	    labelDiv.addElement("<span><b><u>Name</u></b></span>");
	    labelDiv.addElement("<span style='float:right; margin-right:77%'><b><u>Cost</u></b></span>");
	    return labelDiv.toString();
	}
	
	private String makeNewRowButton() {
		return "<button id='new-item-button' type='button'>+</button><br>";
	}
	
	private String makeSubmitButton() {
		if (expense.willBeManuallyPopulated()) {
			return "<button style='margin-top:1.5em; margin-left:30%' type='submit'>Add</button><br>";
		}
		if (expense.isVerified) {
	    	return "<button style='margin-top:1.5em; margin-left:30%' type='submit'>Save</button><br>";
	    }
	    else {
	    	return "<button style='margin-top:1.5em; margin-left:30%' type='submit'>Verify</button><br>";
	    }
	}
	
	private void addDeleteItemScript() {
		StringBuilder s = new StringBuilder();
	    s.append("<script type=\"text/javascript\">");
	    	s.append("function remove() { ");
	    		s.append("var element = $(this); ");
	    		s.append("var parent = element.closest('.item');");
	    		s.append("var name = parent.find('.name').val();");
	    		s.append("var cost = parent.find('.cost').val();");
	    		s.append("if (name || cost) {");
	    			s.append("if (confirm(\"Are you sure you want to remove this item?\")) {");
	    				s.append("parent.remove(); ");
	    			s.append("}");
	    		s.append("}");
	    		s.append("else { parent.remove(); } ");
	    	s.append("}");
	    	s.append("$('.delete-button').click(remove)");
	    s.append("</script>");
	    htmlBuilder.addToBody(s.toString());
	}
	
	private void addNewItemScript() {
		String newItemHtml = makePurchasedItemDiv("", "").replace("\"", "\\\"").replace("'", "\\\"").replace("\n", "").replace("11%", "11.3%").replace("1em", "1.3em");
		StringBuilder s = new StringBuilder();
	    s.append("<script type=\"text/javascript\">");
	    	s.append("function addNewRow() { ");
	    		s.append("var parent = document.getElementById(\"items\"); ");
	    		s.append("var newElement = document.createElement(\"div\"); ");
	    		s.append("newElement.innerHTML = \"" + newItemHtml + "\"; ");
	    		s.append("newElement.getElementsByClassName(\"delete-button\")[0].addEventListener(\"click\", remove); ");
	    		s.append("parent.appendChild(newElement); ");
	    	s.append("}");
	    	s.append("var element = document.getElementById(\"new-item-button\"); ");
	    	s.append("element.addEventListener(\"click\", addNewRow);");
	    s.append("</script>");
	    htmlBuilder.addToBody(s.toString());
	}

}
