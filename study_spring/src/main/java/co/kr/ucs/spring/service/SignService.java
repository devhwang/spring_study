package co.kr.ucs.spring.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import co.kr.ucs.spring.dao.DBConnectionPool;
import co.kr.ucs.spring.dao.DBConnectionPoolManager;
import co.kr.ucs.spring.dao.DBManager;

public class SignService {

	private static final Logger logger = LoggerFactory.getLogger(SignService.class);

	@Autowired
	JdbcTemplate jdbcTemplate;
/*	
	DBConnectionPoolManager dbPoolManager = DBConnectionPoolManager.getInstance();
	DBConnectionPool dbPool;
	
	public SignService() {
		dbPoolManager.setDBPool(DBManager.getUrl(), DBManager.getId(), DBManager.getPw());
		dbPool = dbPoolManager.getDBPool();
	}
	
	public boolean doLogin(Map userInfo) throws Exception {		
		//Connection conn = DBManager.getConnection();
		Connection conn = dbPool.getConnection();				
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{

			pstmt = conn.prepareStatement("SELECT USER_ID, USER_NM, EMAIL FROM CM_USER WHERE USER_ID = ? AND USER_PW = ?");
			pstmt.setString(1, (String) userInfo.get("USER_ID"));
			pstmt.setString(2, (String) userInfo.get("USER_PW"));
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				userInfo.put("USER_NM", rs.getString("USER_NM"));
				userInfo.put("EMAIL", rs.getString("EMAIL"));
				return true;
			}
			return false;
			
		} catch (Exception e){
			e.printStackTrace();
			logger.error("?��?�� : {}", e.getMessage());
			return false;		
		}finally{
			logger.info(dbPool.freeConnection(conn));
			DBManager.close(rs, pstmt);
		}	
	}*/
	
   
   public Map doLogin(Map userInfo)throws SQLException {
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT USER_ID, USER_NM, EMAIL FROM CM_USER WHERE USER_ID = ? AND USER_PW = ?");
               
      return jdbcTemplate.query(sql.toString(), new ResultSetExtractor<Map>(){
          public Map extractData(ResultSet rs) throws SQLException {
              HashMap<String,String> mapRet= new HashMap<String,String>();
              while(rs.next()){
            	  mapRet.put("USER_NM",rs.getString("USER_NM"));
            	  mapRet.put("EMAIL",rs.getString("EMAIL"));
              }
              return mapRet;
          }
      });
   }
	
	/*public boolean createAccount(Map userInfo) throws Exception {
		//Connection conn = DBManager.getConnection();
		Connection conn = dbPool.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			pstmt = conn.prepareStatement("SELECT USER_ID, USER_PW, USER_NM, EMAIL FROM CM_USER WHERE USER_ID = ?");
			pstmt.setString(1, (String) userInfo.get("USER_ID"));

			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return false;
			}
			
			pstmt = conn.prepareStatement("INSERT INTO CM_USER(USER_ID,USER_PW,USER_NM,EMAIL) VALUES (?,?,?,?)");
			pstmt.setString(1, (String) userInfo.get("USER_ID"));
			pstmt.setString(2, (String) userInfo.get("USER_PW"));
			pstmt.setString(3, (String) userInfo.get("USER_NM"));
			pstmt.setString(4, (String) userInfo.get("EMAIL"));
			
			pstmt.executeUpdate();
			
			return true;
		} catch (Exception e){
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				logger.error("?��?�� : {}", e1.getMessage());
			}
			logger.error("?��?�� : {}", e.getMessage());
			throw e;
		}finally{
			logger.info(dbPool.freeConnection(conn));
			DBManager.close(rs, pstmt);
		}	
	}*/
   
   public int existsUser(Map userInfo)throws SQLException{
		
	   String userId = (String) userInfo.get("USER_ID");
	   String userPw = (String) userInfo.get("USER_PW");
	   
	   Object[] sqlParameter = new Object[] {userId};

	   StringBuffer sql = new StringBuffer();
	   sql.append("SELECT COUNT(*) FROM CM_USER WHERE USER_ID = ?");
		
	   return jdbcTemplate.queryForObject(sql.toString(), sqlParameter, Integer.class);
   }
   
   
   public boolean createAccount(Map userInfo) throws SQLException {
      
	   if(existsUser(userInfo) < 1) {
		   return false;
	   }
	   
	   StringBuffer sql = new StringBuffer();
      sql.append("INSERT INTO CM_USER(USER_ID,USER_PW,USER_NM,EMAIL) VALUES (?,?,?,?)");
            
      jdbcTemplate.update(sql.toString(), new Object[] {(String) userInfo.get("USER_ID"),
    		  											(String) userInfo.get("USER_PW"),
    		  											(String) userInfo.get("USER_NM"),
    		  											(String) userInfo.get("EMAIL")
    		  											});
      return true;
   }
}
