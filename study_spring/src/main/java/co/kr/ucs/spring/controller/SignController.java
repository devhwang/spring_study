package co.kr.ucs.spring.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import co.kr.ucs.spring.service.SignService;
import co.kr.ucs.spring.vo.UserVO;

@Controller
public class SignController {

	@Autowired
	SignService signService;

	private static final Logger logger = LoggerFactory.getLogger(SignController.class);

	@RequestMapping(value = "/sign/main.do")
	public ModelAndView signMain(ModelAndView mav) throws Exception {
		mav.setViewName("login/signIn");
		return mav;
	}

	@RequestMapping(value = "/sign/form.do")
	public ModelAndView signUpForm(ModelAndView mav) throws Exception {
		mav.setViewName("login/signUp");
		return mav;
	}

	@RequestMapping(value = "/sign/signIn.do")
	@ResponseBody
	public void signIn(@RequestParam(value = "param") String param, HttpServletRequest request,
			ServletResponse response, UserVO userInfo) throws Exception {
		JSONObject result = new JSONObject();
		userInfo = new UserVO();

		Boolean isSuccess = false;
		JSONObject jsonObj = null;
		String msg = "";

		if (param != null) {
			JSONParser parser = new JSONParser();
			jsonObj = (JSONObject) parser.parse(param);
			userInfo.setUserId(jsonObj.get("USER_ID").toString());
			userInfo.setUserPw(jsonObj.get("USER_PW").toString());
		}

		if (signService.isUniqueId(userInfo)) {
			msg = "등록되지 않은 아이디 입니다.";
		} else {
			userInfo = signService.getUserInfo(userInfo);

			if (userInfo == null) {
				msg = "아이디 또는 패스워드가 올바르지 않습니다";
			} else {
				request.getSession().setAttribute("USER_ID", userInfo.getUserId());
				request.getSession().setAttribute("USER_NM", userInfo.getUserNm());
				request.getSession().setAttribute("EMAIL", userInfo.getEmail());

				msg = userInfo.getUserNm() + "님 방문을 환영합니다.";
				isSuccess = true;
			}
		}

		if (isSuccess == false) {
			result.put("error", msg);
		} else {
			result.put("success", msg);
		}

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result.toJSONString());
	}

	@RequestMapping(value = "/sign/logout.do")
	@ResponseBody
	public void logout(HttpServletRequest request, ServletResponse response, UserVO userInfo) throws Exception {

		request.getSession().invalidate();
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write("<script>"
				+ "alert('성공적으로 로그아웃 되었습니다!');"
				+ " location.href='"+request.getContextPath()+"'</script>");
	}

	@RequestMapping(value = "/sign/signUp.do")
	@ResponseBody
	public void signUp(@RequestParam(value = "param") String param, HttpServletRequest request,
			ServletResponse response, UserVO userInfo) throws Exception {
		JSONObject result = new JSONObject();

		userInfo = new UserVO();

		Boolean isSuccess = false;
		JSONObject jsonObj = null;
		String msg = "";

		if (param != null) {
			JSONParser parser = new JSONParser();
			jsonObj = (JSONObject) parser.parse(param);
			userInfo.setUserId(jsonObj.get("USER_ID").toString());
			userInfo.setUserPw(jsonObj.get("USER_PW").toString());
			userInfo.setUserNm(jsonObj.get("USER_NM").toString());
			userInfo.setEmail(jsonObj.get("EMAIL").toString());
		}

		logger.debug(userInfo.getUserId());

		if (!signService.isUniqueId(userInfo)) {
			msg = "이미 존재하는 계정입니다";
		} else {
			if (signService.createAccount(userInfo)) {
				msg = "성공적으로 가입되었습니다";
				isSuccess = true;
			} else {
				msg = "계정 등록에 실패하였습니다";
			}
		}

		if (isSuccess == false) {
			result.put("error", msg);
		} else {
			result.put("success", msg);
		}

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result.toJSONString());
	}

	@RequestMapping(value = "/sign/checkId.do")
	public void checkId(@RequestParam(value = "param") String param, HttpServletRequest request,
			ServletResponse response, UserVO userInfo) throws Exception {

		JSONObject result = new JSONObject();
		userInfo = new UserVO();
		Boolean isSuccess = false;
		JSONObject jsonObj = null;
		String msg = "";

		if (param != null) {
			JSONParser parser = new JSONParser();
			jsonObj = (JSONObject) parser.parse(param);
			userInfo.setUserId(jsonObj.get("USER_ID").toString());
		}

		result.put("list", signService.isUniqueId(userInfo));

		if (signService.isUniqueId(userInfo)) {
			msg = "사용가능한 ID 입니다";
			isSuccess = true;
		} else {
			msg = "이미 존재하는 계정입니다";
		}

		if (isSuccess == false) {
			result.put("error", msg);
		} else {
			result.put("success", msg);
		}

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result.toJSONString());

	}

}
