package co.kr.ucs.spring.dao;

import java.sql.SQLException;
import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.kr.ucs.spring.service.SignService;

@Repository
public class BoardDAO {

	private static final Logger logger = LoggerFactory.getLogger(SignService.class);

	@Autowired
	private SqlSession sqlSession;
	
	   
	public int selectTotalCount(HashMap board)throws SQLException{
		return sqlSession.selectOne("board.selectTotalCount", board);
	}
	
	public HashMap selectBoardInfo(HashMap board)throws SQLException {
		return sqlSession.selectOne("board.selectBoardInfo", board);
	}
	
	public List<HashMap> selectBoardList(HashMap board)throws SQLException{
		return sqlSession.selectList("board.selectBoardList", board);
	}
	
	public int insertBoardInfo(HashMap board)throws SQLException{
		return sqlSession.update("board.insertBoardInfo", board);
	}
}
