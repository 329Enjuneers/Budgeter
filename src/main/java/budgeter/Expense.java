package budgeter;

import java.util.ArrayList;
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import datastore.BasicEntity;

@Entity
public class Expense extends BasicEntity {
	@Id Long id;
	
	public boolean isVerified;
	public ArrayList<PurchasedItem> purchasedItems;
	public Long authorId;
	public String storeName;
	public Date timeCreated;
	
	public Expense() {
		purchasedItems = new ArrayList<PurchasedItem>();
		this.isVerified = false;
		this.storeName = null;
		this.authorId = null;
	}
	
	public Expense(ArrayList<PurchasedItem> purchasedItems) {
		this.purchasedItems = purchasedItems;
		this.isVerified = false;
		this.storeName = null;
		this.authorId = null;
		this.timeCreated = new Date();
	}
	
	public boolean willBeManuallyPopulated() {
		return purchasedItems.size() == 0;
	}
	
	public float getTotal() {
		float sum = 0;
		for (PurchasedItem purchase : purchasedItems) {
			sum += purchase.cost;
		}
		return sum;
	}

	@Override
	public Long getId() {
		return id;
	}
	
	public void save() {
		if (authorId == null) {
			throw new IllegalStateException("author must be set");
		}
		if (isVerified && storeName == null) {
			throw new IllegalStateException("store name must be set");
		}
		super.save();
	}

}
