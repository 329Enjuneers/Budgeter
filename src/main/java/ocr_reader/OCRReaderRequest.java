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

public class OCRReaderRequest {
	
	private static final String API_KEY = "fa03bcc77488957";
	private static final String BASE_URL = "https://api.ocr.space/parse/image";
	
	private HTTPRequest request;
	public String imageUrl;
	
	public OCRReaderRequest(String imageUrl) {
		this.imageUrl = imageUrl;
		initRequest();
	}
	
	public JSONObject sendReadRequest() {
		setPayload();
		HTTPResponse response = sendRequest();		
		return getJson(response);
	}
	
	private JSONObject getJson(HTTPResponse response) {
		System.out.println(new String(response.getContent()));
		JSONObject json = convertToJson(response);
		try {
			if (json.getInt("OCRExitCode") != 1) {
				return null;
			}
		} catch (JSONException e) {
			return null;
		}
		return json;
	}
	
	private JSONObject convertToJson(HTTPResponse resp) {
		try {
			return new JSONObject(new String(resp.getContent()));
		} catch (JSONException e) {
			return null;
		}
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
