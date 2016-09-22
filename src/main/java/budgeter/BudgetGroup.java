package budgeter;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Id;

public class BudgetGroup {
	@Id private Long id;
	public String name;
	private float amountAllocated;
	private ArrayList<Long> expenseKeys;
	private ArrayList<String> receiptUrls;
	
	public BudgetGroup() {} // required for objectify
	
	public BudgetGroup(String name, float amountAllocated) {
		// TODO
	}
	
	public static BudgetGroup getGroup(Long id) {
		// TODO query for budget group by id
		return new BudgetGroup();
	}
	
	public ArrayList<Expense> getExpenses() {
		// TODO get expenses associated with this group
		return new ArrayList<Expense>();
	}
	
	public void addExpense(Long groupId) {
		// TODO add expense to group
	}
	
	public void removeExpense(Long expenseId) {
		// TODO remove expense from group
	}
	
	public float getPercentageOfIncome(float income) {
		// TODO 
		return (float) 0.0;
	}
	
	public float getAmountRemaining() {
		// TODO
		return (float) 0.0;
	}
	
	public float getAmountSpent() {
		// TODO
		return (float) 0.0;
	}
}
