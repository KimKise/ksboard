package board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;

import board.dto.BoardDTO;
import config.MybatisService;

public class BoardDAO {
	//싱글톤 생성
	private static BoardDAO instance;
	private BoardDAO(){}
	public static BoardDAO getInstance(){
		if(instance == null){
			instance = new BoardDAO();
		}
		return instance;
	}
	public List<BoardDTO> list() {
		List<BoardDTO> list =null;
		SqlSession session = null;
		try {
			session = MybatisService.getFactory().openSession();
			list = session.selectList("board.list");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null)session.close();
		}
		return list;
		
	}
	public void insert(BoardDTO dto) {
		SqlSession session = null;
		try {
			//mybatis 실행객체 (SqlSession) 생성
			session = MybatisService.getFactory().openSession();
			session.insert("board.insert",dto);
			//insert, delete, update는 commit을 해야한다.
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null)session.close();
		}
		
	}
	public BoardDTO detail(int num) {
		BoardDTO dto = new BoardDTO();
		SqlSession session = null;
		try {
			session = MybatisService.getFactory().openSession();
			dto = session.selectOne("board.selectOne", num);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null)session.close();
		}
		return dto;
	}
	public boolean passwdCheck(int num, String passwd) {
		SqlSession session = null;
		boolean result = false ;
		try {
			session = MybatisService.getFactory().openSession();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("num", num);
			map.put("passwd", passwd);
			String dbpasswd= null;
			dbpasswd = session.selectOne("board.passwd_check", map);
			if(passwd.equals(dbpasswd)){
				result = true;
			}else{
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null) session.close();
		}
		return result;
		
		
	}
	public void update(BoardDTO dto){
		SqlSession session = null;
		try {
			session = MybatisService.getFactory().openSession();
			session.update("board.update", dto);
			// 커밋(insert, delete, update)는 커밋이 필요
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null) session.close();
		}
	}
	public void delete(int num) {
		SqlSession session = null;
		try {
			session = MybatisService.getFactory().openSession();
			session.delete("board.delete", num);
			// 커밋(insert, delete, update)는 커밋이 필요
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null) session.close();
		}
		
	}
	public void readcount(int num, HttpSession count_session) {
		SqlSession session = null;
		try {
			//최근 열람 시간 조회(세션변수로), 일정시간 조회수 증가 처리를 위해 
			long update_time= 0;
			// 최근 열람 시간이 세션에 저장돼있지 않으면
			if(count_session.getAttribute("update_time_"+num)!=null){//--getAttribute
				update_time=(long)count_session.getAttribute("update_time_"+num);
			}
			//현재 시간
			long current_time = System.currentTimeMillis();
			session = MybatisService.getFactory().openSession();
			if(current_time - update_time > 5*1000){ //현재 시간과 비교하여 5초 이상이면 업데이트를 하세요
				session.update("board.readcount", num);
				session.commit();
				//업데이트 끝나고 최근 열람 시간을 세션에 저장
				count_session.setAttribute("update_time_"+num, current_time);//--setAttribute
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null) session.close();
		}
		
	}

}