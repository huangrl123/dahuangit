package com.dahuangit.util.sqlite;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import com.dahuangit.util.DBFUtils;
import com.dahuangit.util.string.StringUtils2;
import com.linuxense.javadbf.DBFField;

/**
 * SQLite常用个操作辅助类
 * 
 * @author 黄仁良
 * 
 */
public class SqliteHelper {
	private Connection conn;
	private static final String JDBC_DRIVER = "org.sqlite.JDBC";
	private static final String URL = "jdbc:sqlite:%s";
	private static final String SELECT_TABLE = "SELECT * FROM %s";
	private static final String SELECT_TABLE_PARAM = "SELECT * FROM %s WHERE %s";
	private static final String INSERT_TABLE = "INSERT INTO %s(%s) VALUES (%s)";
	private static final String UPDATE_TABLE = "UPDATE %S SET COUNT=%S,MON=%S,B_CHECK_NO=%S,CHK_FLAG=%S";
	private static final String UPDATE_TABLE_PARAM = "UPDATE %s SET %s WHERE 1=1 %s";
	private static final String DELETE_TABLE_PARAM = "DELETE FROM %s WHERE %s";
	private static final Logger LOGGER = Logger.getLogger(SqliteHelper.class);

	/**
	 * 构造方法
	 * 
	 * @param dbFile
	 *            sqlite3的db文件
	 * @throws SQLException
	 * @throws Exception
	 */
	public SqliteHelper(String dbFile) throws SQLException {
		Validate.notNull(dbFile, "不能为null");

		LOGGER.info("实例化sqlite3的db文件:" + dbFile);
		this.conn = this.getConnection(dbFile);
	}

	/**
	 * 插入数据
	 * 
	 * @param beans
	 *            封装bean对list集合,例如：List<Customer>
	 * @param tableName
	 *            sqlite表名
	 */
	public void insert(List<?> beans, String tableName) throws Exception {
		Validate.notNull(beans, " 封装bean对list集合不能为null");
		Validate.notNull(tableName, "表名不能为null");

		LOGGER.info("插入数据,表名：" + tableName);
		List<SqliteColumn> columns = getColumnNameListByTable(tableName);
		PreparedStatement batchSmt = this.conn.prepareStatement(getPrepareStatementInsertSQLByTableName(tableName));
		this.conn.setAutoCommit(false);
		for (Object obj : beans) {
			for (int i = 1; i <= columns.size(); i++) {
				String value = getFieldValueByColumnName(obj, columns.get(i - 1).getColumnName());

				if (null == value) {
					batchSmt.setString(i, "");
				} else {
					batchSmt.setString(i, value);
				}
			}
			batchSmt.addBatch();
		}
		batchSmt.executeBatch();
		this.conn.commit();
		batchSmt.close();
	}

	/**
	 * 根据条件删除所有记录
	 * 
	 * @param tableName
	 * @param param
	 * @throws Exception
	 */
	public void delete(String tableName, String param) throws Exception {
		Validate.notNull(tableName, "表名不能为null");
		Statement stm = this.conn.createStatement();
		// 不需要自动提交
		this.conn.setAutoCommit(false);
		stm.execute(this.StringFormat(DELETE_TABLE_PARAM, tableName, param));
		// 手动提交
		this.conn.commit();
		// 恢复自动提交
		this.conn.setAutoCommit(true);
	}

