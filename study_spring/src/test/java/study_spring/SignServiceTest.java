package study_spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.kr.ucs.spring.service.SignService;

import static org.junit.Assert.*;

import java.util.*;


@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations="file:src/main/webapp/classes/applicationContext.xml")
@ContextConfiguration(locations="classpath:config/beans.xml")
public class SignServiceTest {
	
	@Autowired
	SignService signService;
	
	Map userInfo;
	boolean isSuccess;
	
	public SignServiceTest() {
		userInfo = new HashMap();
		userInfo.put("USER_ID", "hwangkiha111");
		userInfo.put("USER_NM", "테스트");
		userInfo.put("USER_PW", "1234");
		userInfo.put("EMAIL", "test@test.com");
	}
	
	@Test
	public void createAccountTest() throws Exception {
				
		isSuccess = signService.createAccount(userInfo);
		assertEquals(isSuccess, false);
		
		Random rnd = new Random();
		String randomId = rnd.nextInt(10000)+"";
		
		userInfo.put("USER_ID", randomId);
		isSuccess = signService.createAccount(userInfo);
		assertEquals(isSuccess, true);
	}
	
	@Test
	public void doLoginTest() throws Exception {
		if(signService.isCorrectPassword(userInfo)) {
			System.out.println("올바른 패스워드");
		}else {
			System.out.println("올바르지 않은 패스워드");
		}		
	}
	
	@Test
	public void getUserExistTest() throws Exception {
		
		if(signService.isUniqueId(userInfo)) {
			System.out.println("유일한 아이디");
		}else{
			System.out.println("중복된 아이디");
		};
		
		
	}
}
