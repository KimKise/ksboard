package board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import board.dao.BoardDAO;
import board.dto.BoardDTO;

/**
 * Servlet implementation class BoardController
 */
@WebServlet("/board_servlet/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//url 주소
		//requestURL은 StringBuffer이기 때문에 toString으로 형변환을 해주어야 한다.
		String url = request.getRequestURL().toString();
		String contextPath = request.getContextPath();
		BoardDAO dao = BoardDAO.getInstance();
		
		if(url.indexOf("list.do") != -1){
			List<BoardDTO> list =dao.list();
			request.setAttribute("list", list);
			//포워딩
			String page = "/board/list.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		}else if(url.indexOf("insert.do") != -1){
			BoardDTO dto = new BoardDTO();
			String writer 	= request.getParameter("writer");
			String subject 	= request.getParameter("subject");
			String content 	= request.getParameter("content");
			String passwd 	= request.getParameter("passwd");
			dto.setWriter(writer);
			dto.setSubject(subject);
			dto.setContent(content);
			dto.setPasswd(passwd);
			dao.insert(dto);
			
			//list.do로 이동
			String page =contextPath+"/board_servlet/list.do";
			response.sendRedirect(page);
		}else if(url.indexOf("detail.do") != -1){
			BoardDTO dto = new BoardDTO();
			String num = request.getParameter("num");
			dto = dao.selectOne(num);
			request.setAttribute("dto", dto);
			String page="/board/detail.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