	/**
	 * 查询所有记录，并放回List<Map<String, String>> 集合,Map中的key:列名,value:列对应的值
	 * 
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> queryForList(String tableName) throws Exception {
		Validate.notNull(tableName, "表名不能为null");

		LOGGER.info("查询所有记录,表名:" + tableName);

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Statement stm = this.conn.createStatement();
		ResultSet rs = stm.executeQuery(this.StringFormat(SELECT_TABLE, tableName));
		List<SqliteColumn> columns = getColumnNameListByTable(tableName);

		while (rs.next()) {
			Map<String, String> map = new HashMap<String, String>();
			for (SqliteColumn column : columns) {
				map.put(column.getColumnName(), rs.getString(column.getColumnName()));
			}
			list.add(map);
		}

		return list;
	}

	/**
	 * 根据条件查询所有记录，并放回List<Map<String, String>> 集合,Map中的key:列名,value:列对应的值
	 * 
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> queryForListByParam(String tableName, String param) throws Exception {
		Validate.notNull(tableName, "表名不能为null");

		LOGGER.info("查询所有记录,表名:" + tableName);

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Statement stm = this.conn.createStatement();
		ResultSet rs = stm.executeQuery(this.StringFormat(SELECT_TABLE_PARAM, tableName, param));
		List<SqliteColumn> columns = getColumnNameListByTable(tableName);

		while (rs.next()) {
			Map<String, String> map = new HashMap<String, String>();
			for (SqliteColumn column : columns) {
				map.put(column.getColumnName(), rs.getString(column.getColumnName()));
			}
			list.add(map);
		}

		return list;
	}

	/**
	 * 返回符合条件的所有记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public ResultSet queryBySQL(String sql) throws Exception {
		Validate.notNull(sql, "sql不能为null");

		LOGGER.info("sql:" + sql);
		Statement stm = this.conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);

		return rs;
	}

	/**
	 * 从DBF文件读取然后写入库
	 * 
	 * @param dbfFile
	 *            dbf文件
	 * @param tableName
	 *            目标表名称
	 * @throws SQLException
	 */
	public void readFromDBFAndWrite(String dbfFile, String tableName) throws SQLException {
		Validate.notNull(dbfFile, "dbfFile不能为null");
		Validate.notNull(tableName, "tableName不能为null");

		LOGGER.info("dbfFile:" + dbfFile + " tableName:" + tableName);

		PreparedStatement batchSmt = null;
		try {
			// 从dbf读取数据
			List<Map<String, String>> rows = DBFUtils.readDBF(dbfFile);
			// 根据表名查询表的基本信息以及列的基本信息
			List<SqliteColumn> columns = this.getColumnNameListByTable(tableName);
			// 创建动态insert 语句
			batchSmt = this.conn.prepareStatement(getPrepareStatementInsertSQLByTableName(tableName));
			// 不需要自动提交
			this.conn.setAutoCommit(false);

			for (Map<String, String> row : rows) {
				for (int i = 1; i <= columns.size(); i++) {
					SqliteColumn column = columns.get(i - 1);
					String s = row.get(column.getColumnName());
					batchSmt.setString(i, s);
				}
				// 加入批量执行队列
				batchSmt.addBatch();
			}

			// 批量执行
			batchSmt.executeBatch();
			// 手动提交
			this.conn.commit();
			// 恢复自动提交
			this.conn.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("从DBF文件读取然后写入库时失败,表名：" + tableName + " dbf:" + dbfFile + e, e);
			throw new SqliteException(e.getMessage());
		} finally {
			if (batchSmt != null) {
				batchSmt.close();
			}
		}
	}

	/**
	 * 从DBF文件读取然后写入库
	 * 
	 * @param dbfFile
	 *            dbf文件
	 * @param tableName
	 *            目标表名称
	 * @param flag
	 *            需不需要替换DB中的字段，true则就换
	 * @throws SQLException
	 */
	public void readFromDBFAndWrite(String dbfFile, String tableName, boolean flag, Map<String, String> replaceColumns)
			throws SQLException {
		Validate.notNull(dbfFile, "dbfFile不能为null");
		Validate.notNull(tableName, "tableName不能为null");

		LOGGER.info("dbfFile:" + dbfFile + " tableName:" + tableName);
		PreparedStatement batchSmt = null;
		List<Map<String, String>> rows = null;

		try {
			// 从dbf读取数据
			if (flag == true) {
				rows = DBFUtils.readDBFToDBAndReplaceColumnName(dbfFile, replaceColumns);
			} else {
				rows = DBFUtils.readDBF(dbfFile);
			}
			// 根据表名查询表的基本信息以及列的基本信息
			List<SqliteColumn> columns = this.getColumnNameListByTable(tableName);
			// 创建动态insert 语句
			batchSmt = this.conn.prepareStatement(getPrepareStatementInsertSQLByTableName(tableName));
			// 不需要自动提交
			this.conn.setAutoCommit(false);

			for (Map<String, String> row : rows) {
				for (int i = 1; i <= columns.size(); i++) {
					SqliteColumn column = columns.get(i - 1);
					String s = row.get(column.getColumnName());
					batchSmt.setString(i, s);
				}
				// 加入批量执行队列
				batchSmt.addBatch();
			}

			// 批量执行
			batchSmt.executeBatch();
			// 手动提交
			this.conn.commit();
			// 恢复自动提交
			this.conn.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("从DBF文件读取然后写入库时失败,表名：" + tableName + " dbf:" + dbfFile + e, e);
			throw new SqliteException(e.getMessage());
		} finally {
			batchSmt.close();
		}
	}

