package pages.html_builder;

import java.util.ArrayList;

public class Tabs {
	private ArrayList<Tab> tabs;
	
	public Tabs() {
		tabs = new ArrayList<Tab>();
	}
	
	public void addTab(String link, String tabText) {
		Tab tab = new Tab(link, tabText);
		tabs.add(tab);
	}
	
	public String toString() {
		Div tabsDiv = new Div();
		tabsDiv.addProperty("id", "tabs");
	    tabsDiv.addProperty("class", "tabs");
	    tabsDiv.addElement("<ul>");
	    for (Tab tab : tabs) {
	    	tabsDiv.addElement(tab.toString());
	    }
	    tabsDiv.addElement("</ul>");
	    return tabsDiv.toString();
	}
	
}
