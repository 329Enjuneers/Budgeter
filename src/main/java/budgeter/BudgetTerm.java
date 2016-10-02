package budgeter;

import java.util.ArrayList;
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import static com.googlecode.objectify.ObjectifyService.ofy;

import datastore.BasicEntity;
import budgeter.Expense;

@Entity
public class BudgetTerm extends BasicEntity {
	@Id Long id;
	private  ArrayList<Long> groupIds;
	private Date startDate;
	private Date endDate;
	private double income;
	private double totalExpenses;
	private ArrayList<Expense> expenses;
	private ArrayList<String> receiptUrls;
	private ArrayList<BudgetGroup> budgetGroups;
	private boolean termEnded = false;
	
	public BudgetTerm() {} // required for objectify library
	
	public BudgetTerm(double income) {
		this.income = income;
		this.totalExpenses = 0.0;
		this.expenses = new ArrayList<Expense>();
		this.receiptUrls = new ArrayList<String>();
		this.budgetGroups = new ArrayList<BudgetGroup>();
		// this.budgetGroups.add(new BudgetGroup("Test",400));
		save();
	}
	
	public BudgetTerm(double income, Date startDate, Date endDate) {
		this.income = income;
		this.totalExpenses = 0.0;
		this.startDate = startDate;
		this.endDate = endDate;
		this.expenses = new ArrayList<Expense>();
		this.receiptUrls = new ArrayList<String>();
		this.budgetGroups = new ArrayList<BudgetGroup>();
		save();
	}
	
	public ArrayList<Expense> getExpenses() {
		// Return all expenses in during this term.
		return expenses;
	}
	
	public ArrayList<BudgetGroup> getGroups() {
		return budgetGroups;
	}
	
	public void addGroup(BudgetGroup newGroup) {
		budgetGroups.add(newGroup);
		save();
	}

	public void deleteGroup(Long groupId) {
		for(BudgetGroup bg: budgetGroups){
			if(bg.getId() == groupId){
				budgetGroups.remove(budgetGroups.indexOf(bg));
				break;
			}
		}
		save();
	}
	
	public ArrayList<String> getReceiptUrls() {
		// TODO combine all receipt urls on this and groups
		return new ArrayList<String>();
	}
	
	// returns amount remaining
	public double getAmountRemaining() {
		return (double) (income - totalExpenses);
	}
	
	// returns amount spent
	public double getAmountSpent() {
		return (double) totalExpenses;
	}

	// updates amount remaining and amount spent
	public void addExpense(Expense newExpenseAmount) {
		totalExpenses += (double) newExpenseAmount.getAmount();
	}

	// Checks if this term has ended.
	public boolean isEnded() {
		return termEnded;
	}

	// Ends running term.
	public void endTerm() {
		termEnded = true;
		save();
	}

	@Override
	public Long getId() {
		return id;
	}
}
