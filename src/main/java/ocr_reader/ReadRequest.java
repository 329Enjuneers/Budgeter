package ocr_reader;

import request.PostRequest;

public class ReadRequest extends PostRequest {
	
	private static final String API_KEY = "fa03bcc77488957";
	
	public ReadRequest(String imageUrl) {
		initRequest();
		payloadMap.put("apikey", API_KEY);
		payloadMap.put("url", encodeString(imageUrl));
		payloadMap.put("isOverlayRequired", "True");
	}

	@Override
	protected String getUrl() {
		return "https://api.ocr.space/parse/image";
	}
}
