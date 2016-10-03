package pages;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import pages.html_builder.Form;

public class UploadReceiptPage extends Page {

	public UploadReceiptPage(String baseUrl) {
		super(baseUrl);
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
			htmlBuilder.setTitle("Upload Receipt");
		} catch (Exception e) {}
	}
	
	private void addHeader() {
		htmlBuilder.addToBody("<h2 style='margin-bottom:2em'>Upload Receipt</h2>");
	}

	private void addReceiptForm() {
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		Form receiptUploadForm = new Form();
	    receiptUploadForm.addProperty("action", blobstoreService.createUploadUrl("/receipt"));
	    receiptUploadForm.addProperty("method", "POST");
	    receiptUploadForm.addProperty("enctype", "multipart/form-data");
	    receiptUploadForm.addProperty("style", "margin-bottom:2em");
	    receiptUploadForm.addElement("<div style='margin-bottom: 1em'><label><b>Upload image</b></label></div>");
	    receiptUploadForm.addElement("<input type='file' name='receipt-image' accept='image/png, image/jpeg, image/gif' style='display:block; padding-bottom:1.5em;' required>");
	    receiptUploadForm.addElement("<button type='submit'>Upload</button>");
	    htmlBuilder.addToBody(receiptUploadForm.toString());
	}

}
