package budgeter;

import java.util.Date;

import com.googlecode.objectify.annotation.Id;

public class Expense {
	@Id private Long id;
	public String store;
	public float amount;
	public String name;
	public Date date;
	
	public Expense() {}
	
	public Expense(String store, String name, String amount) { 
		// TODO
	}
	
	public Expense(String store, String name, String amount, Date date) { 
		// TODO
	}
	
	public static Expense getExpense(Long id) {
		// TODO query for expense by id
		return null;
	}
}
