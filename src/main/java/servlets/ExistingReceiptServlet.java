package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datastore.QueryFactory;
import pages.ExistingReceiptPage;
import receipt.PurchasedItem;
import receipt.Receipt;
import user.User;

public class ExistingReceiptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final User user = User.getCurrentUser();
	
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
		Receipt receipt = getReceipt(req.getParameter("receiptId"));
		if (!receipt.isVerified) {
			receipt.isVerified = true;
		}
		String[] names = req.getParameterValues("name");
		String[] costs = req.getParameterValues("cost");
		ArrayList<PurchasedItem> purchasedItems = new ArrayList<PurchasedItem>();
		if (names.length != costs.length) {
			out.write("Names and costs must be the same!");
			return;
		}
		
		for (int i = 0; i < names.length; i++) {
			float f = 0;
			try {
				f = Float.parseFloat(costs[i]);
			}
			catch(Exception e) {
				out.write("cost must be float!");
				return;
			}
			purchasedItems.add(new PurchasedItem(names[i], f));
		}
		receipt.purchasedItems = purchasedItems;
		receipt.save();
		resp.sendRedirect("/receipt/existing?receiptId=" + URLEncoder.encode(Long.toString(receipt.getId()), "UTF-8"));
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
		if (receipt == null || !receipt.authorId.equals(user.getId())) {
			return null;
		}
		return receipt;
	}
}
