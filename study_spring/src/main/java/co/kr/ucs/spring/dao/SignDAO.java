package co.kr.ucs.spring.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.kr.ucs.spring.service.SignService;
import co.kr.ucs.spring.vo.UserVO;

@Repository
public class SignDAO {

	private static final Logger logger = LoggerFactory.getLogger(SignService.class);

	@Autowired
	private SqlSession sqlSession;
	
	public UserVO selectUserInfo(UserVO user)throws SQLException {
		return sqlSession.selectOne("login.selectUserInfo", user);
	}
	   
	public int selectUserCount(UserVO user)throws SQLException{
		return sqlSession.selectOne("login.selectUserCount", user);
	}
	
	public int insertUserInfo(UserVO user)throws SQLException{
		return sqlSession.update("login.insertUserInfo", user);
	}
}
