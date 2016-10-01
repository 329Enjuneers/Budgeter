package budgeter;

import com.googlecode.objectify.annotation.Id;

import datastore.BasicEntity;
import datastore.IdList;
import receipt.Receipt;

public class BudgetGroup extends BasicEntity {
	@Id Long id;
	public String name;
	private float amountAllocated;
	public IdList<Expense> expenseIds;
	public IdList<Receipt> receiptIds;
	
	public BudgetGroup() {} // required for objectify
	
	public BudgetGroup(String name, float amountAllocated) {
		this.name = name;
		this.amountAllocated = amountAllocated;
		this.expenseIds = new IdList<Expense>();
		this.receiptIds = new IdList<Receipt>();
	}
	
	public static BudgetGroup getGroup(Long id) {
		return new BudgetGroup().getById(id);
	}
	
	public float getPercentageOfIncome(float income) {
		return amountAllocated / income;
	}
	
	public float getAmountRemaining() {
		return amountAllocated - getAmountRemaining();
	}
	
	public float getAmountSpent() {
		float sum = 0;
		Expense instance = new Expense();
		for (Expense expense : expenseIds.fetch(instance)) {
			sum += expense.amount;
		}
		return sum;
	}
	
	@Override
	public Long getId() {
		return id;
	}
}
