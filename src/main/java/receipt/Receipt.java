package receipt;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import datastore.BasicEntity;

@Entity
public class Receipt extends BasicEntity {
	@Id Long id;
	
	public boolean isVerified;
	public ArrayList<PurchasedItem> purchasedItems;
	public Long authorId;
	
	public Receipt() {}
	
	public Receipt(ArrayList<PurchasedItem> purchasedItems) {
		this.purchasedItems = purchasedItems;
		this.isVerified = false;
		authorId = null;
	}
	
	public void print() {
		for (PurchasedItem item : purchasedItems) {
			System.out.println(item.name + " :: " + item.cost);
		}
	}

	@Override
	public Long getId() {
		return id;
	}
	
	public void save() {
		if (authorId == null) {
			throw new IllegalStateException("author must be set");
		}
		super.save();
	}

}
