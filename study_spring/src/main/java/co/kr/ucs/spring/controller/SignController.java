package co.kr.ucs.spring.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import co.kr.ucs.spring.service.SignService;
import co.kr.ucs.spring.vo.UserVO;
@Controller
public class SignController extends HttpServlet{

	@Autowired
	SignService signService;
	
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(SignController.class);
	
	/*
	@RequestMapping(value = "/sign/signUp.do", method = RequestMethod.GET)
	public String signUp() throws Exception{
		
		return "login/signUp";
	}*/
	/*@RequestMapping(value = "/sign/signUpAc.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> signUpAc(UserVO UserVo) throws Exception{
		
		int flag = signService.SignUpAc(UserVo);
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("success", (flag > 0)?"Y":"N");
		
		return map;
	}
	*/
	
	@RequestMapping(value = "/sign", method = RequestMethod.GET)
	public void signIn(HttpServletRequest request, HttpServletResponse response) throws Exception{
		System.out.println("진입");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/login/signIn.jsp");
		dispatcher.forward(request, response);
	}	
	
	@RequestMapping(value = "/sign/signIn.do", method = RequestMethod.POST)
	@ResponseBody
	public void signIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, UserVO userInfo) throws Exception{
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");		
		
		JSONObject result = new JSONObject();
				
		userInfo = new UserVO();
		userInfo = signService.getUserInfo(userInfo);

		Boolean isSuccess = false; 
		String param = request.getParameter("param");
		JSONObject jsonObj =null;
		String msg = "";
		if(param != null){
			JSONParser parser = new JSONParser();
			jsonObj = (JSONObject)parser.parse(param);
			userInfo.setUserId(jsonObj.get("USER_ID").toString());
			userInfo.setUserPw(jsonObj.get("USER_PW").toString());
		}

		if(signService.isUniqueId(userInfo)){
			msg ="등록되지 않은 아이디 입니다.";
		}
		
		if(signService.getUserInfo(userInfo) == null) {
			msg ="아이디 또는 패스워드가 올바르지 않습니다";
		}else{
			request.getSession().setAttribute("USER_ID", userInfo.getUserId());
			request.getSession().setAttribute("USER_NM", userInfo.getUserNm());
			request.getSession().setAttribute("EMAIL", userInfo.getEmail());
			
			msg = userInfo.getUserNm()+"님 방문을 환영합니다.";
			isSuccess = true;
		}			

		if(isSuccess == false) {
			result.put("error", msg);
		}else {
			result.put("success", msg);
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result.toJSONString());
	}
	
	
	/*
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");		
		JSONObject result = new JSONObject();
		
		//uri 분석
		String[] uri = request.getRequestURI().split("/");
		String process = uri[uri.length-1];
		
		String path = request.getContextPath();
		String msg = "";
		Boolean isSuccess = false; 

		String param = request.getParameter("param");
		Map<String, String> userInfo = new HashMap<String, String>();
		JSONObject jsonObj =null;
		
		if(param != null){
			JSONParser parser = new JSONParser();
			jsonObj = (JSONObject)parser.parse(param);
			userInfo.put("USER_ID",jsonObj.get("USER_ID").toString());
			userInfo.put("USER_PW",jsonObj.get("USER_PW").toString());
		}
		
		
		if(process.equals("main")){
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/login/signIn.jsp");
			dispatcher.forward(request, response);			
			
		}else if(process.equals("form")){
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/login/signUp.jsp");
			dispatcher.forward(request, response);			
			
		}else if(process.equals("signin")){
			
			if(signService.isUniqueId(userInfo)){
				msg ="등록되지 않은 아이디 입니다.";
			}else if(!signService.isCorrectPassword(userInfo)) {
				msg ="아이디 또는 패스워드가 올바르지 않습니다";
			}else{
				request.getSession().setAttribute("USER_ID", userInfo.get("USER_ID"));
				request.getSession().setAttribute("USER_NM", userInfo.get("USER_NM"));
				request.getSession().setAttribute("EMAIL", userInfo.get("EMAIL"));
				
				msg = userInfo.get("USER_NM")+"님 방문을 환영합니다.";
				isSuccess = true;
			}			
			
		}else if(process.equals("signup")){
			userInfo.put("USER_NM",jsonObj.get("USER_NM").toString());
			userInfo.put("EMAIL",jsonObj.get("EMAIL").toString());
			
			System.out.println(userInfo);
			
			if(!signService.createAccount(userInfo)) {
				msg ="이미 존재하는 아이디입니다.";
			} else {
				msg ="성공적으로 가입되었습니다";	
				isSuccess = true;
			}
			
		}else{
			msg ="잘못된 접근입니다.";
		}
		
		if(isSuccess == false) {
			result.put("error", msg);
		}else {
			result.put("success", msg);
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result.toJSONString());
		
	}*/
}
