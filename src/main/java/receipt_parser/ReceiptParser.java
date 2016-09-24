package receipt_parser;

public class ReceiptParser {
	
	public String imageUrl;
	
	public ReceiptParser(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public Receipt parse() {
		
		return new Receipt();
	}
}
