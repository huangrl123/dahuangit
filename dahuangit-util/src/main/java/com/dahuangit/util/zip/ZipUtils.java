package com.dahuangit.util.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * 
 * zip/gzip工具类
 * 
 * @author 黄仁良
 * 
 *         创建于 2011-5-9 下午01:37:40
 */
public class ZipUtils {
	private static final Logger LOGGER = Logger.getLogger(ZipUtils.class);

	/**
	 * zip压缩
	 * 
	 * @param data
	 *            二进制数据
	 * @param fileName
	 *            zip文件里的文件名
	 * @return 压缩之后的二进制数据
	 * @throws Exception
	 */
	public static byte[] zip(byte[] data, String fileName) throws Exception {
		Validate.notNull(data, "二进制数据不能为null");
		Validate.notNull(fileName, "zip文件里的文件名不能为null");

		byte[] bt = null;
		ByteArrayOutputStream bos =null;
		ZipOutputStream zos=null;
		try {
			bos = new ByteArrayOutputStream();
			zos = new ZipOutputStream(bos);
			ZipEntry entry = new ZipEntry(fileName);
			entry.setSize(data.length);
			zos.putNextEntry(entry);
			zos.write(data);
			zos.closeEntry();
			zos.close();
			bt = bos.toByteArray();
			bos.close();
		} catch (Exception e) {
			LOGGER.error("zip时出错:" + e.getStackTrace(), e);
			throw new ZipException("zip时出错", e);
		}  finally {
            IOUtils.closeQuietly(zos);
            IOUtils.closeQuietly(bos);
        }
		return bt;
	}

	/**
	 * zip压缩
	 * 
	 * @param files
	 *            文件Map,key:文件名;value:文件的byte内容.
	 * @return
	 * @throws Exception
	 */
	public static byte[] zip(Map<String, byte[]> datas) throws Exception {
		Validate.notNull(datas, "文件流不能为null");
		ByteArrayOutputStream bos=null;
		ZipOutputStream zos =null;
		byte[] bt = null;
		
		try {
			bos = new ByteArrayOutputStream();
			zos = new ZipOutputStream(bos);

			for (Map.Entry<String, byte[]> entry : datas.entrySet()) {
				byte[] data = entry.getValue();
				ZipEntry zipEntry = new ZipEntry(entry.getKey());
				zipEntry.setSize(data.length);
				zos.putNextEntry(zipEntry);
				zos.write(data);
				zos.closeEntry();
			}

			zos.close();
			bt = bos.toByteArray();
		} catch (IOException e) {
			LOGGER.error("zip时出错:" + e.getStackTrace(), e);
			throw e;
		}  finally {
            IOUtils.closeQuietly(zos);
            IOUtils.closeQuietly(bos);
        }
		return bt;
	}

