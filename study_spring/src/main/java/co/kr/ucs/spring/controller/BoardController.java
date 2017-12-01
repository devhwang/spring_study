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
public class BoardController {

	@Autowired
	BoardService board;

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@RequestMapping(value = "/board/main.do")
	public ModelAndView boardMain(ModelAndView mav) throws Exception {
		mav.setViewName("board/boardList");
		return mav;
	}

	@RequestMapping(value = "/board/readForm.do")
	public ModelAndView boardReadForm(ModelAndView mav) throws Exception {
		mav.setViewName("board/boardRead");
		return mav;
	}

	@RequestMapping(value = "/board/writeForm.do")
	public ModelAndView boardWriteForm(ModelAndView mav) throws Exception {
		mav.setViewName("board/boardWrite");
		return mav;
	}

	@RequestMapping(value = "/board/list.do")
	@ResponseBody
	public void boardList(@RequestParam(value = "param") String param, HttpServletRequest request,
			ServletResponse response) throws Exception {

		String msg = "";// 사용자에게 출력할 메시지
		HashMap result = new HashMap(); // 반환할 결과
		HashMap searchInfo = new HashMap();// 게시물 정보

		JsonElement jsonObj = null;

		if (param != null) {
			JsonParser parser = new JsonParser();
			jsonObj = (JsonElement) parser.parse(param);
		}

		searchInfo.put("BLOCKSIZE", jsonObj.getAsJsonObject().get("BLOCKSIZE").getAsString());
		searchInfo.put("ROWSIZE", jsonObj.getAsJsonObject().get("ROWSIZE").getAsString());
		searchInfo.put("PAGE", jsonObj.getAsJsonObject().get("PAGE").getAsString());
		searchInfo.put("TYPE", jsonObj.getAsJsonObject().get("TYPE").getAsString());
		searchInfo.put("KEYWORD", jsonObj.getAsJsonObject().get("KEYWORD").getAsString());

		result.put("list", board.getBoardList(searchInfo));
		result.put("searchInfo", searchInfo);

		Gson gson = new Gson();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(gson.toJson(result));
	}

	@RequestMapping(value = "/board/read.do")
	@ResponseBody
	public void boardRead(@RequestParam(value = "param") String param, HttpServletRequest request,
			ServletResponse response) throws Exception {

		String msg = "";// 사용자에게 출력할 메시지
		HashMap result = new HashMap(); // 반환할 결과
		HashMap boardInfo = new HashMap();// 게시물 정보

		JsonElement jsonObj = null;

		if (param != null) {
			JsonParser parser = new JsonParser();
			jsonObj = (JsonElement) parser.parse(param);
		}

		String seq = jsonObj.getAsJsonObject().get("seq").getAsString();
		
		boardInfo.put("SEQ", seq);

		result.put("brdInfo", board.getBoardInfo(boardInfo));

		Gson gson = new Gson();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(gson.toJson(result));
	}

	@RequestMapping(value = "/board/write.do")
	@ResponseBody
	public void boardWrite(@RequestParam(value = "param") String param, HttpServletRequest request,
			ServletResponse response) throws Exception {

		String msg = "";// 사용자에게 출력할 메시지
		Boolean isSuccess = false; // 성공여부
		HashMap result = new HashMap(); // 반환할 결과
		HashMap boardInfo = new HashMap();// 게시물 정보

		JsonElement jsonObj = null;

		if (param != null) {
			JsonParser parser = new JsonParser();
			jsonObj = (JsonElement) parser.parse(param);
		}

		boardInfo.put("TITLE", jsonObj.getAsJsonObject().get("TITLE").getAsString());
		boardInfo.put("CONTENTS", jsonObj.getAsJsonObject().get("CONTENTS").getAsString());
		boardInfo.put("SEQ", jsonObj.getAsJsonObject().get("SEQ").getAsString());
		boardInfo.put("REG_ID", request.getSession().getAttribute("USER_ID"));

		if (board.writePost(boardInfo)) {
			msg = "성공적으로 글을 저장했습니다";
			isSuccess = true;
		} else {
			msg = "저장에 실패하였습니다. 다시 시도해주세요";
		}

		if (isSuccess == false) {
			result.put("error", msg);
		} else {
			result.put("success", msg);
		}
		Gson gson = new Gson();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(gson.toJson(result));
	}
	
	@RequestMapping(value = "/board/delete.do")
	@ResponseBody
	public void boardDelete(@RequestParam(value = "param") String param, HttpServletRequest request,
			ServletResponse response) throws Exception {

		String msg = "";// 사용자에게 출력할 메시지
		Boolean isSuccess = false; // 성공여부
		HashMap result = new HashMap(); // 반환할 결과
		HashMap boardInfo = new HashMap();// 게시물 정보

		JsonElement jsonObj = null;

		if (param != null) {
			JsonParser parser = new JsonParser();
			jsonObj = (JsonElement) parser.parse(param);
		}

		boardInfo.put("SEQ", jsonObj.getAsJsonObject().get("SEQ").getAsString());
		boardInfo.put("REG_ID", request.getSession().getAttribute("USER_ID"));

		if (board.deletePost(boardInfo)) {
			msg = "성공적으로 글을 삭제했습니다";
			isSuccess = true;
		} else {
			msg = "삭제에 실패하였습니다. 다시 시도해주세요";
		}

		if (isSuccess == false) {
			result.put("error", msg);
		} else {
			result.put("success", msg);
		}
		Gson gson = new Gson();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(gson.toJson(result));
	}
	
}
