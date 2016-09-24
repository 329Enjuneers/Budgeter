package test.sandbox;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;

import budgeter.Expense;
import datastore.IdList;
import datastore.QueryFactory;

/*
 * File for local testing/development
 */
public class Sandbox {

	public static void main(String[] args) {
		final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
		helper.setUp();
		ObjectifyService.register(Expense.class);
		
		ObjectifyService.run(new VoidWork() {
		    public void vrun() {
		    	// ENTER YOUR TESTING CODE HERE
		    	Expense exp = new Expense("Wal-mart", "Dollar", (float) 5.3);
				exp.save();
				System.out.println("Saved:" + exp);
				QueryFactory qryFactory = new QueryFactory(Expense.class);
				System.out.println("Fetched: " + qryFactory.getEntityById(exp.getId()));
				IdList<Expense> list = new IdList<Expense>();
				list.add(exp.getId());
				for (Expense expense : list.fetch(Expense.class)) {
					System.out.println("Fetched from list: " + expense);
				}
		    }
		});
		
		helper.tearDown();
	}

}
