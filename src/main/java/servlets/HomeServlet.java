package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import budgeter.BudgetTerm;
import pages.HomePage;

/**
 * shows/adds----> /category
 *
 * adds ---> /receipt
 *
 * links to ----> /history
 *
 *
 */
public class HomeServlet extends BasicServlet{

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
		try{
			super.doGet(req, resp);
		}
		catch(IOException e) { return; }
		showHome();
		
	}


	/**
	 * Crete and End budget terms.
	 * arguments: action (optional) - end: ends current budget term
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try{
			super.doPost(req, resp);
		}
		catch(IOException e) { return; }
		showHome();
	}

	private void showHome()  throws IOException {
		if(user == null){
			out.write(new HomePage(request.getRequestURI()).make());
			return;
		}
		BudgetTerm term = user.getCurrentBudgetTerm();
		if(term == null){
			//create new budget term
			if(request.getParameterMap().containsKey("income")){
				float budget = Float.parseFloat(request.getParameter("income"));
				BudgetTerm newTerm;
				BudgetTerm lastTerm = user.getMostRecentBudgetTerm();
				if (lastTerm == null) {
					newTerm = new BudgetTerm(budget);
				}
				else {
					newTerm = BudgetTerm.makeWithPreviousCategories(lastTerm, budget);
				}
				user.startNewTerm(newTerm);
				response.sendRedirect("/");
			}
			else{
				out.write(new HomePage(request.getRequestURI()).make());
			}
		}
		else{
			//check for action "end"
			if(request.getParameterMap().containsKey("action")){
				String action = request.getParameter("action").toLowerCase();
				if(action != null && action.equals("end")){
					user.endTerm();
				}
				out.write(new HomePage(request.getRequestURI()).make());
			}else{
				out.write(new HomePage(request.getRequestURI(), term).make());
			}
		}
	}
}
