package budgeter;

import java.util.ArrayList;
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import datastore.BasicEntity;
import datastore.IdList;

@Entity
public class BudgetTerm extends BasicEntity {
	@Id Long id;
	private  ArrayList<Long> groupIds;
	private Date startDate;
	private Date endDate;
	private float income;
	private IdList<Expense> expenseIds;
	private ArrayList<String> receiptUrls;
	private ArrayList<BudgetGroup> budgetGroups;
	private boolean termEnded = false;
	
	public BudgetTerm() {} // required for objectify library
	
	public BudgetTerm(float income) {
		this.income = income;
		this.expenseIds = new IdList<Expense>();
		this.receiptUrls = new ArrayList<String>();
		this.budgetGroups = new ArrayList<BudgetGroup>();
		// this.budgetGroups.add(new BudgetGroup("Test",400));
		save();
	}
	
	public BudgetTerm(float income, Date startDate, Date endDate) {
		this.income = income;
		this.startDate = startDate;
		this.endDate = endDate;
		this.expenseIds = new IdList<Expense>();
		this.receiptUrls = new ArrayList<String>();
		this.budgetGroups = new ArrayList<BudgetGroup>();
		save();
	}
	
	public ArrayList<Expense> getExpenses() {
		return expenseIds.fetch(new Expense());
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
	
	public void addReceiptUrl(String servingUrl) {
		receiptUrls.add(servingUrl);
	}
	
	public ArrayList<String> getReceiptUrls() {
		return receiptUrls;
	}
	
	public float getAmountRemaining() {
		return (income - getAmountSpent());
	}
	
	public float getAmountSpent() {
		float sum = 0;
		for (Expense expense : expenseIds.fetch(new Expense())) {
			sum += expense.getTotal();
		}
		return sum;
	}

	public void addExpense(Expense expense) {
		expenseIds.add(expense.getId());
		save();
	}

	public boolean isEnded() {
		return termEnded;
	}

	public void endTerm() {
		termEnded = true;
		save();
	}

	@Override
	public Long getId() {
		return id;
	}
}
