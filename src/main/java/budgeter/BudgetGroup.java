package budgeter;

import com.googlecode.objectify.annotation.Id;

import datastore.BasicEntity;
import datastore.IdList;

public class BudgetGroup extends BasicEntity {
	@Id Long id;
	public String name;
	private double amountAllocated;
	public IdList<DeprecatedExpense> expenseIds;
	public IdList<Expense> receiptIds;
	
	public BudgetGroup() {} // required for objectify
	
	public BudgetGroup(String name, double amountAllocated) {
		this.name = name;
		this.amountAllocated = amountAllocated;
		this.expenseIds = new IdList<DeprecatedExpense>();
		this.receiptIds = new IdList<Expense>();
	}
	
	public static BudgetGroup getGroup(Long id) {
		return new BudgetGroup().getById(id);
	}
	
	public double getPercentageOfIncome(double income) {
		return amountAllocated / income;
	}
	
	public double getAmountRemaining() {
		return amountAllocated - getAmountSpent();
	}
	
	public double getAmountSpent() {
		double sum = 0;
		DeprecatedExpense instance = new DeprecatedExpense();
		for (DeprecatedExpense deprecatedExpense : expenseIds.fetch(instance)) {
			sum += deprecatedExpense.amount;
		}
		return sum;
	}
	
	@Override
	public Long getId() {
		return id;
	}
}
