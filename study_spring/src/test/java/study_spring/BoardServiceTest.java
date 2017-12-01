package study_spring;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.kr.ucs.spring.service.BoardService;
import co.kr.ucs.spring.service.SignService;
import co.kr.ucs.spring.vo.UserVO;

import static org.junit.Assert.*;

import java.util.*;


@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations="file:src/main/webapp/classes/applicationContext.xml")
@ContextConfiguration(locations="classpath:config/beans.xml")
public class BoardServiceTest {
	
	@Autowired
	BoardService boardService;
	
	HashMap boardInfo;
	boolean isSuccess;
	
	public BoardServiceTest() {		
		boardInfo = new HashMap();
		boardInfo.put("TITLE","테스트입니다");
		boardInfo.put("CONTENTS","테스트입니다");
		boardInfo.put("REGID","hwangkiha");
	}
	
	@Test@Ignore
	public void newPost() throws Exception{
		System.out.println(boardInfo.get("TITLE"));
		System.out.println(boardService.writePost(boardInfo));
	}
	
	@Test@Ignore
	public void getBoardInfoTest() throws Exception{
		boardInfo.put("seq", "4");
		String str = (String) (boardService.getBoardInfo(boardInfo)).get("TITLE");
		System.out.println(str==null?"null입니다":str);
	}
	
	@Test@Ignore
	public void getRowCountTest() throws Exception{
		boardInfo.put("TYPE", "NAME");
		boardInfo.put("KEYWORD", "황기하");
		System.out.println(boardService.getRowCount(boardInfo));
	}
	
	@Test
	public void getBoardListTest() throws Exception{
		boardInfo.put("TYPE", "NAME");
		boardInfo.put("KEYWORD", "황기하");
		boardInfo.put("MAXROWNUM", "10");
		boardInfo.put("MINROWNUM", "1");
		System.out.println(boardService.getBoardList(boardInfo));
		List list = new ArrayList();
		list = boardService.getBoardList(boardInfo);
		
		for(HashMap row : boardService.getBoardList(boardInfo)) {
			System.out.print(row.get("REG_DATE")+",");
			System.out.print(row.get("SEQ")+",");
			System.out.print(row.get("REG_ID")+",");
			System.out.print(row.get("TITLE")+",");
			System.out.print(row.get("CONTENTS"));
			System.out.println("");
		}
	}
}

