package com.dahuangit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFWriter;

/**
 * DBF文件处理的相关方法
 * 
 * @author 黄仁良
 * 
 */
public class DBFUtils {
	/**
	 * 读取DBF文件
	 * 
	 * @param dbf
	 * @return List<Map<String, String>>
	 *         DBF文件里的所有行,其中的每一行都是一个以响应的字段名为key,字段值作为value的Map对象
	 * @exception Exception
	 */
	public static List<Map<String, String>> readDBF(String dbf) throws Exception {
		Validate.notNull(dbf, "dbf文件不能为null");
		
		List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
		InputStream in = null;
		try{
		    in= new FileInputStream(dbf);
		    DBFReader reader = new DBFReader(in);
    		int fieldCount = reader.getFieldCount();
    		Object[] rowValues = new Object[fieldCount];
    		while (null != (rowValues = reader.nextRecord())) {// 循环每一行
    			Map<String, String> row = new HashMap<String, String>();
    			for (int i = 0; i < fieldCount; i++) {// 循环每一列
    				String fieldName = reader.getField(i).getName().trim();
    				String fv = String.valueOf(rowValues[i]).trim();
    				row.put(fieldName, fv);
    			}
    			rows.add(row);
    		}
		}finally {
            try {
                if(in!=null){
                    in.close(); 
                }
            } catch (IOException ioe) {
                // ignore
            }   
        }
		return rows;
	}
	/**
	 * 读取DBF文件
	 * 
	 * @param dbf
	 * 
	 * @param replaceColumns 如果DBF中传来的字段名和定义DB3中的字段不匹配，进行替换，KEY 源字段名 value 替换成目标字段名
	 * 
	 * @return List<Map<String, String>>
	 *         DBF文件里的所有行,其中的每一行都是一个以响应的字段名为key,字段值作为value的Map对象
	 *         
	 * @throws Exception
	 */
	public static List<Map<String, String>> readDBFToDBAndReplaceColumnName(String dbf,Map<String, String> replaceColumns) throws Exception {
		Validate.notNull(dbf, "dbf文件不能为null");
		
		List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
		InputStream in =null;
		try{
		    in=new FileInputStream(dbf);
    		DBFReader reader = new DBFReader(in);
    		int fieldCount = reader.getFieldCount();
    		Object[] rowValues = new Object[fieldCount];
    		while (null != (rowValues = reader.nextRecord())) {// 循环每一行
    			Map<String, String> row = new HashMap<String, String>();
    			for (int i = 0; i < fieldCount; i++) {// 循环每一列
    				String fieldName = reader.getField(i).getName().trim();
    				String fv = String.valueOf(rowValues[i]).trim();
    				
    				for(Map.Entry<String, String> col : replaceColumns.entrySet()){
    					if(col.getKey().equalsIgnoreCase(fieldName)){
    						fieldName = col.getValue().trim();
    						break;
    					}
    				}
    				row.put(fieldName, fv);
    			}
    			rows.add(row);
    		}
		}finally {
            try {
                if(in!=null){
                    in.close(); 
                }
            } catch (IOException ioe) {
                // ignore
            }   
        }
		in.close();
		return rows;
	}
	
	

	/**
	 * 写入DBF文件
	 * 
	 * @param dbfFields
	 *            字段列表,例如：
	 *            <p>
	 *            List<DBFField> dbfFields = new ArrayList<DBFField>();<br>
	 *            dbfField.setName("字段名称1");//注意：字段名称长度不能大于10个字节
	 *            dbfField.setDataType
	 *            (DBFField.FIELD_TYPE_C);//FIELD_TYPE_C对应String
	 *            ,FIELD_TYPE_L对应Long,FIELD_TYPE_N对应Integer,FIELD_TYPE_F对应Float,
	 *            FIELD_TYPE_D对应Date<br>
	 *            dbfField.setFieldLength(80); <br>
	 *            dbfFields.add(dbfField);
	 *            </p>
	 * @param rows
	 *            记录行,例如： String[] rows = new String[] { "字段名称1对应的值" };
	 * @param dbf
	 *            DBF文件,例如：c:/test.dbf
	 * @throws Exception
	 */
	public static void writeDBF(List<DBFField> dbfFields, List<String[]> rows, String dbf) throws Exception {
		Validate.notNull(dbfFields, "字段列表不能为null");
		Validate.notNull(rows, "记录行不能为null");
		Validate.notNull(dbf, "DBF文件不能为null");
		DBFWriter writer = new DBFWriter();
		writer.setFields(dbfFields.toArray(new DBFField[dbfFields.size()]));
		for (Object[] arr : rows) {
			writer.addRecord(arr);
		}
		File file = new File(dbf);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		OutputStream fos = new FileOutputStream(dbf);
		writer.write(fos);
		fos.close();
	}
}
