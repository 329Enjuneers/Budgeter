package servlets;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import budgeter.BudgetTerm;
import budgeter.Expense;
import image.BlobImage;
import pages.HomePage;
import pages.UploadReceiptPage;
import receipt_parser.ReceiptParser;

public class ReceiptServlet extends BasicServlet {
	private static final long serialVersionUID = 1L;
	private static final BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try{
			super.doGet(req, resp);
		}
		catch(IOException e) { return; }
		BudgetTerm term = user.getCurrentBudgetTerm();
		if (term == null) {
			out.write(new HomePage(req.getRequestURI(),true).make());
			return;
		}
		out.write(new UploadReceiptPage(req.getRequestURI()).make());
		
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try{
			super.doPost(req, resp);
		}
		catch(IOException e) { return; }

		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
        List<BlobKey> blobKeys = blobs.get("receipt-image");

        if (blobKeys == null || blobKeys.isEmpty()) {
            out.write("You must provide an image!");
            return;
        }
        
        BudgetTerm term = user.getCurrentBudgetTerm();
        BlobImage blobImage = new BlobImage(blobKeys.get(0));
        String imageUrl = blobImage.getUrl();
        ReceiptParser parser = new ReceiptParser(imageUrl);
        Expense expense = parser.parse();
        expense.authorId = user.getId();
        expense.save();
        term.addReceiptUrl(imageUrl);
        resp.sendRedirect("/expense/existing?expenseId=" + URLEncoder.encode(Long.toString(expense.getId()), "UTF-8"));
	}
}
