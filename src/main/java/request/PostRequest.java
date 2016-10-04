package request;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public abstract class PostRequest extends BaseRequest {
	
	protected HashMap<String, String> payloadMap;
	
	public PostRequest() {
		super();
		payloadMap = new HashMap<String, String>();
		initRequest();
	}
	
	protected void initRequest() {
		try {
			request = new HTTPRequest(new URL(getUrl()), HTTPMethod.POST);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public JSONObject send() {
		addHeaders();
		setPayload();
		HTTPResponse response = sendRequest();
		try {
			return convertToJson(response);
		} catch (JSONException e) {
			return null;
		}
	}
	
	protected void setPayload() {
		byte[] payload = makeFormEncodedPayload();
		request.setPayload(payload);
	}
	
	protected byte[] makeFormEncodedPayload() {
		ArrayList<String> payloadElements = new ArrayList<String>();
		for(Entry<String, String> entry  : payloadMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			String payloadItem = key + "=" + value;
			payloadElements.add(payloadItem);
		}
		String str = String.join("&", payloadElements);
		return str.toString().getBytes();
	}
	
	protected HTTPResponse sendRequest() {
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
	
}
