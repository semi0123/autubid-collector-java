import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main {
	
	private static final String activeBidMachine = "SELECT process_num FROM bid_machine_mngs WHERE status = 'Active'";
	private static final String pnByKwd = "SELECT kwd_nm, target, process_num FROM bid_favorite_keywords WHERE bid_status = 'Active' AND target is not null GROUP BY kwd_nm, target";
	
	
	public static void main(String[] args) {
		String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
//		String HOST = "211.215.18.244"; //args[0];
//		String DB_NAME = "wonderbox_beta"; //args[1];
//		String USER_NAME = "wonderbox"; //args[2];
//		String PASSWORD = "wkddb3250"; //args[3];
		String HOST = args[0];
		String DB_NAME = args[1];
		String USER_NAME = args[2];
		String PASSWORD = args[3];
		String DB_URL = "jdbc:mysql://"+ HOST +"/" + DB_NAME +"?useUnicode=true&characterEncoding=utf8";
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(activeBidMachine);
			
			List<Integer> activeProcessList = new ArrayList<Integer>();
			while( rs.next() ){
				activeProcessList.add(rs.getInt(1));
			}
			Integer[] bmmArr = new Integer[activeProcessList.size()];
			Map<Integer, Integer> processCapacity = new HashMap<Integer, Integer>();
			int totalCount = 0;
			Integer numOfMachine = bmmArr.length;
			for(int i=0; i<numOfMachine; i++){
				bmmArr[i] = activeProcessList.get(i);
				processCapacity.put(bmmArr[i], 0);
			}
			
			List<HashMap<String, Object>> kwdList = new ArrayList<HashMap<String, Object>>();
			
			HashMap<String, Object> row = null;
			rs = stmt.executeQuery(pnByKwd);
			while( rs.next() ){
				row = new HashMap<String, Object>();
				row.put("kwd_nm", rs.getObject("kwd_nm"));
				row.put("target", rs.getObject("target"));
				row.put("process_num", rs.getObject("process_num"));
				kwdList.add(row);
			}
			
			Integer machineNum = null;
			Random random = new Random();
			Integer curCapacity = null;
			
			for(Map<String, Object> kwd : kwdList){
				while(true){
					machineNum = bmmArr[random.nextInt(numOfMachine)];
					curCapacity = processCapacity.get(machineNum);
					if( kwd.get("process_num") != machineNum && curCapacity < 100){
						kwd.put("process_num", machineNum);
						processCapacity.put(machineNum, curCapacity+1);
						totalCount++;
						break;
					}
				}
			}
			PreparedStatement pstmt = conn.prepareStatement("UPDATE bid_favorite_keywords SET process_num = ? WHERE kwd_nm = ? AND target = ?");
			for(Map<String, Object> kwd : kwdList){
				pstmt.setObject(1, kwd.get("process_num"));
				pstmt.setObject(2, kwd.get("kwd_nm"));
				pstmt.setObject(3, kwd.get("target"));
				pstmt.addBatch();
				pstmt.clearParameters();
			}
			int[] result = pstmt.executeBatch();
			System.out.println("업데이트 된 키워드 네임 갯수 : " + result.length);
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			try{
				conn.rollback();
			}catch(Exception re){
				System.out.println(re);
			}
		}finally{
			try{
				if( conn != null ){ 
					conn.close();
				}
			}catch(Exception e){
				
			}
		}
	}
	
	public static String getCurrentTime(){
		return new StringBuffer("[" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + "] ").toString();
	}
	
}
