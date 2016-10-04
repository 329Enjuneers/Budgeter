package budgeter;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import static com.googlecode.objectify.ObjectifyService.ofy;

import datastore.BasicEntity;
import datastore.IdList;
import receipt.Receipt;

@Entity
public class BudgetGroup extends BasicEntity {
	@Id Long id;
	public String name;
	private double amountAllocated;
	public IdList<Expense> expenseIds;
	public IdList<Receipt> receiptIds;
	
	public BudgetGroup() {} // required for objectify
	
	public BudgetGroup(String name, double amountAllocated) {
		this.name = name;
		this.amountAllocated = amountAllocated;
		this.expenseIds = new IdList<Expense>();
		this.receiptIds = new IdList<Receipt>();
		save();
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
