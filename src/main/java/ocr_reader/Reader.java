package ocr_reader;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;

public class Reader {
	public String imageUrl;
	
	public Reader(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public ArrayList<Cluster> read() {
		ReadRequest request = new ReadRequest(imageUrl);
		ReadResponse response;
		
		try {
			response = request.sendReadRequest();
		} catch (JSONException e) {
			return new ArrayList<Cluster>();
		}
		
		ArrayList<Cluster> clusters = makeClusters(response);
		return clusters;
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<Cluster> makeClusters(ReadResponse response) {
		ArrayList<HashMap<String, Object>> wordChunks = response.getContent();
		ArrayList<Cluster> clusters = new ArrayList<Cluster>();
		for (int i = 0; i < wordChunks.size(); i++) {
			HashMap<String, Object> chunk = wordChunks.get(i);
			int location = (int) chunk.get("location");
			ArrayList<String> words = (ArrayList<String>) chunk.get("words");
			Cluster cluster = new Cluster(location, words);
			clusters.add(cluster);
		}
		return clusters;
	}

}