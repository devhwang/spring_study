package co.kr.ucs.spring.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConnectionPool {

	private static final Logger logger = LoggerFactory.getLogger(DBConnectionPool.class);
	
	private int initConns, maxConns;
	private long timeOut = 1000 * 1;// timeout 기본?? 30초이�? setTimeOut 메서?���? ?��?�� 직접 �??��?�� �? ?�� ?��?��.
	
	private String url, id, pw;
	
	private Boolean[] connStatus;
	private Connection[] connPool;
	
	public DBConnectionPool(String url, String id, String pw, int initConns, int maxConns){
		this.url = url;
		this.id  = id;
		this.pw  = pw;
		
		this.initConns = initConns;
		this.maxConns  = maxConns;
		
		this.connStatus = new Boolean[maxConns];//1 ?��?���??�� 2?��?���?
		this.connPool   = new Connection[maxConns];
		System.out.println(this.connPool);
		
		for(int i=0; i<this.initConns; i++) {//initConns만큼 커넥?�� 객체?��?��
			try {
				this.createConnection(i);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}
	
	//커넥?�� 객체?��?��
	private Connection createConnection(int pos) throws ClassNotFoundException, SQLException{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn=DriverManager.getConnection(url,id,pw);
		
		this.connPool[pos] = conn;
		this.connStatus[pos] = false;//1�? 초기?��
		
		return this.connPool[pos];
	}

	public synchronized Connection getConnection() throws SQLTimeoutException, InterruptedException{
		
		//커넥?��?? ?��?��?��
		/*String temp;
		StringBuffer status = new StringBuffer();
		status.append("[");
		for(int j = 0 ; j < connPool.length; j++) {
			if(this.connStatus[j]==null) {temp = "";} else if(this.connStatus[j]==false) { temp= "?��";} else {temp= "?��";}
			status.append(temp);
		}
		status.append("]");
		logger.info(status.toString());*/
		
		long currTime = System.currentTimeMillis();
		//int retryCnt = 0;
		
		do {
			
			
			for(int i=0; i<this.maxConns; i++) {
								
				if(this.connStatus[i] != null && this.connStatus[i] == false) {//?��?���??��?���?
					this.connStatus[i] = true;//?��?��중으로바꾸고
					return this.connPool[i];//?��?�� ?��?��?��번째?�� 커넥?�� 객체�? 반환
				}else if(this.connStatus[i] == null) {//0?��?���?
					try {
						this.connStatus[i] = true;
						return createConnection(i);//?���? 커넥?�� ?��?��?��?��
						
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("Connection ?��?�� ?��?��");
					}
				}				
			}	
			
			try {			
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}while(((System.currentTimeMillis() - currTime) <= this.timeOut));//??기�? 1000*1 1�? ?��?��?���? timeout

		
		throw new SQLTimeoutException("모든 Connection?�� ?��?��중입?��?��.");
	}
	
	public String freeConnection(Connection conn){
				
		if(conn != null) {
			for(int i=0; i<this.maxConns; i++) {
				if(this.connPool[i] == conn) {												
					this.connStatus[i] = false;//커넥?�� ?��?���??��?��?���? 만듦
					return "?���? 종료 ?���?";
					//break;
				}
			}
		}
		return "?���? 종료 ?��?��";
	}
	
	public long getTimeOut(){
		return timeOut;
	}
	
	public void setTimeOut(long timeOut){
		this.timeOut = timeOut;
	}
	

}
