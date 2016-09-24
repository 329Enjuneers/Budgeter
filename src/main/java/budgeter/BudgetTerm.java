package budgeter;

import java.util.ArrayList;
import java.util.Date;

import com.googlecode.objectify.annotation.Id;

import datastore.BasicEntity;

public class BudgetTerm extends BasicEntity {
	@Id private Long id;
	private  ArrayList<Long> groupIds;
	private Date startDate;
	private Date endDate;
	private float income;
	private ArrayList<String> receiptUrls;
	
	public BudgetTerm() {} // required for objectify library
	
	public BudgetTerm(float income) {
		// TODO
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
	
	public float getAmountRemaining() {
		// TODO compute amount remaining
		return (float) 0.0;
	}
	
	public float getAmountSpent() {
		// TODO compute amount spent
		return (float) 0.0;
	}

	@Override
	public Long getId() {
		return id;
	}
}
