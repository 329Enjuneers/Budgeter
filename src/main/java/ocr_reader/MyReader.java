package ocr_reader;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;

public class MyReader {
	public String imageUrl;
	
	public MyReader(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public ArrayList<Cluster> read() {
		ReadRequest request = new ReadRequest(imageUrl);
		ReadResponse response;
		
		try {
			response = request.sendNewReadRequest();
		} catch (JSONException e) {
			return new ArrayList<Cluster>();
		}
		
		ArrayList<Cluster> clusters = makeClusters(response);
		for (Cluster cluster : clusters) {
			System.out.println(cluster.location + " : " + cluster.words.toString());
		}
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
