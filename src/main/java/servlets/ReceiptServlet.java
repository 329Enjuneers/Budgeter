package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import budgeter.BudgetTerm;
import budgeter.Expense;
import image.BlobImage;
import pages.UploadReceiptPage;
import receipt_parser.ReceiptParser;
import user.User;

public class ReceiptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	private User user;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		user = User.getCurrentUser();
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		BudgetTerm term = user.getCurrentBudgetTerm();
		if (term == null) {
			out.write("You have not started a budget term yet! Please visit the <a href='/'>home page</a> to start a new one!");
			return;
		}
		out.write(new UploadReceiptPage(req.getRequestURI()).make());
		
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		user = User.getCurrentUser();
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");

		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
        List<BlobKey> blobKeys = blobs.get("receipt-image");

        if (blobKeys == null || blobKeys.isEmpty()) {
            out.write("You must provide an image!");
            return;
        }

        BudgetTerm term = user.getCurrentBudgetTerm();
        BlobImage blobImage = new BlobImage(blobKeys.get(0));
        String imageUrl = blobImage.getUrl();
        System.out.println("Created image url: " + imageUrl);
        System.out.println("Instead using: http://lh3.googleusercontent.com/zxe--JDdKH8qw3KDXt7AvLGSfLD2qHHutvwpiS2U-xfE8rgNCw4CaY4bOAPL8Oz0iolZOYOMIhtYGlwveeljDd9kdg8AuonfF7xuA5M5PoQ");
        imageUrl = "http://lh3.googleusercontent.com/zxe--JDdKH8qw3KDXt7AvLGSfLD2qHHutvwpiS2U-xfE8rgNCw4CaY4bOAPL8Oz0iolZOYOMIhtYGlwveeljDd9kdg8AuonfF7xuA5M5PoQ";
        ReceiptParser parser = new ReceiptParser(imageUrl);
        Expense expense = parser.parse();
        expense.authorId = user.getId();
        expense.save();
        term.addReceiptUrl(imageUrl);
        resp.sendRedirect("/expense/existing?expenseId=" + URLEncoder.encode(Long.toString(expense.getId()), "UTF-8"));
	}
}
