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

	public List<HashMap> getBoardList(HashMap searchInfo)throws SQLException {
		      
		/*int rowSize = Integer.parseInt((String)searchInfo.get("rowSize")); //group당 몇개의 page를 표현할 것인가//page당 몇개의 row를 표현할 것인가
		int totalCount = 0;//resultSet 순회시 대입됨, 질의 결과 총 반환되는 row의 갯수      
		int totalPage = 0;
		//null이면 1블럭
		int nowPage = Integer.parseInt((String)searchInfo.get("page")==null?"1":(String)searchInfo.get("page")); //네비게이터; 현재 어떤 페이지를 보고있는가
		//null이면 1페이지		  
		int maxRowNum = rowSize * (nowPage);//페이지 네비게이터 최대 페이지
		int minRowNum = rowSize * (nowPage-1)+1;//페이지 네비게이터 최소페이지
		
		String type = "";//검색 타입
		String keyword = "";//검색 키워드
		
		totalCount = rs.getInt("TOTCNT");
        totalPage = (totalCount/10)+1;
        System.out.println(totalCount/10);
		*/
		return boardDAO.selectBoardList(searchInfo);
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
