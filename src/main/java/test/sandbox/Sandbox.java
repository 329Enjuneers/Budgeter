//package test.sandbox;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
//import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
//import com.googlecode.objectify.ObjectifyService;
//import com.googlecode.objectify.VoidWork;
//
//import budgeter.Expense;
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
//		    	Feedback f = new Feedback("hello");
//		    	System.out.println(f.getId());
//		    	System.out.println(f.text);
//		    	System.out.println(f.getById(1L));
//		    	f.delete();
//		    	System.out.println(f.getId());
//		    	System.out.println(f.text);
//		    	System.out.println(f.getById(1L));
//		    }
//		});
//
//		helper.tearDown();
//	}
//
//}
