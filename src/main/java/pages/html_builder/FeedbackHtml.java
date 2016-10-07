package pages.html_builder;

public class FeedbackHtml {
	private String text;
	private String color;
	
	private static final String GREEN_IN_HEX = "#00C851";
	private static final String BLUE_IN_HEX = "#33b5e5";
	private static final String ORANGE_IN_HEX = "#ffbb33";
	private static final String RED_IN_HEX = "#ff4444";
	
	public FeedbackHtml(String text) {
		this.text = text;
		this.color = null;
	}
	
	public FeedbackHtml(String text, String color) {
		this.text = text;
		this.color = color;
	}
	
	public String toString() {
		Div div = new Div();
		div.addProperty("style", "background-color: " + colorToHex() +"; padding-top: .5em; padding-bottom: .5em; box-shadow: 0 0 1px 1px #A7ADBA");
		div.addElement("<p style='text-align: center; color: white; font-family: sans-serif; font-weight: lighter;'>" + text + "</p>");
		return div.toString();
	}
	
	private String colorToHex() {
		if (color == null) {
			return BLUE_IN_HEX;
		}
		if(color.equals("green")) {
			return GREEN_IN_HEX;
		}
		else if (color.equals("blue")) {
			return BLUE_IN_HEX;
		}
		else if (color.equals("orange")) {
			return ORANGE_IN_HEX;
		}
		else if (color.equals("red")) {
			return RED_IN_HEX;
		}
		return BLUE_IN_HEX;
	}
}
