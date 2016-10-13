package test.sandbox;

import java.text.DecimalFormat;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;

import feedback.Feedback;

/*
 * File for local testing/development
 */
public class Sandbox {

	public static void main(String[] args) {
		final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
		helper.setUp();
		ObjectifyService.register(Feedback.class);

		ObjectifyService.run(new VoidWork() {
		    public void vrun() {
		    	double d = 1234.234567;
		    	DecimalFormat df = new DecimalFormat("#.##");
		    	System.out.print(df.format(d));
		    	
		    }
		});

		helper.tearDown();
	}

}
