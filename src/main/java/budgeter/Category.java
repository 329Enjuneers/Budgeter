package budgeter;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import datastore.BasicEntity;
import datastore.IdList;

@Entity
public class Category extends BasicEntity {
	@Id Long id;
	public String name;
	private float amountAllocated;
	public IdList<Expense> expenseIds;
	
	public Category() {} // required for objectify
	
	public Category(String name, float amountAllocated) {
		this.name = name;
		this.amountAllocated = amountAllocated;
		this.expenseIds = new IdList<Expense>();
		save();
	}
	
	public void addExpense(Expense expense) {
		Long id = expense.getId();
		if (id == null) {
			throw new IllegalStateException();
		}
		expenseIds.add(expense.getId());
		save();
	}
	
	public boolean hasExpense(Expense expense) {
		return expenseIds.hasId(expense.getId());
	}
	
	public void removeExpense(Expense expense) {
		expenseIds.remove(expense.getId());
		save();
	}
	
	public ArrayList<Expense> getExpenses() {
		return expenseIds.fetch(new Expense());
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
			sum += expense.getTotal();
		}
		return sum;
	}
	
	public Category makeCopy(float previousIncome, float newIncome) {
		System.out.println("making copy!");
		double previousPercentage = this.getPercentageOfIncome(previousIncome);
		float amountToAllocate = (float) (int) (newIncome * previousPercentage);
		System.out.println("income: " + amountToAllocate);
		System.out.println("name: " + this.name);
		Category copy = new Category(this.name, amountToAllocate);
		return copy;
	}
	
	@Override
	public Long getId() {
		return id;
	}
}
