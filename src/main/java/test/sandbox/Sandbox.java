//package test.sandbox;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
//import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
//import com.googlecode.objectify.ObjectifyService;
//import com.googlecode.objectify.VoidWork;
//
//import feedback.Feedback;
//
///*
// * File for local testing/development
// */
//public class Sandbox {
//
//	public static void main(String[] args) {
//		final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
//		helper.setUp();
//		ObjectifyService.register(Feedback.class);
//
//		ObjectifyService.run(new VoidWork() {
//		    public void vrun() {
//		    	JSONObject json;
//				try {
//					json = new JSONObject("{}");
//					System.out.println(json);
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		    	
//		    }
//		});
//
//		helper.tearDown();
//	}
//
//}
