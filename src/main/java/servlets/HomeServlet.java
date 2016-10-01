package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pages.HomePage;
import user.User;


/**
 * shows/adds----> /group
 * 
 * adds ---> /receipt
 * 
 * links to ----> /history
 * 
 *
 */
public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * show current budget term
	 * button to end current budget term
	 * 
	 * if one has not been created: start new budget term
	 * 
	 * if this budget term is already complete, nothing is editable
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		User user = User.getCurrentUser();
		
		//get current budget term
		//BudgetTerm currentTerm = user.getCurrentBudgetTerm();
		//if one has not been created, start new budget term
//		if(currentTerm == null)
//		{
//			//TODO user.startNewTerm(new BudgetTerm(income));
//		}
		//TODO if this budget term is already complete, nothing is editable

		//		ReceiptParser parser = new ReceiptParser("http://lh3.googleusercontent.com/zxe--JDdKH8qw3KDXt7AvLGSfLD2qHHutvwpiS2U-xfE8rgNCw4CaY4bOAPL8Oz0iolZOYOMIhtYGlwveeljDd9kdg8AuonfF7xuA5M5PoQ");
		//		try {
		//			parser.parse();
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
		out.write(new HomePage(req.getRequestURI()).make());
	}


	/**
	 * create new budget term
	 * 
	 * if action = end: end current budget term
	 * 
	 * arguments: action (optional) - end: ends current budget term
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		User user = User.getCurrentUser();
		
		
		//create new budget term 
		//TODO what is income to set budget term?
		//BudgetTerm term = new BudgetTerm(income);
		
		
		//check for action "end"
		String action = req.getParameter("action").toLowerCase();
		if(action != null && action.equals("end"))
		{
			//end current budget term
			user.endTerm();
			
		}

		out.write(new HomePage(req.getRequestURI()).make());//,user.getCurrentBudgetTerm() ).make());
	}

}