	/**
	 * 将一个表另存为dbf文件
	 * 
	 * @param tableName
	 *            表名
	 * @param dbfFile
	 *            DBF文件
	 * @throws Exception
	 */
	public void saveAsDBF(String tableName, String dbfFile) throws Exception {
		Validate.notNull(tableName, "tableName不能为null");
		Validate.notNull(dbfFile, "dbfFile不能为null");

		LOGGER.info("tableName:" + tableName + " dbfFile:" + dbfFile);

		Statement stm = null;
		try {
			List<SqliteColumn> columns = this.getColumnNameListByTable(tableName);
			List<DBFField> dbfFields = this.convert2DBFFields(columns);
			List<String[]> rows = new ArrayList<String[]>();
			String select = this.StringFormat(SELECT_TABLE, tableName);
			stm = this.conn.createStatement();
			ResultSet rs = stm.executeQuery(select);

			while (rs.next()) {
				String[] row = new String[columns.size()];
				for (int i = 0; i < columns.size(); i++) {
					SqliteColumn column = (SqliteColumn) columns.get(i);
					String columnName = column.getColumnName().toUpperCase();
					row[i] = rs.getString(columnName);
				}
				rows.add(row);
			}

			DBFUtils.writeDBF(dbfFields, rows, dbfFile);
		} catch (Exception e) {
			LOGGER.error("将表[" + tableName + "]另存为dbf文件时出错,原因:" + e, e);
			throw new SqliteException(e.getMessage());
		} finally {
			stm.close();
		}
	}

	/**
	 * 将一个表另存为dbf文件
	 * 
	 * @param tableName
	 *            表名
	 * @param dbfFile
	 *            DBF文件
	 * @param ignoredColumnNames
	 *            被忽略的列
	 * @throws Exception
	 */
	public void sqliteSaveAsDBF(String tableName, String dbfFile, Map<String, String> replaceColumns,
			List<String> ignoredFieldNameList) throws Exception {
		Validate.notNull(tableName, "tableName不能为null");
		Validate.notNull(dbfFile, "dbfFile不能为null");

		LOGGER.info("tableName:" + tableName + " dbfFile:" + dbfFile);

		Statement stm = null;
		try {
			List<SqliteColumn> columns = this.getColumnNameListByTable(tableName);
			List<DBFField> dbfFields = this.convert2DBFFieldList(columns, replaceColumns, ignoredFieldNameList);
			List<String[]> rows = new ArrayList<String[]>();

			String select = this.StringFormat(SELECT_TABLE, tableName);
			stm = this.conn.createStatement();
			ResultSet rs = stm.executeQuery(select);

			while (rs.next()) {
				String[] row = new String[columns.size()];
				for (int i = 0; i < columns.size(); i++) {
					SqliteColumn column = (SqliteColumn) columns.get(i);
					String columnName = column.getColumnName().toUpperCase();
					row[i] = rs.getString(columnName);
				}
				rows.add(row);
			}

			DBFUtils.writeDBF(dbfFields, rows, dbfFile);
		} catch (Exception e) {
			LOGGER.error("将表[" + tableName + "]另存为dbf文件时出错,原因:" + e, e);
			throw new SqliteException(e.getMessage());
		} finally {
			stm.close();
		}
	}

