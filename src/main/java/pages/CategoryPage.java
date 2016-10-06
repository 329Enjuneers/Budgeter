package pages;

import java.util.ArrayList;

import budgeter.BudgetTerm;
import budgeter.Category;
import pages.html_builder.Form;

public class CategoryPage extends Page {
	private BudgetTerm term;
	public CategoryPage(String baseUrl) {
		super(baseUrl);
		htmlBuilder.includeAppHeader = true;
	}
	public CategoryPage(String baseUrl, BudgetTerm term) {
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
		htmlBuilder.addToBody("<style>th, td { border-bottom: 1px solid #ddd; padding: 10px; } table { text-align: left; vertical-align: bottom;}</style>");
		addNewCategoryForm();
		printExistingCategory();
	    addHorizontalRule();
	    return htmlBuilder.build();
	}

	private void setTitle() {
		try {
			htmlBuilder.setTitle("Categories");
		} catch (Exception e) {}
	}
	
	private void printExistingCategory(){
		if(term == null){return;}
		ArrayList<Category> categories = term.getCategories();
		if(categories != null && categories.size() > 0){
			String categorySummary = "";
			categorySummary += "<table>";
			categorySummary += "<thead><tr>";
			categorySummary += "<th>Category</th>";
			categorySummary += "<th>Original Amount</th>";
			categorySummary += "<th>Spent</th>";
			categorySummary += "<th>Available</th>";
			categorySummary += "<th>Action</th>";
			categorySummary += "</tr></thead>";
			categorySummary += "<tbody>";
			for (Category category : categories) {
				categorySummary += makeCategoryRow(category);
			}
			categorySummary += "</tbody>";
			categorySummary += "</table>";
			htmlBuilder.addToBody(categorySummary);
		}
	}
	
	private String makeCategoryRow(Category category) {
		StringBuilder row = new StringBuilder();
		row.append("<tr>");
			row.append("<form action='/category?categoryId=" + category.getId() + "' method='post'>");
			row.append("<td>" + category.name + "</td>");
			row.append("<td><input type='number' name='amountAllocated' value='" + category.amountAllocated + "'></td>");
			row.append("<td>" + category.getAmountSpent() + "</td>");
			row.append("<td>" + category.getAmountRemaining() + "</td>");
			row.append("<td><input name='action' value='Update' type='submit'><input name='action' value='Delete' type='submit'><input type='reset'></td>");
			row.append("</form>");
		row.append("</tr>");
		return row.toString();
	}

	private void addNewCategoryForm() {
		String categoryFormTable = "<div id='term-summary'>";
		categoryFormTable += "<table>";
		categoryFormTable += "<thead><tr>";
		categoryFormTable += "<th colspan=\"2\">New Category</th>";
		categoryFormTable += "<tr>";
		categoryFormTable += "</tr></thead>";
		categoryFormTable += "<tbody><tr>";
		categoryFormTable += "<td>Name:</td><td>&nbsp;&nbsp;<input name='newcategory' placeholder='New Category' required/></td>";
		categoryFormTable += "</tr><tr>";
		categoryFormTable += "<td>Amount:</td><td>$ &nbsp;<input name='amount' placeholder='Amount to be allocated' required/></td>";
		categoryFormTable += "</tr>";
		categoryFormTable += "</tbody>";
		categoryFormTable += "</table>";
		categoryFormTable += "</div><br/>";
		Form newCategoryForm = new Form();
	    newCategoryForm.addProperty("action", "/category");
	    newCategoryForm.addProperty("method", "POST");
	    newCategoryForm.addProperty("style", "margin-bottom:2em");
	    newCategoryForm.addElement(categoryFormTable);
	    newCategoryForm.addElement("<button type='submit'>Add</button></div>");
	    htmlBuilder.addToBody(newCategoryForm.toString());
	}
}
