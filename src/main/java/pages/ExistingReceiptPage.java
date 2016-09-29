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
	    for (PurchasedItem purchasedItem : receipt.purchasedItems) {
	    	Div div = new Div();
	    	div.addProperty("style", "margin-bottom:1.5em");
	    	div.addElement("<label><b>" + purchasedItem.name + "</b></label>");
	    	div.addElement("<input value='" + purchasedItem.cost + "'>");
	    	receiptForm.addElement(div.toString());
	    }
	    if (receipt.isVerified) {
	    	receiptForm.addElement("<button type='submit'>Edit</button>");
	    }
	    else {
	    	receiptForm.addElement("<button type='submit'>Verify</button>");
	    }
	    htmlBuilder.addToBody(receiptForm.toString());
	}

}
