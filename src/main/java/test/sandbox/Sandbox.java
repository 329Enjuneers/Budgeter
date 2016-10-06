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
//
///*
// * File for local testing/development
// */
//public class Sandbox {
//
//	public static void main(String[] args) {
//		final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
//		helper.setUp();
//		ObjectifyService.register(Expense.class);
//
//		ObjectifyService.run(new VoidWork() {
//		    public void vrun() {
//		    	// ENTER YOUR TESTING CODE HERE
//		    }
//		});
//
//		helper.tearDown();
//	}
//
//}
