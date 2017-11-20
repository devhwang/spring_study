package co.kr.ucs.spring.service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;



@Service
public class SignService {

	private static final Logger logger = LoggerFactory.getLogger(SignService.class);

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public Map doLogin(Map userInfo)throws SQLException {
		String userId = (String)userInfo.get("USER_ID");
		String userPw = (String)userInfo.get("USER_PW");
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT USER_ID, USER_NM, USER_PW, EMAIL FROM CM_USER WHERE USER_ID = ?");
		sql.append(" AND USER_PW = ?");
		
		return jdbcTemplate.queryForObject(sql.toString(), new Object[] {userId, userPw}, 
				new RowMapper<Map>() {
					public Map mapRow(ResultSet rs, int rowNum) throws SQLException {
						Map userInfo = new HashMap();
						userInfo.put("USER_ID",rs.getString("USER_ID"));
						userInfo.put("USER_NM",rs.getString("USER_NM"));
						userInfo.put("USER_PW",rs.getString("USER_PW"));
						userInfo.put("EMAIL",rs.getString("EMAIL"));
						return userInfo;
					}
	});
   }
	
   public boolean createAccount(Map userInfo) throws SQLException {
	   
	   if(!isUniqueId(userInfo)) {
		   return false;
	   }
	   
	   StringBuffer sql = new StringBuffer();
	   sql.append("INSERT INTO CM_USER(USER_ID,USER_PW,USER_NM,EMAIL) VALUES (?,?,?,?)");
            
	   jdbcTemplate.update(sql.toString(), 
			   new Object[] {(String) userInfo.get("USER_ID"),
							 (String) userInfo.get("USER_PW"),
							 (String) userInfo.get("USER_NM"),
							 (String) userInfo.get("EMAIL")
							});
	   return true;
   }
   
   public boolean isUniqueId(Map userInfo)throws SQLException{

	   String userId = (String) userInfo.get("USER_ID");
	   
	   Object[] sqlParameter = new Object[] {userId};

	   StringBuffer sql = new StringBuffer();
	   sql.append("SELECT COUNT(*) FROM CM_USER WHERE USER_ID = ?");
	
	   int num = jdbcTemplate.queryForObject(sql.toString(), sqlParameter, Integer.class);

	   if(num == 0) {
		   return true;
	   }	   
	   return false;
   }
   
   public boolean isCorrectPassword(Map userInfo)throws SQLException{

	   String userId = (String) userInfo.get("USER_ID");
	   String userPw = (String) userInfo.get("USER_PW");
	   
	   Object[] sqlParameter = new Object[] {userId, userPw};

	   StringBuffer sql = new StringBuffer();
	   sql.append("SELECT COUNT(*) FROM CM_USER WHERE USER_ID = ? AND USER_PW = ?");
	
	   int num = jdbcTemplate.queryForObject(sql.toString(), sqlParameter, Integer.class);
	   
	   if(num == 1) {
		   return true;
	   }	   
	   return false;
   }

}
