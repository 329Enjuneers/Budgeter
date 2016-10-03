package auto_correct;

import java.util.Calendar;

import request.PostRequest;

public class AutoCorrectRequest extends PostRequest {
	private static final String[] API_KEYS = {"65cd30ef7067484682a98462d52dc91a", "6a2e3e2be6594893af4276a06b5a51ca"};
	
	public AutoCorrectRequest(String sentence) {
		super();
		payloadMap.put("Text", encodeString(sentence));
		headersMap.put("Ocp-Apim-Subscription-Key", getApiKey());
	}
	
	private String getApiKey() {
		Calendar rightNow = Calendar.getInstance();
		int minute = rightNow.get(Calendar.MINUTE);
		int index = minute <= 30 ? 0 : 1; 
		return API_KEYS[index];
	}

	@Override
	protected String getUrl() {
		return "https://api.cognitive.microsoft.com/bing/v5.0/spellcheck/";
	}
}
