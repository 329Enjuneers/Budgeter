package pages.html_builder;

public class Feedback {
	String text;
	
	public Feedback(String text) {
		this.text = text;
	}
	
	public String toString() {
		Div div = new Div();
		div.addProperty("style", "background-color: #00C851; padding-top: .5em; padding-bottom: .5em; box-shadow: 0 0 1px 1px #A7ADBA");
		div.addElement("<p style='text-align: center; color: white; font-family: sans-serif; font-weight: lighter;'>" + text + "</p>");
		return div.toString();
	}
}
