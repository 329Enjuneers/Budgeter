package ocr_reader;

import java.util.ArrayList;

public class Cluster {
	public int location;
	public ArrayList<String> words;
	
	public Cluster (int location, ArrayList<String> words) {
		this.location = location;
		this.words = words;
	}
}
