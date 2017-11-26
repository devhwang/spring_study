package co.kr.ucs.spring.service;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.kr.ucs.spring.dao.BoardDAO;

@Service
public class BoardService {

	private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

	@Autowired
	private BoardDAO boardDAO;
	
	public int getRowCount(HashMap board)throws SQLException {
		return boardDAO.selectRowCount(board);
	}
	
	public HashMap getBoardInfo(HashMap board)throws SQLException {
		return boardDAO.selectBoardInfo(board);
	}

	public List<HashMap> getBoardList(HashMap board)throws SQLException {
		return boardDAO.selectBoardList(board);
	}

	public String writeNewPost(HashMap board) throws SQLException {
		try {
			boardDAO.insertBoardInfo(board);
		} catch (Exception e) {
			e.printStackTrace();
			return "게시물 등록에 실패하였습니다";
		}
		return "게시물 등록에 성공하였습니다";
	}   
}
