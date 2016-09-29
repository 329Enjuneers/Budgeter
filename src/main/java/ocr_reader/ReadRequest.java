package ocr_reader;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class ReadRequest {
	
	private static final String API_KEY = "fa03bcc77488957";
	private static final String BASE_URL = "https://api.ocr.space/parse/image";
	
	private HTTPRequest request;
	public String imageUrl;
	
	public ReadRequest(String imageUrl) {
		this.imageUrl = imageUrl;
		initRequest();
	}
	
	public ReadResponse sendReadRequest() throws JSONException {
		setPayload();
		HTTPResponse response = sendRequest();
		JSONObject json = convertToJson(response);
		return new ReadResponse(json);
	}
	
	private JSONObject convertToJson(HTTPResponse resp) throws JSONException {
		return new JSONObject(new String(resp.getContent()));
	}
	
	private HTTPResponse sendRequest() {
		URLFetchService url_service = URLFetchServiceFactory.getURLFetchService();
		HTTPResponse response;
		try {
			response = url_service.fetch(request);
		} catch (IOException e) {
			return null;
		}
		if (response.getResponseCode() != 200) {
			return null;
		}
		return response;
	}
	
	private void setPayload() {
		byte[] payload = makePayload();
		request.setPayload(payload);
	}
	
	private byte[] makePayload() {
		String url;
		try {
			url = URLEncoder.encode(imageUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			url = imageUrl;
		}
		String str = "apikey=" + API_KEY + "&url=" + url + "&isOverlayRequired=True";
		return str.getBytes();
	}
	
	private void initRequest() {
		try {
			request = new HTTPRequest(new URL(BASE_URL), HTTPMethod.POST);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
