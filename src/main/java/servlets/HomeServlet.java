package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ocr_reader.OCRReader;
import pages.HomePage;

public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
//		OCRReader reader = new OCRReader("http://lh3.googleusercontent.com/zxe--JDdKH8qw3KDXt7AvLGSfLD2qHHutvwpiS2U-xfE8rgNCw4CaY4bOAPL8Oz0iolZOYOMIhtYGlwveeljDd9kdg8AuonfF7xuA5M5PoQ");
//		try {
//			reader.read();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		out.write(new HomePage(req.getRequestURI()).make());
	}

}
