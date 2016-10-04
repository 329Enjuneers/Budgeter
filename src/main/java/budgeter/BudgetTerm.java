package budgeter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import datastore.BasicEntity;
import datastore.IdList;

@Entity
public class BudgetTerm extends BasicEntity {
	@Id Long id;
	private Date startDate;
	private Date endDate;
	private float income;
	private ArrayList<String> receiptUrls;
	private IdList<BudgetGroup> budgetGroupIds;
	private boolean termEnded = false;
	
	public BudgetTerm() {} // required for objectify library
	
	public BudgetTerm(float income) {
		this.income = income;
		this.receiptUrls = new ArrayList<String>();
		this.budgetGroupIds = new IdList<BudgetGroup>();
		startDate = new Date();
		createMiscellaneousCategory();
		save();
	}
	
	public BudgetTerm(float income, Date startDate, Date endDate) {
		this.income = income;
		this.startDate = startDate;
		this.endDate = endDate;
		this.receiptUrls = new ArrayList<String>();
		this.budgetGroupIds = new IdList<BudgetGroup>();
		startDate = new Date();
		createMiscellaneousCategory();
		save();
	}
	
	public HashMap<String, ArrayList<Expense>> getExpenses() {
		HashMap<String, ArrayList<Expense>> map = new HashMap<String, ArrayList<Expense>>();
		ArrayList<BudgetGroup> groups = budgetGroupIds.fetch(new BudgetGroup());
		for (BudgetGroup group : groups) {
			map.put(group.name, group.getExpenses());
		}
		return map;
	}
	
	public ArrayList<BudgetGroup> getGroups() {
		return budgetGroupIds.fetch(new BudgetGroup());
	}
	
	public BudgetGroup getGroup(String name) {
		for (BudgetGroup group : getGroups()) {
			if (group.name.equals(name)) {
				return group;
			}
		}
		return null;
	}
	
	public void addGroup(BudgetGroup newGroup) {
		budgetGroupIds.add(newGroup.getId());
		save();
	}

	public void deleteGroup(Long groupId) {
		budgetGroupIds.remove(groupId);
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
		for (BudgetGroup group : getGroups()) {
			sum += group.getAmountSpent();
		}
		return sum;
	}

	public boolean isEnded() {
		return termEnded;
	}

	public void endTerm() {
		termEnded = true;
		endDate = new Date();
		save();
	}
	
	public void removeExpense(Expense expense) {
		for (BudgetGroup group : getGroups()) {
			if (group.hasExpense(expense)) {
				group.removeExpense(expense);
			}
		}
	}

	@Override
	public Long getId() {
		return id;
	}
	
	private void createMiscellaneousCategory() {
		BudgetGroup group = new BudgetGroup("Miscellaneous", (float) 0.0);
		addGroup(group);
	}
}
