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
		//���� ���� ����
		userInfo = new HashMap();
		userInfo.put("USER_ID", "hwangkiha");
		userInfo.put("USER_NM", "�׽�Ʈ");
		userInfo.put("USER_PW", "1234");
		userInfo.put("EMAIL", "test@test.com");
	}

/*	@Test
	public void doLoginTest() throws Exception {

		//��й�ȣ�� �¾��� ��� ����
		isSuccess = signService.doLogin(userInfo);
		assertEquals(isSuccess, true);
		
		//��й�ȣ�� Ʋ���� ��츦 ����
		userInfo.put("USER_PW", "12345");
		isSuccess = signService.doLogin(userInfo);
		assertEquals(isSuccess, false);
	}
	*/
	@Test
	public void createAccountTest() throws Exception {
				
		isSuccess = signService.createAccount(userInfo);//�̹� �ִ� �����̹Ƿ� ����
		assertEquals(isSuccess, false);
		
		//���̵� ���� ����
		Random rnd = new Random();
		String randomId = rnd.nextInt(10000)+"";
		
		userInfo.put("USER_ID", randomId);
		isSuccess = signService.createAccount(userInfo);//���̵� ���� ����
		assertEquals(isSuccess, true);
	}
}
