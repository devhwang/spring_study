package co.kr.ucs.spring.dao;

import java.util.HashMap;
import java.util.Map;

public class DBConnectionPoolManager {
	final static String DEFAULT_POOLNAME = "DEFAULT";
	final static Map<String, DBConnectionPool> dbPool = new HashMap<String, DBConnectionPool>();
	final static DBConnectionPoolManager instance = new DBConnectionPoolManager();
	
	//DB Pool ?��?��
	//DBConnctionPoolManager 반환

	public static DBConnectionPoolManager getInstance(){
		return instance;
	}
	
	public void setDBPool(String url, String id, String pw) {
		setDBPool(DEFAULT_POOLNAME, url, id, pw, 1, 10);
	}
	
	//DB Pool ?��?��
	public void setDBPool(String poolName, String url, String id, String pw) {
		setDBPool(poolName, url, id, pw, 1, 10);
	}
	
	//DB Pool ?��?��
	public void setDBPool(String poolName, String url, String id, String pw, int initConns, int maxConns) {
		if(!dbPool.containsKey(poolName))  {
			DBConnectionPool connPool = new DBConnectionPool(url, id, pw, initConns, maxConns);
			dbPool.put(poolName, connPool);
		}
	}
	//Pool?��?�� 커넥?�� ?��?��
	public DBConnectionPool getDBPool(){
		return getDBPool(DEFAULT_POOLNAME);
	};
	
	//Pool?��?�� 커넥?�� ?��?��
	DBConnectionPool getDBPool(String poolName){//poolName?�� �??��?���?경우
		return dbPool.get(poolName);
	};
	
	
}
