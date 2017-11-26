package co.kr.ucs.spring.service;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.kr.ucs.spring.dao.SignDAO;
import co.kr.ucs.spring.vo.UserVO;

@Service
public class SignService {

	private static final Logger logger = LoggerFactory.getLogger(SignService.class);

	@Autowired
	private SignDAO signDAO;
	
	public UserVO getUserInfo(UserVO user)throws SQLException {
		return signDAO.selectUserInfo(user);
	}
	
	public boolean isUniqueId(UserVO user)throws SQLException{		
		if(signDAO.selectUserCount(user) > 0) {
			return false;
		}	   
		return true;
	}

	public String createAccount(UserVO user) throws SQLException {
		try {
			signDAO.insertUserInfo(user);
		} catch (Exception e) {
			e.printStackTrace();
			return "계정 등록에 실패하였습니다";
		}
		return "계정 등록에 성공하였습니다";
	}   
}
