package budgeter;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import datastore.BasicEntity;

@Entity
public class Expense extends BasicEntity{
	@Id private Long id;
	public String store;
	public float amount;
	public String name;
	public Date date;
	
	public Expense() {}
	
	public Expense(String store, String name, float amount) { 
		// TODO
	}
	
	public Expense(String store, String name, float amount, Date date) { 
		// TODO
	}
	
	public static Expense getExpense(Long id) {
		// TODO query for expense by id
		return null;
	}
	public Long getId() {
		return id;
	}
}
