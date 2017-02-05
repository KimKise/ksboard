package board.dao;

import java.util.List;
import java.util.Map;

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
	public BoardDTO selectOne(String num) {
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

}