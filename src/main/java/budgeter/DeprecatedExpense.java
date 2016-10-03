package budgeter;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import datastore.BasicEntity;

@Entity
public class DeprecatedExpense extends BasicEntity {
	@Id Long id;
	public String store;
	public double amount;
	public String name;
	public Date date;
	
	public DeprecatedExpense() {}
	
	public DeprecatedExpense(String store, String name, double amount) {
		this.store = store;
		this.name = name;
		this.amount = amount;
		// TODO
	}
	
	public DeprecatedExpense(String store, String name, double amount, Date date) { 
		// TODO
		this.store = store;
		this.name = name;
		this.amount = amount;
		this.date = date;
	}
	
	public static DeprecatedExpense getExpense(Long id) {
		// TODO query for expense by id
		return null;
	}
	
	public double getAmount(){
		return this.amount;
	}
	
	public Long getId() {
		return id;
	}
}
