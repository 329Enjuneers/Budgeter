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
	private IdList<Category> categoryIds;
	private boolean termEnded = false;
	
	public BudgetTerm() {} // required for objectify library
	
	public BudgetTerm(float income) {
		this.income = income;
		this.receiptUrls = new ArrayList<String>();
		this.categoryIds = new IdList<Category>();
		startDate = new Date();
		createMiscellaneousCategory();
		save();
	}
	
	public BudgetTerm(float income, Date startDate, Date endDate) {
		this.income = income;
		this.startDate = startDate;
		this.endDate = endDate;
		this.receiptUrls = new ArrayList<String>();
		this.categoryIds = new IdList<Category>();
		startDate = new Date();
		createMiscellaneousCategory();
		save();
	}
	
	public HashMap<String, ArrayList<Expense>> getExpenses() {
		HashMap<String, ArrayList<Expense>> map = new HashMap<String, ArrayList<Expense>>();
		ArrayList<Category> categories = categoryIds.fetch(new Category());
		for (Category category : categories) {
			map.put(category.name, category.getExpenses());
		}
		return map;
	}
	
	public ArrayList<Category> getCategories() {
		return categoryIds.fetch(new Category());
	}
	
	public Category getGroup(String name) {
		for (Category group : getCategories()) {
			if (group.name.equals(name)) {
				return group;
			}
		}
		return null;
	}
	
	public void addGroup(Category newGroup) {
		categoryIds.add(newGroup.getId());
		save();
	}

	public void deleteGroup(Long groupId) {
		categoryIds.remove(groupId);
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
		for (Category category : getCategories()) {
			sum += category.getAmountSpent();
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
		for (Category category : getCategories()) {
			if (category.hasExpense(expense)) {
				category.removeExpense(expense);
			}
		}
	}

	@Override
	public Long getId() {
		return id;
	}
	
	private void createMiscellaneousCategory() {
		Category category = new Category("Miscellaneous", (float) 0.0);
		addGroup(category);
	}
}
