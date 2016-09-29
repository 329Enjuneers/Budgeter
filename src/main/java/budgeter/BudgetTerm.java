package budgeter;

import java.util.ArrayList;
import java.util.Date;

import com.googlecode.objectify.annotation.Id;

import datastore.BasicEntity;
import budgeter.Expense;

public class BudgetTerm extends BasicEntity {
	@Id Long id;
	private  ArrayList<Long> groupIds;
	private Date startDate;
	private Date endDate;
	private double income;
	private double totalExpenses;
	private ArrayList<Expense> expenses;
	private ArrayList<String> receiptUrls;
	
	public BudgetTerm() {} // required for objectify library
	
	public BudgetTerm(double income) {
		this.income = income;
		this.totalExpenses = 0.0;
	}
	
	public ArrayList<BudgetGroup> getGroups() {
		// TODO iterate over every key and fetch the group associated with it
		return new ArrayList<BudgetGroup>();
	}
	
	public void addGroup(Long groupId) {
		// TODO add this id the list of keys
	}
	
	public void deleteGroup(Long groupId) {
		// TODO remove the id from this group
	}
	
	public ArrayList<String> getReceiptUrls() {
		// TODO combine all receipt urls on this and groups
		return new ArrayList<String>();
	}
	
	public double getAmountRemaining() {
		// TODO return amount remaining
		return (double) (income - totalExpenses);
	}
	
	public double getAmountSpent() {
		// TODO return amount spent
		return (double) totalExpenses;
	}

	public void addExpense(Expense newExpenseAmount) {
		// TODO update amount remaining and amount spent
		totalExpenses += (double) newExpenseAmount.getAmount();
	}

	@Override
	public Long getId() {
		return id;
	}
}
