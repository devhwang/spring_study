package study_spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.kr.ucs.spring.service.SignService;
import co.kr.ucs.spring.vo.UserVO;

import static org.junit.Assert.*;

import java.util.*;


@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations="file:src/main/webapp/classes/applicationContext.xml")
@ContextConfiguration(locations="classpath:config/beans.xml")
public class SignServiceTest {
	
	@Autowired
	SignService signService;
	
	//Map userInfo;
	UserVO userInfo;
	boolean isSuccess;
	
	public SignServiceTest() {
		
		userInfo = new UserVO();

		userInfo.setUserId("hwangkiha");
		userInfo.setUserNm("황기하");
		userInfo.setUserPw("1234");
		userInfo.setEmail("test@test.com");
	}
	
	
	@Test
	public void createUser() throws Exception{
		System.out.println(signService.createAccount(userInfo));
	}
	
	@Test
	public void doLoginTest() throws Exception {
		UserVO uv = new UserVO();
		uv = signService.getUserInfo(userInfo);
		System.out.println(uv.getEmail());
	}

	@Test
	public void isUniqueId() throws Exception {
		System.out.println(signService.isUniqueId(userInfo));
	}
	
}