	/**
	 * 将一个表另存为dbf文件
	 * 
	 * @param tableName
	 *            表名
	 * @param dbfFile
	 *            DBF文件
	 * @param replaceColumns
	 *            替换的字段
	 * @param columnNumber
	 *            需要转换的db3的列数
	 * @param ignoredColumnNames
	 *            被忽略的字段（因为DB3中上传的字段DBF不需要，在DB3转换DBF时需要去掉）
	 * @throws Exception
	 */
	public void sqliteSaveAsDBF(String tableName, String dbfFile, Map<String, String> replaceColumns, int columnNumber,
			List<String> ignoredFieldNameList) throws Exception {
		Validate.notNull(tableName, "tableName不能为null");
		Validate.notNull(dbfFile, "dbfFile不能为null");

		LOGGER.info("tableName:" + tableName + " dbfFile:" + dbfFile);

		Statement stm = null;
		try {
			List<SqliteColumn> columns = this.getColumnNameListByTable(tableName);
			List<SqliteColumn> columnList = new ArrayList<SqliteColumn>();
			for (int i = 0; i < columns.size(); i++) {
				if(null == ignoredFieldNameList){
					columnList.add(columns.get(i));
				}else{
					if(!ignoredFieldNameList.isEmpty()){
						if (!ignoredFieldNameList.contains(columns.get(i).getColumnName())) {
							columnList.add(columns.get(i));
						}
					}else{
						columnList.add(columns.get(i));
					}
				}
			}

			List<DBFField> dbfFields = this.convert2DBFFieldList(columnList, replaceColumns, ignoredFieldNameList);
			List<String[]> rows = new ArrayList<String[]>();
			String select = this.StringFormat(SELECT_TABLE, tableName);
			stm = this.conn.createStatement();
			ResultSet rs = stm.executeQuery(select);

			while (rs.next()) {
				String[] row = new String[columnList.size()];
				for (int i = 0; i < columnList.size(); i++) {
					SqliteColumn column = (SqliteColumn) columnList.get(i);
					String columnName = column.getColumnName().toUpperCase();
					if (null != rs.getString(columnName)) {
						row[i] = rs.getString(columnName).trim();
					} else {
						row[i] = "";
					}

				}
				rows.add(row);
			}

			DBFUtils.writeDBF(dbfFields, rows, dbfFile);
		} catch (Exception e) {
			LOGGER.error("将表[" + tableName + "]另存为dbf文件时出错,原因:" + e, e);
			throw new SqliteException(e.getMessage());
		} finally {
			stm.close();
		}
	}

	/**
	 * 往DB3的commconfig表中写入数据,因为commconfig表中是键值对，传值时“键,值”，中间以逗号隔开作为一条记录
	 * 
	 * @param tableName
	 *            表名
	 * @param parms
	 * @throws Exception
	 */
	public void insertCommConfig(String tableName, String[] parms) throws Exception {
		Validate.notNull(parms, " 封装bean对list集合不能为null");
		Validate.notNull(tableName, "表名不能为null");

		LOGGER.info("插入数据,表名：" + tableName);
		StringBuffer columnNames = new StringBuffer();
		StringBuffer preps = new StringBuffer();
		columnNames.append("PROPERTY_NAME,PROPERTY_VALUE");
		preps.append("?,?");
		PreparedStatement batchSmt = this.conn.prepareStatement(this.StringFormat(INSERT_TABLE, tableName, columnNames,
				preps));

		this.conn.setAutoCommit(false);

		for (int i = 0; i < parms.length; i++) {
			String[] slqParms = parms[i].split(",");
			for (int j = 0; j < slqParms.length; j++) {
				batchSmt.setString(j + 1, slqParms[j].trim());
			}
			batchSmt.addBatch();
		}

		batchSmt.executeBatch();
		this.conn.commit();
		this.conn.setAutoCommit(true);

		batchSmt.close();
	}

