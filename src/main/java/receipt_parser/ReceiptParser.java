package receipt_parser;

import java.util.ArrayList;
import java.util.HashMap;

import budgeter.Expense;
import budgeter.PurchasedItem;
import ocr_reader.Cluster;
import ocr_reader.Reader;
import receipt_parser.float_list_factory.FloatListFactory;

public class ReceiptParser {

	public String imageUrl;

	public ReceiptParser(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Expense parse() {
		ArrayList<Cluster> clusters = readImage();

		ArrayList<MyFloat> floats = getFloats(clusters);
		ArrayList<PurchasedItem> purchasedItems = makePurchasedItems(clusters, floats);
		Expense r = new Expense(purchasedItems);
		return r;
	}

	private ArrayList<Cluster> readImage() {
		Reader reader = new Reader(imageUrl);
		return reader.read();
	}

	private ArrayList<MyFloat> getFloats(ArrayList<Cluster> clusters) {
		FloatListFactory floatListFactory = makeFloatListFactory(clusters);
		return floatListFactory.getAssociatedFloats();
	}

	private ArrayList<PurchasedItem> makePurchasedItems(ArrayList<Cluster> clusters, ArrayList<MyFloat> floats) {
		FloatListFactory floatListFactory = makeFloatListFactory(clusters);
		HashMap<Integer, Phrase> map = floatListFactory.getLocationToWordMapWithoutFloats();
		PurchasedItemFactory purchasedItemFactory = new PurchasedItemFactory(map, floats);
		return purchasedItemFactory.makePuchasedItems();
	}

	private FloatListFactory makeFloatListFactory(ArrayList<Cluster> clusters) {
		FloatListFactory floatListFactory = new FloatListFactory();
		for (Cluster c : clusters) {
			floatListFactory.addData(c.location, c.words);
		}
		return floatListFactory;
	}
}
