package budgeter;

import auto_correct.AutoCorrector;

public class PurchasedItem{
	
	public String name;
	public float cost;
	
	public PurchasedItem() {}
	
	public PurchasedItem(String name, float cost) {
		this.name = name;
		this.cost = cost;
	}
	
	public void autoCorrect() {
		AutoCorrector corrector = new AutoCorrector(name);
		this.name = corrector.correct();
	}
}