	/**
	 * 修改DB3中的指定的表中的数据
	 */
	public void updateSqliteTableByName(String tableName, String[] updateColumnData) throws Exception {
		Validate.notNull(updateColumnData, " 修改的列数据updateColumnData不能为空");
		Validate.notNull(tableName, "表名不能为null");
		LOGGER.info("修改表数据,表名：" + tableName);
		PreparedStatement batchSmt = this.conn.prepareStatement(this.StringFormat(UPDATE_TABLE, tableName, "?", "?",
				"?", "?"));
		this.conn.setAutoCommit(false);
		for (int i = 0; i < updateColumnData.length; i++) {
			batchSmt.setString(i + 1, updateColumnData[i]);
		}

		batchSmt.addBatch();
		batchSmt.executeBatch();
		this.conn.commit();
		this.conn.setAutoCommit(true);

		batchSmt.close();
	}
	
	/**
	 * 通用的修改DB3中的指定的表中的数据
	 */
	public void updateCommSqliteTableByName(String tableName, String[] updateColumnData) throws Exception {
		Validate.notNull(updateColumnData, " 修改的列数据updateColumnData不能为空");
		Validate.notNull(tableName, "表名不能为null");
		LOGGER.info("修改表数据,表名：" + tableName);
		PreparedStatement batchSmt = this.conn.prepareStatement(this.StringFormat(UPDATE_TABLE_PARAM, tableName,
				updateColumnData[0], updateColumnData[1]));
		this.conn.setAutoCommit(false);
		batchSmt.addBatch();
		int row=batchSmt.executeUpdate();
		if(row > 0 ){
			LOGGER.info("修改表" + tableName+"一条数据成功!");
		}
		this.conn.commit();
		this.conn.setAutoCommit(true);
		batchSmt.close();
	}

	/**
	 * 获取sqlite连接
	 * 
	 * @param dbFile
	 * @return
	 */
	private Connection getConnection(String dbFile) {
		Validate.notNull(dbFile, "dbFile不能为null");

		LOGGER.info("dbFile:" + dbFile);

		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.StringFormat(URL, dbFile));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("获取sqlite连接时报错,这可能是sqlite文件[" + dbFile + "]不正确导致的:" + e, e);
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

	@SuppressWarnings("unchecked")
	private String getFieldValueByColumnName(Object obj, String columnName) throws IllegalArgumentException,
			IllegalAccessException {
		String value = null;

		// 如果是map的情况
		if (obj instanceof Map) {
			Map<String, Object> m = (Map<String, Object>) obj;
			for (Map.Entry<String, Object> en : m.entrySet()) {
				if (en.getKey().equals(columnName)) {

					Object o = en.getValue();
					if (null == o) {
						value = "";
					} else {
						value = String.valueOf(o);
					}

					break;
				}
			}
			return value;
		}

		for (Field field : obj.getClass().getDeclaredFields()) {
			// 属性名称与字段名称相同,或者是字段名称是属性名称的驼峰状表示,例如属性名为：userNo，字段名为：USER_NO

			if (field.getName().toUpperCase().equals(columnName)
					|| StringUtils2.hump2underlineUpperCase(field.getName()).equals(columnName)) {
				field.setAccessible(true);
				Object o = field.get(obj);

				if (null == o) {
					value = "";
				} else {
					value = String.valueOf(o);
				}

				field.setAccessible(false);
				break;
			}
		}
		return value;
	}

	/**
	 * 根据表名构造动态insert sql语句
	 * 
	 * @param tableName
	 * @return
	 */
	private String getPrepareStatementInsertSQLByTableName(String tableName) {
		String str = null;
		List<SqliteColumn> columns = this.getColumnNameListByTable(tableName);
		// 列名，以逗号分隔
		StringBuffer columnNames = new StringBuffer();
		// 问号，以逗号分隔
		StringBuffer preps = new StringBuffer();

		for (int i = 0; i < columns.size(); i++) {
			SqliteColumn column = columns.get(i);
			columnNames.append(column.getColumnName());
			preps.append("?");
			if (i != columns.size() - 1) {
				columnNames.append(",");
				preps.append(",");
			}
		}

		str = this.StringFormat(INSERT_TABLE, tableName, columnNames, preps);
		return str;
	}

