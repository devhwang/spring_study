package co.kr.ucs.spring.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.kr.ucs.spring.service.SignService;

//@WebServlet(urlPatterns = {"/sign/*"}, loadOnStartup = 1)
public class SignController extends HttpServlet{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(SignController.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");		
		SignService sign = new SignService();
		JSONObject result = new JSONObject();//반환?�� 결과
		
		//?���?분석
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
			if(false) {
			//if(!sign.doLogin(userInfo)){
				msg ="?��?��?�� ?��?�� ?��?��?��?���? 맞�? ?��?��?��?��";
			}else{
				request.getSession().setAttribute("USER_ID", userInfo.get("USER_ID"));
				request.getSession().setAttribute("USER_NM", userInfo.get("USER_NM"));
				request.getSession().setAttribute("EMAIL", userInfo.get("EMAIL"));
				
				msg = userInfo.get("USER_NM")+"?�� 방문?�� ?��?��?��?��?��.";
				isSuccess = true;
			}			
			
		}else if(process.equals("signup")){
			userInfo.put("USER_NM",jsonObj.get("USER_NM").toString());
			userInfo.put("EMAIL",jsonObj.get("EMAIL").toString());
			
			System.out.println(userInfo);
			
			if(!sign.createAccount(userInfo)) {
				msg ="?���? 존재?��?�� ?��?��?��?��?��?��.";
			} else {
				msg ="?��공적?���? �??��?��?��?��?��?��";	
				isSuccess = true;
			}
			
		}else{
			msg ="?��못된 ?��근입?��?��.";
		}
		
		if(isSuccess == false) {
			result.put("error", msg);
		}else {
			result.put("success", msg);
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result.toJSONString());
		
	}
}
