package study_spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.kr.ucs.spring.service.SignService;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:src/main/webapp/classes/applicationContext.xml")
public class SignServiceTest {
	
	@Autowired
	SignService signService;
	
	Map userInfo;
	boolean isSuccess;
	
	public SignServiceTest() {
		//유저 정보 세팅
		userInfo = new HashMap();
		userInfo.put("USER_ID", "hwangkiha");
		userInfo.put("USER_NM", "테스트");
		userInfo.put("USER_PW", "1234");
		userInfo.put("EMAIL", "test@test.com");
	}

/*	@Test
	public void doLoginTest() throws Exception {

		//비밀번호를 맞았을 경우 가정
		isSuccess = signService.doLogin(userInfo);
		assertEquals(isSuccess, true);
		
		//비밀번호를 틀렸을 경우를 가정
		userInfo.put("USER_PW", "12345");
		isSuccess = signService.doLogin(userInfo);
		assertEquals(isSuccess, false);
	}
	*/
	@Test
	public void createAccountTest() throws Exception {
				
		isSuccess = signService.createAccount(userInfo);//이미 있는 정보이므로 실패
		assertEquals(isSuccess, false);
		
		//아이디 랜덤 세팅
		Random rnd = new Random();
		String randomId = rnd.nextInt(10000)+"";
		
		userInfo.put("USER_ID", randomId);
		isSuccess = signService.createAccount(userInfo);//아이디 생성 성공
		assertEquals(isSuccess, true);
	}
}
