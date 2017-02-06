package board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
			int num = Integer.parseInt(request.getParameter("num"));
			//세션 객체 생성, 조회수 바로바로 업뎃 안되게
			HttpSession session = request.getSession();
			//조회수 증가
			dao.readcount(num, session);
			//view를 받기 전에 조회수 증가 처리를 해야한다.
			dto = dao.detail(num);
			request.setAttribute("dto", dto);
			
			
			String page="/board/detail.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		}else if(url.indexOf("passwd_check.do") != -1){
			//게시물 번호
			int num = Integer.parseInt(request.getParameter("num"));
			String passwd = request.getParameter("passwd");
			boolean result = dao.passwdCheck(num, passwd);
			String page = null;
			if(result){//비번이 맞으면
				request.setAttribute("dto", dao.detail(num));
				page="/board/edit.jsp";
				RequestDispatcher rd = request.getRequestDispatcher(page);
				rd.forward(request, response);
			}else{//비번이 맞지 않으면
				page="/board_servlet/detail.do?num="+num+"&message=error";
				response.sendRedirect(request.getContextPath()+page);
			}
		}else if(url.indexOf("update.do") != -1){
			int num = Integer.parseInt(request.getParameter("num"));
			String writer = request.getParameter("writer");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			String passwd = request.getParameter("passwd");
			
			BoardDTO dto = new BoardDTO();
			dto.setNum(num);
			dto.setWriter(writer);
			dto.setSubject(subject);
			dto.setContent(content);
			dto.setPasswd(passwd);
		
			dao.update(dto);
			
			String page = "/board_servlet/list.do";
			response.sendRedirect(contextPath+page);
		}else if(url.indexOf("delete.do") != -1){
			int num = Integer.parseInt(request.getParameter("num"));
			dao.delete(num);
			
			String page="/board_servlet/list.do";
			response.sendRedirect(contextPath+page);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
