package pages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import pages.html_builder.Div;
import pages.html_builder.Form;
import receipt.PurchasedItem;
import receipt.Receipt;

public class ExistingReceiptPage extends Page{
	
	private Receipt receipt;
	
	public ExistingReceiptPage(String baseUrl, Receipt receipt) {
		super(baseUrl);
		this.receipt = receipt;
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
		if (receipt.isVerified) {
			htmlBuilder.addToBody("<h2 style='margin-bottom:2em'>Edit receipt</h2>");
		}
		else {
			htmlBuilder.addToBody("<h2 style='margin-bottom:2em'>Verify Receipt</h2>");
		}
	}

	private void addReceiptForm() {
		Form receiptForm = new Form();
	    try {
			receiptForm.addProperty("action", "/receipt/existing?receiptId=" + URLEncoder.encode(Long.toString(receipt.getId()), "UTF-8"));
		} catch (UnsupportedEncodingException e) { }
	    receiptForm.addProperty("method", "POST");
	    receiptForm.addProperty("style", "margin-bottom:2em");
	    receiptForm.addElement("<h3>Edit Receipt Contents</h3>");
	    receiptForm.addElement("<hr>");
	    receiptForm.addElement(makeLabelDiv());
	    Div itemsDiv = new Div();
	    itemsDiv.addProperty("id", "items");
	    for (int i = 0; i < receipt.purchasedItems.size(); i++) {
	    	PurchasedItem purchasedItem = receipt.purchasedItems.get(i);
	    	String cost = Float.toString(purchasedItem.cost);
	    	String purchasedItemDiv = makePurchasedItemDiv(purchasedItem.name, cost);
	    	itemsDiv.addElement(purchasedItemDiv);
	    }
	    receiptForm.addElement(itemsDiv.toString());
	    receiptForm.addElement(makeNewRowButton());
	    receiptForm.addElement(makeSubmitButton());
	    htmlBuilder.addToBody(receiptForm.toString());
	}
	
	private String makePurchasedItemDiv(String name, String cost) {
		Div div = new Div();
		div.addProperty("class", "item");
    	div.addProperty("style", "margin-bottom:1.5em");
    	div.addElement("<input name='" + "name"+ "' value='" + name + "' required>");
    	div.addElement("<input style='margin-left:11%' name='" + "cost" + "' value='" + cost + "' required>");
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
		if (receipt.isVerified) {
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
	    		s.append("if (confirm(\"Are you sure you want to remove this item?\")) {");
	    			s.append("var parent = this.parentNode; ");
	    			s.append("parent.parentNode.removeChild(parent); ");
	    		s.append("}");
	    	s.append("}");
	    	s.append("var elements = document.getElementsByClassName(\"delete-button\"); ");
	    	s.append("console.log(elements.length);  ");
	    	s.append("for (var i = 0; i < elements.length; i++) {");
	    		s.append("var el = elements[i]; ");
	    		s.append("el.addEventListener(\"click\", remove);");
	    	s.append("}");
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
