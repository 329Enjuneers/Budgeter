package request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;

public abstract class BaseRequest {
	protected String BASE_URL;
	protected HTTPRequest request;
	protected HashMap<String, String> headersMap;
	
	public BaseRequest() {
		headersMap = new HashMap<String, String>();
	}
	
	public abstract JSONObject send();
	
	protected String encodeString(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}
	
	protected JSONObject convertToJson(HTTPResponse resp) throws JSONException {
		return new JSONObject(new String(resp.getContent()));
	}
	
	protected void addHeaders() {
		for(Entry<String, String> entry  : headersMap.entrySet()) {
			String headerName = entry.getKey();
			String headerValue = entry.getValue();
			HTTPHeader header = new HTTPHeader(headerName, headerValue);
			request.addHeader(header);
		}
	}
	
	protected abstract String getUrl();
}
