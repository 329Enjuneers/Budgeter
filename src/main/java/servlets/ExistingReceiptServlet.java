package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import datastore.QueryFactory;
import pages.ExistingReceiptPage;
import pages.ReceiptPage;
import receipt.Receipt;

public class ExistingReceiptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		Receipt receipt = getReceipt(req.getParameter("receiptId"));
		if (receipt == null) {
			out.write("Receipt not found");
			return;
		}
		out.write(new ExistingReceiptPage(req.getRequestURI(), receipt).make());
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		// TODO this should edit the values
		out.write(new ReceiptPage(req.getRequestURI()).make());
	}
	
	private Receipt getReceipt(String rawReceiptId) {
		if (rawReceiptId == null) {
			return null;
		}
		
		Long receiptId;
		try {
			receiptId = Long.parseLong(rawReceiptId);
		}
		catch(ClassCastException e) {
			return null;
		}
		
		QueryFactory factory = new QueryFactory(Receipt.class);
		Receipt receipt = factory.getEntityById(receiptId);
		if (receipt == null) {
			return null;
		}
		return receipt;
	}
}
