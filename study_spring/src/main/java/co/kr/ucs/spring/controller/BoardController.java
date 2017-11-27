package co.kr.ucs.spring.controller;

import java.util.HashMap;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import co.kr.ucs.spring.service.BoardService;

@Controller
public class BoardController{

	@Autowired
	BoardService board;
		
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@RequestMapping(value = "/board/main.do")
	public ModelAndView boardMain(ModelAndView mav) throws Exception{
		mav.setViewName("board/boardList");
		return mav;
	}

	@RequestMapping(value = "/board/boardList.do")
	@ResponseBody
	public void boardList(@RequestParam(value="param") String param, HttpServletRequest request, ServletResponse response) throws Exception{

		String msg = "";//사용자에게 출력할 메시지
		Boolean isSuccess = false; //성공여부
		HashMap result = new HashMap();  //반환할 결과
		HashMap searchInfo = new HashMap();//페이징 정보
		HashMap brdInfo = new HashMap();//게시물 정보
		
		JsonElement jsonObj =null;
		  
		if(param != null){
		   JsonParser parser = new JsonParser();
		   jsonObj = (JsonElement)parser.parse(param);
		}
		
		searchInfo.put("blockSize", jsonObj.getAsJsonObject().get("blockSize").getAsString());
        searchInfo.put("rowSize", jsonObj.getAsJsonObject().get("rowSize").getAsString());
        searchInfo.put("page", jsonObj.getAsJsonObject().get("page").getAsString());
        searchInfo.put("type", jsonObj.getAsJsonObject().get("type").getAsString());
        searchInfo.put("keyword", jsonObj.getAsJsonObject().get("keyword").getAsString());
        
        result.put("list", board.getBoardList(searchInfo));
        result.put("searchInfo", searchInfo);

		if(isSuccess == false) {
		   result.put("error", msg);
		}else {
		   result.put("success", msg);
		}
		Gson gson = new Gson();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(gson.toJson(result));
	}
}


/*




public class BoardController extends HttpServlet{

 
       
      // 기능수행
      if(process.equals("main")) {//리스트조회
         
         //단순 페이지 이동만
         RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/board/boardList.jsp");
         dispatcher.forward(request, response);      
         
      }else if(process.equals("list")){
         searchInfo.put("blockSize", jsonObj.getAsJsonObject().get("blockSize").getAsString());
         searchInfo.put("rowSize", jsonObj.getAsJsonObject().get("rowSize").getAsString());
         searchInfo.put("page", jsonObj.getAsJsonObject().get("page").getAsString());
         searchInfo.put("type", jsonObj.getAsJsonObject().get("type").getAsString());
         searchInfo.put("keyword", jsonObj.getAsJsonObject().get("keyword").getAsString());
         
         result.put("list", board.getlist(searchInfo));
         result.put("searchInfo", searchInfo);
         
      
      }else if(process.equals("form")) {//글 작성폼으로 이동
         RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/board/boardWrite.jsp");
         dispatcher.forward(request, response);   
         
      }else if(process.equals("read")) {//글 상세로 이동
         RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/board/boardRead.jsp");
         dispatcher.forward(request, response);   
         
      }else if(process.equals("detail")) {//글 상세로 이동
    	  String seq = jsonObj.getAsJsonObject().get("seq").getAsString();
    	  String page = jsonObj.getAsJsonObject().get("page").getAsString();
    	  
		  brdInfo.put("seq", seq);
		  brdInfo.put("page", page);
	      result.put("brdInfo", board.getDetailView(seq));
          
       }else if(process.equals("write")) {//글 쓰기로 이동
         brdInfo.put("TITLE", jsonObj.getAsJsonObject().get("TITLE").getAsString());
         brdInfo.put("CONTENTS", jsonObj.getAsJsonObject().get("CONTENTS").getAsString());
         brdInfo.put("REG_ID", request.getSession().getAttribute("USER_ID"));
         
         if(board.doWrite(brdInfo)){
            msg = "성공적으로 글을 등록했습니다";
            isSuccess = true;
         }else{
            msg = "등록에 실패하였습니다. 다시 시도해주세요";
         }
         
      }else{
         msg ="잘못된 접근입니다.";      
      }
      
      if(isSuccess == false) {
         result.put("error", msg);
      }else {
         result.put("success", msg);
      }
      Gson gson = new Gson();
      response.setContentType("text/html;charset=UTF-8");
      response.getWriter().write(gson.toJson(result));
      
                  
   }*/