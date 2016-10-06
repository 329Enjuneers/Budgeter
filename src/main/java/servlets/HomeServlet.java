package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pages.HomePage;
import user.User;
import budgeter.BudgetTerm;

/**
 * shows/adds----> /category
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
		showHome(req,resp);
	}


	/**
	 * Crete and End budget terms.
	 * arguments: action (optional) - end: ends current budget term
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		showHome(req,resp);
	}
	
	private void showHome(HttpServletRequest req, HttpServletResponse resp)  throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		User user = User.getCurrentUser();
		if(user == null){
			out.write(new HomePage(req.getRequestURI()).make());
			return;
		}
		BudgetTerm term = user.getCurrentBudgetTerm();
		if(term == null){
			//create new budget term 
			if(req.getParameterMap().containsKey("income")){
				float budget = Float.parseFloat(req.getParameter("income"));
				BudgetTerm newTerm = new BudgetTerm(budget);
				user.startNewTerm(newTerm);
				out.write(new HomePage(req.getRequestURI(),user.getCurrentBudgetTerm()).make());
			}
			else{
				out.write(new HomePage(req.getRequestURI()).make());
			}
		}
		else{
			//check for action "end"
			if(req.getParameterMap().containsKey("action")){
				String action = req.getParameter("action").toLowerCase();
				if(action != null && action.equals("end")){
					user.endTerm();
				}
				out.write(new HomePage(req.getRequestURI()).make());			
			}else{
				out.write(new HomePage(req.getRequestURI(), term).make());			
			}
		}
	}
}
