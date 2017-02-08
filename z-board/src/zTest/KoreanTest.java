package zTest;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class KoreanTest
 */
@WebServlet("/KoreanTest")
public class KoreanTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		

		String nameKorean=request.getParameter("nameKorean");
		String nameEnglish=request.getParameter("nameEnglish");
		
		
		PrintWriter out = response.getWriter();
		out.println(nameKorean);
		out.println(nameEnglish);
	}
		
		
		
/*		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("이름");
		out.println("name");*/


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
