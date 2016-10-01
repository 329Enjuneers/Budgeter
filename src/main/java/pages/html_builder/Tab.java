package pages.html_builder;

public class Tab {
	
	private String link;
	private String text;
	
	public Tab(String link, String text) {
		this.link = link;
		this.text = text;
	}
	
	public String toString() {
		return "<li><a class='tab-link' href='" + link + "'>" + text +"</a></li>";
	}
}
