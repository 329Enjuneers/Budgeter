package auto_correct;

import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONObject;

public class AutoCorrector {
	private String sentence;
	
	public AutoCorrector(String sentence) {
		this.sentence = sentence;
	}
	
	public String correct() {
		AutoCorrectResponse response = sendRequest();
		if (response == null) {
			return sentence;
		}
		HashMap<String, String> map = response.getReplacements();
		String newSentence = makeNewSentence(map);
		return newSentence;
	}
	
	private AutoCorrectResponse sendRequest() {
		AutoCorrectRequest request = new AutoCorrectRequest(sentence);
		JSONObject json = request.send();
		if (json == null) {
			return null;
		}
		AutoCorrectResponse response = new AutoCorrectResponse(json);
		return response;
	}
	
	private String makeNewSentence(HashMap<String, String> oldToNewWordMap) {
		String newSentence = sentence;
		for (Entry<String, String> entry : oldToNewWordMap.entrySet()) {
			String oldWord = entry.getKey();
			String newWord = entry.getValue();
			if (newWord != null) {
				newSentence = newSentence.replace(oldWord, newWord);
			}
		}
		return newSentence;
	}
}