	/**
	 * 得到列信息
	 * 
	 * @param tableName
	 * @return
	 */
	public List<SqliteColumn> getColumnNameListByTable(String tableName) {
		List<SqliteColumn> columnNames = new ArrayList<SqliteColumn>();
		try {
			DatabaseMetaData metaData = this.conn.getMetaData();
			// 得到列信息
			ResultSet rs = metaData.getColumns(null, "%", tableName, "%");

			while (rs.next()) {
				SqliteColumn column = new SqliteColumn();
				String columnName = rs.getString("COLUMN_NAME").toUpperCase();
				column.setColumnName(columnName);
				String typeName = rs.getString("TYPE_NAME").toUpperCase();
				// 判断类型是否有带长度
				if (typeName.indexOf("(") != -1) {
					String s = typeName.substring(typeName.indexOf("(") + 1, typeName.indexOf(")"));
					if (s.indexOf(",") != -1) {
						column.setLength(Integer.parseInt(s.substring(0, s.indexOf(",")))
								+ Integer.parseInt(s.substring(s.indexOf(",") + 1)));
					} else {
						column.setLength(Integer.parseInt(s));
					}
				}
				columnNames.add(column);
			}

			if (columnNames.isEmpty()) {
				throw new SqliteException("不能在数据库文件里找到表[" + tableName + "]");
			}
		} catch (Exception e) {
			LOGGER.error("得到列信息时出错,请检查sqlite文件表结构是否正确,目前列类型应全为VARCHAR(长度)类型" + e, e);
			throw new SqliteException(e.getMessage());
		}
		return columnNames;
	}

	/**
	 * 将dbf字段
	 * 
	 * @param table
	 * @return
	 */
	private List<DBFField> convert2DBFFields(List<SqliteColumn> columns) {
		List<DBFField> dbfFields = new ArrayList<DBFField>();

		for (SqliteColumn column : columns) {
			DBFField field = new DBFField();
			LOGGER.info(column.getColumnName());
			field.setFieldLength(column.getLength());
			field.setName(column.getColumnName());
			field.setDataType(DBFField.FIELD_TYPE_C);
			dbfFields.add(field);
		}

		return dbfFields;
	}

	/**
	 * 将DB3 中的字段转换成DBF的字段。
	 * 
	 * @param columns
	 * @param replaceColumns
	 *            DB3中有些字段如果不符合要求，可进行替换。key DB3 src字段名 value是目标字段名
	 * @param ignoredColumnNames
	 *            被忽略的字段
	 * @return
	 */
	private List<DBFField> convert2DBFFieldList(List<SqliteColumn> columns, Map<String, String> replaceColumns,
			List<String> ignoredFieldNameList) {

		List<DBFField> dbfFields = new ArrayList<DBFField>();

		for (SqliteColumn column : columns) {
			DBFField field = new DBFField();
			String columnName = column.getColumnName();
			LOGGER.info(column.getColumnName());
			
			if(null != ignoredFieldNameList){
				if(!ignoredFieldNameList.isEmpty()){
					if (ignoredFieldNameList.contains(columnName)) {
						   continue;
						}
				}
			}
			for (Map.Entry<String, String> col : replaceColumns.entrySet()) {
				if (col.getKey().equalsIgnoreCase(columnName)) {
					columnName = col.getValue();
					break;
				}
			}

			field.setName(columnName);
			int filedLength=column.getLength();
			if(filedLength>0){
				field.setFieldLength(filedLength);
			}
			field.setDataType(DBFField.FIELD_TYPE_C);
			dbfFields.add(field);
		}
		return dbfFields;
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

	/**
	 * 得到jdbc 连接
	 * 
	 * @return
	 */
	public Connection getConn() {
		return conn;
	}

}
