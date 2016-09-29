package ocr_reader;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReadResponse {
	
	public JSONObject json;
	
	public ReadResponse(JSONObject json) {
		this.json = json;
	}
	
	public ArrayList<HashMap<String, Object>> getContent() {
		ArrayList<HashMap<String, Object>> chunks = new ArrayList<HashMap<String, Object>>();
		ArrayList<Line> lines = getLines();
		for(int i = 0; i < lines.size(); i++) {
			Line line = lines.get(i);
			HashMap<String, Object> chunk;
			try {
				chunk = line.getContent();
			} catch (JSONException e) {
				continue;
			}
			chunks.add(chunk);
		}
		return chunks;
	}
	
	private ArrayList<Line> getLines() {
		ArrayList<Line> lines = new ArrayList<Line>();
		JSONArray rawLines = getRawLines();
		for(int i = 0; i < rawLines.length(); i++) {
			JSONObject rawLine;
			try {
				rawLine = rawLines.getJSONObject(i);
			} catch (JSONException e) {
				continue;
			}
			Line line = new Line(rawLine);
			lines.add(line);
		}
		return lines;
	}
	
	private JSONArray getRawLines() {
		try {
			return json.getJSONArray("ParsedResults").getJSONObject(0).getJSONObject("TextOverlay").getJSONArray("Lines");
		} catch (JSONException e) {
			return new JSONArray();
		}
	}
	
	public boolean isValid() {
		try {
			if (json.getInt("OCRExitCode") != 1) {
				return false;
			}
		} catch (JSONException e) {
			return false;
		}
		return true;
	}
}