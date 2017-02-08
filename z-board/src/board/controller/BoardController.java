package board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import board.dao.BoardDAO;
import board.dto.BoardDTO;
import common.Constants;

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
//			request는 text파일만 처리할 수 있기 때문에 첨부파일을 하려면 mutipartrequest를 써야 한다.		
//			첨부파일 처리를 위한 MultipartRequest 선언
//			request 객체를 multipartrequest로 확장
//			new MiltipartRequest(request객체, 업로드경로, 최대사이즈, 파일이름의 인코딩방식, 중복된 이름의 파일이름 처리)
			MultipartRequest multi = new MultipartRequest(
					request, Constants.UPLOAD_PATH, Constants.MAX_UPLOAD,"utf-8", new DefaultFileRenamePolicy()); 
			String filename="";
			int filesize=0;
			try{
//				파일이 여러개있을 경우 Enumeration으로 처리 ,
//				multi.getFileNames(); => 업로드한 파일을 가져옴
				Enumeration files = multi.getFileNames();
				//첨부파일이 없으면 while문 통과 있으면 계속 처리
				while(files.hasMoreElements()){
					//첫번째 파일을 꺼내옴
					String file1=(String)files.nextElement();
					//그 첨부파일의 이름
					filename = multi.getFilesystemName(file1);
					File f1 = multi.getFile(file1);
					//첨부파일의 사이즈
					filesize = (int)f1.length();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			//request => multi로 변경 (기존에 썼던 request객체에는 파일 업로드 기능이 없다)
			String writer 	= multi.getParameter("writer");
			String subject 	= multi.getParameter("subject");
			String content 	= multi.getParameter("content");
			String passwd 	= multi.getParameter("passwd");
			dto.setWriter(writer);
			dto.setSubject(subject);
			dto.setContent(content);
			dto.setPasswd(passwd);
			if(filename == null || filename.trim().equals("")) filename="-"; //trim()은 좌우 공백 제거
			dto.setFilename(filename);
			dto.setFilesize(filesize);
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
		}else if(url.indexOf("download.do") != -1){
			//게시뭎 번호
			int num=Integer.parseInt(request.getParameter("num"));
			//파일 이름
			String filename=dao.getFileName(num);
			
			//파일 경로
			String path = Constants.UPLOAD_PATH+filename;
			//바이트 배열 선언, 자바에서 Stream은  byte단위로 파일을 처리된다.
			byte b[] = new byte[4096];
			//서버에 있는 파일을 읽어야 클라이언트로 보낼수 있으니 읽을 때는 InputStream
			FileInputStream fis = new FileInputStream(path);
			//파일의 마임타입(파일의 종류), Multipurpose Internet Mail Extension의 약자 다목적 인터넷 메일 확장자
			//즉 웹사이트상에 표현되는 멀티미디어 데이터에 대한 형식, 첨부파일의 확장자를 알아야 하기 때문에 쓴다.
			//이 mimeType이 있어야 웹서비스에서 파일을 인식할 수 있다.
			String mimeType= getServletContext().getMimeType(path);
			System.out.println("MimeType : "+mimeType);
			if(mimeType==null){//mimeType이 지정되지 않았을 때는 아래로 지정한다.
				mimeType = "application/octet-stream;charset=utf-8";
			}
			//http header에 첨부파일 정보 추가  
			//attachment : 지금부터 보내는 파일은 첨부파일이고
			//filename="+filename : filename을 보낸다
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
						//			//한글파일이름 처리
						//			// new String(바이트배열("언어셋"),"변경할 언어셋")
						//			//8859_1 서유럽언어 인코딩 방식
						//			//스트링.getBytes() 스트링을 바이트배열로 변환
						//			//new String(바이트배열) 바이트배열을 스트링으로 변환
						//			filename = new String(filename.getBytes("88859_1"), "utf-8");
			//출력스트림으로 서버의 파일을 클라이언트로 전송
			ServletOutputStream out = response.getOutputStream();
			int numRead;
			//파잉을 읽어서 클라이언트로 보내는 코드
			// 읽은 바이트수 = fis.read(byte배열, 배열에서 읽어들일 인덱스 값, 배열에서 어디까지 읽어들일지 인덱스 값)  --읽어 들인 바이트 수가 리턴됨
			//즉 서버의 파일을 읽어서 바이트배열에 저장
			while((numRead=fis.read(b,0,b.length))!= -1){//더 이상 읽어 들일 바이트 수가 없으면 -1로 리턴된다.
				//클라이언트에 전송
				out.write(b,0,numRead);//클라이언트에 보내는 코드, 보낼때 웹에서는 항상 헤더와 바디가 필요하다
			}
			//리소스 닫기
			out.flush(); //버퍼를 지운다.
			out.close(); //출력 스트림 닫기
			fis.close(); //입력 스트림 닫기
			//다운로드 횟수 증가 처리
			dao.plusDownload(num);
		}else if(url.indexOf("search.do") != -1){
			String search_option = request.getParameter("search_option");
			String keyword = request.getParameter("keyword");
			List<BoardDTO> list = dao.searchList(search_option, keyword);
			
			request.setAttribute("list", list);
			request.setAttribute("search_option", search_option);
			request.setAttribute("keyword", keyword);
			
			String page="/board/search.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
