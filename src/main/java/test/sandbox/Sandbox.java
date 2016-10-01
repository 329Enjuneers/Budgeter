//package test.sandbox;
//
//import java.util.HashMap;
//
//import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
//import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
//import com.googlecode.objectify.ObjectifyService;
//import com.googlecode.objectify.VoidWork;
//
//import budgeter.Expense;
//import datastore.IdList;
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
//		    	Expense exp = new Expense("Wal-mart", "Dollar", (float) 5.3);
//				exp.save();	
//				System.out.println(exp.getById(exp.getId()));
//				HashMap<String, String> map = new HashMap<String, String>();
//				map.put("store", "Wal-mart");
//				System.out.println(new Expense().getSingleBy(map));
//				IdList<Expense> expenseIds = new IdList<Expense>();
//				expenseIds.add(exp.getId());
//				System.out.println(expenseIds.fetch(exp));
//		    }
//		});
//
//		helper.tearDown();
//	}
//
//}
