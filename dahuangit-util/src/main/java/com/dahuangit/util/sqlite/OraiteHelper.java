package com.dahuangit.util.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * SQLite常用个操作辅助类
 * 
 * @author 黄仁良
 * 
 */
public class OraiteHelper {
	private Connection conn;
	private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:sqlite:%s";
	private static final String SELECT_TABLE = "SELECT * FROM %s";
	private static final String SELECT_TABLE_PARAM = "SELECT * FROM %s WHERE %s";
	private static final String INSERT_TABLE = "INSERT INTO %s(%s) VALUES (%s)";
	private static final String UPDATE_TABLE = "UPDATE %S SET COUNT=%S,MON=%S,B_CHECK_NO=%S,CHK_FLAG=%S";
	private static final String DELETE_TABLE_PARAM = "DELETE FROM %s WHERE %s";
	private static final Logger LOGGER = null;

	/**
	 * 获取sqlite连接
	 * 
	 * @param dbFile
	 * @return
	 */
	private Connection getConnection() {
	
		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl","knet7000cs","knet#002");  
		} catch (Exception e) {
			e.printStackTrace();
			throw new SqliteException(e.getMessage());
		}
		return conn;
	}

	/**
	 * 关闭连接
	 * 
	 */
	public void closeConnection() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			LOGGER.error("关闭sqlite连接时报错" + e, e);
			throw new SqliteException(e.getMessage());
		}
	}

	
	
	/**
	 * 得到列信息
	 * 
	 * @param tableName
	 * @return
	 */
	public  List<Map<String,String>> getColumnNameListByTable(String tableName,String rowName,Long id) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		try {
		
			// 得到列信息
			ResultSet rs = null;  
			Statement stmt = null;  
			conn = this.getConnection();
			stmt = conn.createStatement();  
			
			if(null != id || !"".equals(rowName)){
				rs = stmt.executeQuery("select * from " + tableName + " where "+rowName+" = "+id);
			}
			else{
				rs = stmt.executeQuery("select * from " + tableName);
			}
			
			//System.out.println(conn);
			ResultSetMetaData md =rs.getMetaData();
			
			 while(rs.next()) {  
				 Map<String,String> map = new HashMap<String, String>();
				 for (int i = 0; i < md.getColumnCount(); i++) {
					 String columnName = md.getColumnName(i + 1);
					 map.put(columnName, rs.getString(columnName));
				 }  
				 list.add(map);
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 得到列信息
	 * 
	 * @param tableName
	 * @return
	 */
	public  List<Map<String,String>> getColumnNameByTable(String tableName,String rowName,Long id) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		try {
		
			// 得到列信息
			ResultSet rs = null;  
			Statement stmt = null;  
			conn = this.getConnection();
			stmt = conn.createStatement();  
			
			if(null != id || !"".equals(rowName)){
				rs = stmt.executeQuery("select * from " + tableName + " where "+rowName+" like '%"+id+"%'");
			}
			
			//System.out.println(conn);
			ResultSetMetaData md =rs.getMetaData();
			
			 while(rs.next()) {  
				 Map<String,String> map = new HashMap<String, String>();
				 for (int i = 0; i < md.getColumnCount(); i++) {
					 String columnName = md.getColumnName(i + 1);
					 map.put(columnName, rs.getString(columnName));
				 }  
				 list.add(map);
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * string格式化方法
	 * 
	 * @param format
	 * @param args
	 * @return
	 */
	private String StringFormat(String format, Object... args) {
		Formatter formatter = new Formatter();
		return formatter.format(format, args).toString();
	}

	
    public static void main(String[] args) {
    	OraiteHelper o = new OraiteHelper();
    	String name = "";
    	String name1 = "";
    	int totalTable = 0;
    	
    	List<Map<String,String>> list = new ArrayList<Map<String,String>>();
    	String type = "36";
    	list = o.getColumnNameListByTable("typetable","",null);
    	String tableName ="";
    	String a  = "";
    	for(Map<String,String> map:list){
    		Iterator it = map.keySet().iterator();
    		String str = (String)it.next();
    		if(str.equals("TABLETYPE")){
    			String tableType = map.get(str);
    			if((" "+tableType).indexOf(type) > 0 ){
    				tableName = map.get("TABLENAME");
    				
    				String[] b = tableName.split("[;]");
    				a = b[0];
    				Map rowMap = new HashMap();
    				
    				String[] row = tableName.split("[;]"); 
					for (int i = 0; i < row.length; i++) {
						String[] b2 = row[i].split("[:]");
						for (int j = 0; j < b2.length; j++) {
							rowMap.put(b2[0], b2[1]);
						}	
					}
					
					for(int d =0;d<rowMap.size();d++){
						Object s[] = rowMap.keySet().toArray();
						System.out.println(s[d]+"~~~~~~~~~~~~~~~~~~~~~~"+rowMap.get(s[d]));
					}
    			}
    		}
    	}
    	
    	System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&"+a);
    	totalTable =  tableName.split(";").length;
    	
    	System.out.println(type+"-------------------"+tableName+">>>>>>>>>>>"+totalTable);
    	
    	for(int i =0;i<list.size();i++){
    		name=list.get(0).toString();
    		name1 = list.get(1).toString();
    	}
    	
    	System.out.println(name);
    	System.out.println(name1);
	}

}
