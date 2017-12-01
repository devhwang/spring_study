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
		return boardDAO.selectTotalCount(board);
	}
	
	public HashMap getBoardInfo(HashMap board)throws SQLException {
		return boardDAO.selectBoardInfo(board);
	}

	public List<HashMap> getBoardList(HashMap searchInfo)throws SQLException {
		
		int rowSize = Integer.parseInt((String) searchInfo.get("ROWSIZE"));
		int page = Integer.parseInt((String)searchInfo.get("PAGE")==null?"1":(String)searchInfo.get("PAGE"));
		int maxRowNum = rowSize * (page);//페이지 네비게이터 최대 페이지
		int minRowNum = rowSize * (page-1)+1;//페이지 네비게이터 최소페이지

		searchInfo.put("MAXROWNUM", maxRowNum);
		searchInfo.put("MINROWNUM", minRowNum);
		
		int totalCount = boardDAO.selectTotalCount(searchInfo);
        int totalPage = (totalCount/10)+1;
        
        searchInfo.put("TOTALCOUNT", totalCount);
        searchInfo.put("TOTALPAGE", totalPage);   
        
		return boardDAO.selectBoardList(searchInfo);
	}

	public boolean writePost(HashMap board) throws SQLException {
		try {
			if(board.get("SEQ").equals("") || board.get("SEQ")==null) {
				if(boardDAO.insertBoardInfo(board) == 0)
					return false;
			}else{
				if(boardDAO.updateBoardInfo(board) == 0)
					return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}   
	
	public boolean deletePost(HashMap board) throws SQLException {
		try {
			if(boardDAO.deleteBoardInfo(board) == 0) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}  
}
