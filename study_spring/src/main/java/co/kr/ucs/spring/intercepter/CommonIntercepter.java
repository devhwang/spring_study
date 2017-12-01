package co.kr.ucs.spring.intercepter;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CommonIntercepter extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(CommonIntercepter.class);

	/*
	 * HandlerInterceptor 인터페이스
	 * - HandlerInterceptorAdapter 추상 클래스를 지원한다. => 요녀석은 위의 인터페이스를 사용하기 쉽게 구현해 놓은
	 * 추상클래스. // preHandle() : 컨트롤러보다 먼저 수행되는 메서드
	 */

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		logger.info("[컨트롤러를 실행 이전입니다]");
		// session 객체
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("USER_ID");

		if (obj == null) {
			// 로그인이 안되어 있는 상태임으로 로그인 폼으로 다시 돌려보냄(redirect)
			response.sendRedirect(request.getContextPath());
			return false; // 더이상 컨트롤러 요청으로 가지 않도록 false로 반환함
		}

		// preHandle의 return은 컨트롤러 요청 uri로 가도 되냐 안되냐를 허가하는 의미임
		// 따라서 true로하면 컨트롤러 uri로 가게 됨.
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 컨트롤러 실행 이후
		/*
		 * : 컨트롤러의 메서드의 처리가 끝나 return 되고 화면을 띄워주는 처리가 되기 직전에 이 메서드가 수행된다. : ModelAndView
		 * 객체에 컨트롤러에서 전달해 온 Model 객체가 전달됨으로 컨트롤러에서 작업 후 : postHandle() 에서 작업할 것이 있다면
		 * ModelAndView를 이용하면 된다.
		 */
		logger.info("[컨트롤러를 실행한 이후입니다]");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 컨트롤러 실행 직전: 컨트롤러가 수행되고 화면처리까지 끝난 뒤 호출된다.

		logger.info("[컨트롤러가 수행되고 화면처리까지 끝난 뒤 호출]");
	}

}