	/**
	 * zip压缩
	 * 
	 * @param files
	 *            文件Map,key:文件名;value:文件的byte内容.
	 * @param zipFile
	 *            保存的zip文件,例如:c:/test/test.zip
	 * @return
	 * @throws Exception
	 */
	public static void zip(Map<String, byte[]> datas, String zipFile) throws Exception {
		Validate.notNull(datas, "文件流不能为null");
		Validate.notNull(zipFile, " 保存的zip文件不能为null");
		FileOutputStream out=null;
		ByteArrayOutputStream bos=null;
		ZipOutputStream zos=null;
		
		try {
			out = new FileOutputStream(zipFile);
			bos = new ByteArrayOutputStream();
			zos = new ZipOutputStream(bos);

			for (Map.Entry<String, byte[]> entry : datas.entrySet()) {
				byte[] data = entry.getValue();
				ZipEntry zipEntry = new ZipEntry(entry.getKey());
				zipEntry.setSize(data.length);
				zos.putNextEntry(zipEntry);
				zos.write(data);
				zos.closeEntry();
			}

			zos.close();// important
			out.write(bos.toByteArray());
			out.close();
		} catch (Exception e) {
			LOGGER.error("zip时出错:" + e.getStackTrace(), e);
			throw new ZipException("zip时出错", e);
		}  finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(bos);
            IOUtils.closeQuietly(zos);
        }
	}

	/**
	 * zip压缩
	 * 
	 * @param data
	 *            二进制数据
	 * @param srcFileName
	 *            zip文件里的文件名
	 * @param destFilePathAndName
	 *            压缩之后的zip文件路径和名称
	 * @throws Exception
	 */
	public static void zip(byte[] data, String srcFileName, String destFilePathAndName) throws Exception {
		Validate.notNull(data, " 二进制数据不能为null");
		Validate.notNull(srcFileName, "zip文件里的文件名不能为null");
		Validate.notNull(destFilePathAndName, "压缩之后的zip文件路径和名称不能为null");
		FileOutputStream out=null;
		
		try {
			byte[] bt = zip(data, srcFileName);
			File file = new File(destFilePathAndName);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			out = new FileOutputStream(file);
			out.write(bt);
			out.close();
		} catch (Exception e) {
			LOGGER.error("zip时出错:" + e.getStackTrace(), e);
			throw new ZipException("zip时出错", e);
		} finally {
            IOUtils.closeQuietly(out);
        }
	}

	/**
	 * zip解压缩
	 * 
	 * @param data
	 *            zip二进制数据
	 * @return 解压缩之后的二进制数据
	 * @throws Exception
	 */
	public static byte[] unZip(byte[] data) throws Exception {
		Validate.notNull(data, "zip二进制数据不能为null");

		ByteArrayInputStream bis =null;
		ZipInputStream zis =null;
		byte[] bt = null;
		try {
			bis = new ByteArrayInputStream(data);
			zis = new ZipInputStream(bis);
			while (zis.getNextEntry() != null) {
				byte[] buf = new byte[1024];
				int temp;
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				while ((temp = zis.read(buf, 0, buf.length)) != -1) {
					bos.write(buf, 0, temp);
				}
				bt = bos.toByteArray();
				bos.close();
			}
			bis.close();
			zis.close();
		} catch (Exception e) {
			LOGGER.error("unZip时出错:" + e.getStackTrace(), e);
			throw e;
		}  finally {
            IOUtils.closeQuietly(bis);
            IOUtils.closeQuietly(zis);
        }
		return bt;
	}

	/**
	 * zip解压缩（注意:本方法暂时不支持中文文件夹名称以及文件名）
	 * 
	 * @param zipFile
	 *            需要解压的zip文件,例如:c:/test.zip
	 * @param targetFilePath
	 *            解压到目录,例如c:/test
	 */
	@SuppressWarnings("unchecked")
	public static void unZip(String zipFile, String targetFilePath) {
		Validate.notNull("zipFile", "需要解压的zip文件不能为null");
		Validate.notNull("targetFilePath", "解压到目录不能为null");
		FileOutputStream out=null;
		InputStream in =null;
		
		try {
			ZipFile zip = new ZipFile(zipFile);
			Enumeration<ZipEntry> entryEnum = (Enumeration<ZipEntry>) zip.entries();
			// 创建解压到目录
			File targetDir = new File(targetFilePath);
			if (!targetDir.exists()) {
				targetDir.mkdirs();
			}

			while (entryEnum.hasMoreElements()) {
				ZipEntry entry = entryEnum.nextElement();
				String file = targetFilePath + File.separator + entry.getName();

				// 如果是目录
				if (entry.isDirectory()) {
					File dir = new File(file);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					continue;
				}

				// 如果是文件
				in = zip.getInputStream(entry);
				out = new FileOutputStream(file);
				byte[] buf = new byte[1024];
				int temp;
				while ((temp = in.read(buf, 0, buf.length)) != -1) {
					out.write(buf, 0, temp);
				}
				in.close();
				out.close();
			}
		} catch (Exception e) {
			LOGGER.error("unZip时出错:" + e.getStackTrace(), e);
			throw new ZipException("unZip时出错", e);
		}  finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
        }
	}

	/**
	 * zip压缩文件夹
	 * 
	 * @param filePath
	 *            需要压缩的文件夹路径(包含要压缩的文件夹名称) 例如：d:/temp/test
	 * @param zipFileName
	 *            压缩后的需要保存的路径以及zip的名称 例如：d:/temp/test.zip
	 */
	public static void zip(String folderPathAndName, String zipFilePathAndName) {
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(new FileOutputStream(zipFilePathAndName));
			File file = new File(folderPathAndName);
			zipFolder(zos, file, "");
			zos.close();
		} catch (Exception e) {
			LOGGER.error("压缩文件夹时出错:" + e.getStackTrace(), e);
			throw new ZipException("压缩文件夹时出错", e);
		}
	}

	/**
	 * 对文件夹进行压缩操作
	 * 
	 * @param zos
	 * @param file
	 * @param base
	 * @throws Exception
	 */
	private static void zipFolder(ZipOutputStream zos, File file, String base) throws Exception {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			//这一行代码会在压缩文件的每一级文件夹下添加一个空文件（去掉的话终端解析压缩包有问题）
			zos.putNextEntry(new ZipEntry(base + File.separator));
			base = base.length() == 0 ? "" : base + File.separator;
			for (int i = 0; i < files.length; i++) {
				zipFolder(zos, files[i], base + files[i].getName().replace("–","-"));
			}
		} else {
			zos.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(file);
			int b;
			while ((b = in.read()) != -1) {
				zos.write(b);
			}
			in.close();
		}
	}

}
